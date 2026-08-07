package eu.midnightdust.motschen.rocks.datagen;

import eu.midnightdust.motschen.rocks.RocksMain;
import eu.midnightdust.motschen.rocks.util.RockType;
import eu.midnightdust.motschen.rocks.util.StickType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class Language extends FabricLanguageProvider {
    LanguageHelper langHelper;

    protected Language(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    protected Language(FabricPackOutput dataOutput, String languageCode, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, languageCode, registryLookup);
    }

    public String getCommonString(String first, String second) {
        // To get the pure wood set name, we compare the names of the log and plank blocks and only keep the common part
        // Kinda cursed, but hey – it works :)
        StringBuilder commonTranslation = new StringBuilder();
        for (String subFirst : first.split(" ")) {
            for (String subSecond : second.split(" ")) {
                String commonPart = "";
                if (!second.contains(" ")) { // This is often the case in German
                    for (char c : subFirst.toCharArray()) {
                        String temp = commonPart + c;
                        if (subSecond.startsWith(temp)) commonPart = temp;
                        else break;
                    }
                }
                else if (subFirst.equals(subSecond)) commonPart = subSecond; // This is common in English

                if (!commonPart.isEmpty()) {
                    commonTranslation.append(commonPart).append(" ");
                    break;
                }
            }
        }
        return commonTranslation.substring(0, commonTranslation.length()-1);
    }

    protected static void addBlock(TranslationBuilder translationBuilder, Block block, String value) {
        translationBuilder.add(block, value);
        translationBuilder.add(block.asItem(), value);
    }

    protected static void midnightconfig(TranslationBuilder translationBuilder, String key, String value) {
        translationBuilder.add("rocks.midnightconfig."+key, value);
    }

    public void createRepeatedTranslations(TranslationBuilder translationBuilder, String rockWord, String splitterWord, String stickWord) {
        for (RockType type : RockType.values()) {
            Block block = BuiltInRegistries.BLOCK.getValue(RocksMain.id(type.getName()));
            String baseTranslation = langHelper.translate(type.getStoneBlock().getDescriptionId());
            addBlock(translationBuilder, block, baseTranslation+rockWord);

            if (type != RockType.GRAVEL) {
                String splitterBaseTranslation = langHelper.translate(type.getFragment().getStoneBlock().getDescriptionId());
                Item splitter = BuiltInRegistries.ITEM.getValue(RocksMain.id(type.getFragment().getName()));
                translationBuilder.add(splitter, splitterBaseTranslation+splitterWord);
            }
        }
        for (StickType type : StickType.values()) {
            Block block = BuiltInRegistries.BLOCK.getValue(RocksMain.id(type.getName()+"_stick"));
            if (type.getBaseBlock() instanceof Block logBlock &&
                    BuiltInRegistries.BLOCK.getValue(Identifier.withDefaultNamespace(type.getName()+"_planks")) instanceof Block plankBlock &&
                    BuiltInRegistries.BLOCK.getValue(Identifier.withDefaultNamespace(type.getName()+"_stairs")) instanceof Block stairBlock) {
                String logTranslation = langHelper.translate(logBlock.getDescriptionId());
                String plankTranslation = langHelper.translate(plankBlock.getDescriptionId());
                String stairTranslation = langHelper.translate(stairBlock.getDescriptionId());

                addBlock(translationBuilder, block, getCommonString(getCommonString(logTranslation, plankTranslation), getCommonString(plankTranslation, stairTranslation)) + stickWord);
            }
        }
    }

    public static class English extends Language {
        public English(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
            langHelper = new LanguageHelper("en_us");
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add("itemGroup.rocks.rocks","This Rocks!");

            createRepeatedTranslations(translationBuilder, " Rock", " Fragment", " Stick");

            addBlock(translationBuilder, RocksMain.Geyser, "Geyser");
            addBlock(translationBuilder, RocksMain.NetherGeyser, "Nether Geyser");
            addBlock(translationBuilder, RocksMain.Starfish, "Starfish");
            addBlock(translationBuilder, RocksMain.Seashell, "Seashell");
            addBlock(translationBuilder, RocksMain.Pinecone, "Pinecone");

            midnightconfig(translationBuilder, "title", "This Rocks! Config");
            midnightconfig(translationBuilder, "category.rocks", "Rocks");
            midnightconfig(translationBuilder, "category.sticks", "Sticks");
            midnightconfig(translationBuilder, "category.misc", "Miscellaneous");
            midnightconfig(translationBuilder, "category.effects", "Effects");

            for (String key : Set.of("needs_restart", "needs_restart1", "needs_restart2")) {
                midnightconfig(translationBuilder, key, "§cRestart the game after changing options here!");
            }

            midnightconfig(translationBuilder, "rockMix", "Mixed Rocks");
            midnightconfig(translationBuilder, "rockMix.tooltip", "Adds small batches of Granite, Andesite and Diorite rocks to all biomes");
            midnightconfig(translationBuilder, "underwaterSeashell", "Underwater Seashell");
            midnightconfig(translationBuilder, "underwaterStarfish", "Underwater Starfish");

            midnightconfig(translationBuilder, "geyserLevitation", "Geyser Levitation");
            midnightconfig(translationBuilder, "netherGeyserDamage", "Nether Geyser Damage");

            midnightconfig(translationBuilder, "biomeExclusions", "Excluded biomes");
            midnightconfig(translationBuilder, "biomeExclusions.tooltip", "No feature will generate in these biomes.\nFormat: modid:biome_name");

            midnightconfig(translationBuilder, "enablePolymerMode", "Enable Polymer Mode");
            midnightconfig(translationBuilder, "enablePolymerMode.tooltip", "Allows the mod to work fully server-sided when used in combination with Polymer and FactoryTools");
            midnightconfig(translationBuilder, "forcePolymerMode", "Force Polymer Mode");
            midnightconfig(translationBuilder, "forcePolymerMode.tooltip", "Also enables Polymer mode for clients that have ThisRocks! installed");
            midnightconfig(translationBuilder, "polymerViewDistance", "Polymer View Distance");
        }
    }
    public static class German extends Language {
        public German(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, "de_de", registryLookup);
            langHelper = new LanguageHelper("de_de");
        }

        @Override
        public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add("itemGroup.rocks.rocks","This Rocks!");

            createRepeatedTranslations(translationBuilder, "brocken", "splitter", "stock");

            addBlock(translationBuilder, RocksMain.Geyser, "Geysir");
            addBlock(translationBuilder, RocksMain.NetherGeyser, "Nether-Geysir");
            addBlock(translationBuilder, RocksMain.Starfish, "Seestern");
            addBlock(translationBuilder, RocksMain.Seashell, "Muschel");
            addBlock(translationBuilder, RocksMain.Pinecone, "Tannenzapfen");


            midnightconfig(translationBuilder, "title", "This Rocks! Config");
            midnightconfig(translationBuilder, "category.rocks", "Brocken");
            midnightconfig(translationBuilder, "category.sticks", "Stöcke");
            midnightconfig(translationBuilder, "category.misc", "Sonstiges");
            midnightconfig(translationBuilder, "category.effects", "Effekte");

            for (String key : Set.of("needs_restart", "needs_restart1", "needs_restart2")) {
                midnightconfig(translationBuilder, key, "§cStarte das Spiel neu, nachdem du Änderungen vorgenommen hast!");
            }

            midnightconfig(translationBuilder, "rockMix", "Gemischte Brocken");
            midnightconfig(translationBuilder, "rockMix.tooltip", "Fügt kleine Mengen an Granit-, Andesit- und Diorit-Brocken zu allen Biomen hinzu");
            midnightconfig(translationBuilder, "underwaterSeashell", "Unterwasser-Muschel");
            midnightconfig(translationBuilder, "underwaterStarfish", "Unterwasser-Seestern");

            midnightconfig(translationBuilder, "geyserLevitation", "Geysir Schwebeeffekt");
            midnightconfig(translationBuilder, "netherGeyserDamage", "Nether Geysir Schaden");

            midnightconfig(translationBuilder, "biomeExclusions", "Ausgeschlosene Biome");
            midnightconfig(translationBuilder, "biomeExclusions.tooltip", "In diesen Biomen werden keine Features der Mod generiert.\nFormat: modid:biome_name");

            midnightconfig(translationBuilder, "enablePolymerMode", "Aktiviere Polymer-Modus");
            midnightconfig(translationBuilder, "enablePolymerMode.tooltip", "Erlaubt der Mod, komplett serverseitig zu funktionieren, wenn Polymer und FactoryTools installiert sind");
            midnightconfig(translationBuilder, "forcePolymerMode", "Erzwinge Polymer-Modus");
            midnightconfig(translationBuilder, "forcePolymerMode.tooltip", "Aktiviert den Polymer-Modus auch für Clients, die die ThisRocks! installiert haben");
            midnightconfig(translationBuilder, "polymerViewDistance", "Polymer-Sichtweite");
        }
    }
}
