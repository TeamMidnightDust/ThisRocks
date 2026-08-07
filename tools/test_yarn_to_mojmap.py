"""Regression tests for the Yarn -> Mojang source rewriter.

Standard library only — this machine has no pytest.
Run with: python3 -m unittest discover -s tools -p "test_*.py" -v
"""

import sys
import tempfile
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent))

from yarn_to_mojmap import already_mojang, resolve, rewrite_file, _report_impl

# Real 1.21.11 correspondences, verified against the joined mapping artifacts.
DICT = {
    "net.minecraft.registry.Registries": "net.minecraft.core.registries.BuiltInRegistries",
    "net.minecraft.registry.RegistryKeys": "net.minecraft.core.registries.Registries",
    "net.minecraft.registry.RegistryKey": "net.minecraft.resources.ResourceKey",
    "net.minecraft.item.Item": "net.minecraft.world.item.Item",
    "net.minecraft.item.Item$Settings": "net.minecraft.world.item.Item$Properties",
    "net.minecraft.text.Text": "net.minecraft.network.chat.Component",
    "net.minecraft.state.property.Properties":
        "net.minecraft.world.level.block.state.properties.BlockStateProperties",
}


class RewriteTests(unittest.TestCase):
    def test_rewrites_import_and_simple_name(self):
        src = 'import net.minecraft.text.Text;\nText t = Text.literal("x");\n'
        out, unresolved = rewrite_file(src, DICT)
        self.assertIn("import net.minecraft.network.chat.Component;", out)
        self.assertIn('Component t = Component.literal("x");', out)
        self.assertEqual(unresolved, [])

    def test_sequential_rename_collision_does_not_cascade(self):
        """RegistryKeys -> Registries must not then become BuiltInRegistries.

        Both renames apply in RocksMain. Running them as two sequential passes
        lets the second consume the first's output, silently producing
        BuiltInRegistries.ITEM_GROUP where Registries.ITEM_GROUP was meant.
        """
        src = (
            "import net.minecraft.registry.Registries;\n"
            "import net.minecraft.registry.RegistryKeys;\n"
            "import net.minecraft.registry.RegistryKey;\n"
            "RegistryKey.of(RegistryKeys.ITEM_GROUP, id);\n"
            "Registry.register(Registries.BLOCK, id, block);\n"
        )
        out, _ = rewrite_file(src, DICT)
        self.assertIn("ResourceKey.of(Registries.ITEM_GROUP, id);", out)
        self.assertIn("Registry.register(BuiltInRegistries.BLOCK, id, block);", out)
        self.assertNotIn("BuiltInRegistries.ITEM_GROUP", out)

    def test_nested_class_rename_beats_bare_outer(self):
        src = "import net.minecraft.item.Item;\nnew Item.Settings();\n"
        out, _ = rewrite_file(src, DICT)
        self.assertIn("new Item.Properties();", out)

    def test_static_member_import_keeps_member(self):
        name = "net.minecraft.state.property.Properties.WATERLOGGED"
        self.assertEqual(
            resolve(DICT, name),
            "net.minecraft.world.level.block.state.properties."
            "BlockStateProperties.WATERLOGGED",
        )

    def test_rewrite_is_idempotent(self):
        src = "import net.minecraft.text.Text;\nText t;\n"
        once, _ = rewrite_file(src, DICT)
        twice, unresolved = rewrite_file(once, DICT)
        self.assertEqual(once, twice)
        self.assertEqual(unresolved, [])

    def test_already_mojang_names_are_not_flagged(self):
        self.assertTrue(already_mojang(DICT, "net.minecraft.network.chat.Component"))
        self.assertFalse(already_mojang(DICT, "net.minecraft.text.Text"))

    def test_unresolved_import_is_reported_and_left_alone(self):
        src = "import net.minecraft.made.Up;\nUp u;\n"
        out, unresolved = rewrite_file(src, DICT)
        self.assertEqual(unresolved, ["net.minecraft.made.Up"])
        self.assertIn("import net.minecraft.made.Up;", out)

    def test_wildcard_expands_to_used_classes_only(self):
        """Wildcard with two classes, only one referenced."""
        dict_with_block = {
            **DICT,
            "net.minecraft.block.Block": "net.minecraft.world.level.block.Block",
            "net.minecraft.block.Fertilizable": "net.minecraft.world.level.block.Fertilizable",
        }
        src = (
            "import net.minecraft.block.*;\n"
            "Block b = null;\n"
        )
        out, unresolved = rewrite_file(src, dict_with_block)
        self.assertIn("import net.minecraft.world.level.block.Block;", out)
        self.assertNotIn("Fertilizable", out)
        self.assertNotIn("*", out)
        self.assertEqual(unresolved, [])

    def test_wildcard_scatters_across_mojang_packages(self):
        """Wildcard package scatters across multiple Mojang packages."""
        dict_with_scatter = {
            **DICT,
            "net.minecraft.block.Block": "net.minecraft.world.level.block.Block",
            "net.minecraft.block.Properties": "net.minecraft.world.level.block.state.properties.BlockStateProperties",
        }
        src = (
            "import net.minecraft.block.*;\n"
            "Block b = null;\n"
            "Properties p = null;\n"
        )
        out, unresolved = rewrite_file(src, dict_with_scatter)
        self.assertIn("import net.minecraft.world.level.block.Block;", out)
        self.assertIn("import net.minecraft.world.level.block.state.properties.BlockStateProperties;", out)
        self.assertIn("BlockStateProperties p = null;", out)
        self.assertEqual(unresolved, [])

    def test_wildcard_conflict_leaves_file_alone(self):
        """Same simple name from two wildcards — file left untouched."""
        dict_with_conflict = {
            **DICT,
            "net.minecraft.block.Item": "net.minecraft.world.level.block.Item",
            "net.minecraft.item.Item": "net.minecraft.world.item.Item",
        }
        src = (
            "import net.minecraft.block.*;\n"
            "import net.minecraft.item.*;\n"
            "Item i = null;\n"
        )
        out, unresolved = rewrite_file(src, dict_with_conflict)
        # Wildcards should remain unchanged
        self.assertIn("import net.minecraft.block.*;", out)
        self.assertIn("import net.minecraft.item.*;", out)
        # Both packages should be reported
        self.assertIn("net.minecraft.block.*", unresolved)
        self.assertIn("net.minecraft.item.*", unresolved)

    def test_wildcard_absent_package(self):
        """Wildcard for package absent from dictionary."""
        src = (
            "import net.minecraft.nonexistent.*;\n"
            "Something s = null;\n"
        )
        out, unresolved = rewrite_file(src, DICT)
        # Wildcard should remain unchanged
        self.assertIn("import net.minecraft.nonexistent.*;", out)
        # Should be reported as unresolved
        self.assertIn("net.minecraft.nonexistent.*", unresolved)

    def test_report_counts_wildcard_as_resolved(self):
        """report should count wildcards in vanilla imports and mark them resolved."""
        dict_with_block = {
            **DICT,
            "net.minecraft.block.Block": "net.minecraft.world.level.block.Block",
            "net.minecraft.block.Blocks": "net.minecraft.world.level.block.Blocks",
        }

        with tempfile.TemporaryDirectory() as tmpdir:
            tmppath = Path(tmpdir)

            # Create a Java file with wildcard import that has used classes
            java_file = tmppath / "TestClass.java"
            java_file.write_text(
                "import net.minecraft.block.*;\n"
                "class TestClass {\n"
                "  Block b;\n"
                "  Blocks bs;\n"
                "}\n"
            )

            # Call _report_impl which is the core of report()
            names, unresolved = _report_impl(dict_with_block, tmppath)

            # The wildcard should be counted as an import
            self.assertIn("net.minecraft.block.*", names)
            # It should NOT be in unresolved because it expands successfully
            self.assertNotIn("net.minecraft.block.*", unresolved)
            # All imports should be resolved
            self.assertEqual(len(names), len(names) - len(unresolved))

    def test_wildcard_nested_class_rename(self):
        """Wildcard-imported class with nested class accessed via dot notation."""
        dict_with_nested = {
            **DICT,
            "net.minecraft.block.AbstractBlock": "net.minecraft.world.level.block.state.BlockBehaviour",
            "net.minecraft.block.AbstractBlock$Settings": "net.minecraft.world.level.block.state.BlockBehaviour$Properties",
        }
        src = (
            "import net.minecraft.block.*;\n"
            "class TestBlock {\n"
            "  void test() {\n"
            "    AbstractBlock.Settings.copy(x);\n"
            "  }\n"
            "}\n"
        )
        out, unresolved = rewrite_file(src, dict_with_nested)
        # Import should be expanded
        self.assertIn("import net.minecraft.world.level.block.state.BlockBehaviour;", out)
        # Nested class MUST be renamed: Settings -> Properties
        self.assertIn("BlockBehaviour.Properties.copy(x);", out)
        # Must NOT contain the wrong name
        self.assertNotIn("BlockBehaviour.Settings", out)
        self.assertEqual(unresolved, [])


if __name__ == "__main__":
    unittest.main()
