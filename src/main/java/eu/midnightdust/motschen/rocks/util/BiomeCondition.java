package eu.midnightdust.motschen.rocks.util;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public record BiomeCondition(boolean includeByDefault, Set<Identifier> validIds, Set<String> validRegexes, Set<TagKey<Biome>> validTags, Set<Predicate<Biome>> validPredicates, Set<String> invalidRegexes, Set<TagKey<Biome>> invalidTags) {
    public static BiomeConditionBuilder builder() {
        return new BiomeConditionBuilder();
    }

    public static class BiomeConditionBuilder {
        private final Set<Identifier> validIds = new HashSet<>();
        private final Set<String> validRegexes = new HashSet<>();
        private final Set<TagKey<Biome>> validTags = new HashSet<>();
        private final Set<Predicate<Biome>> validPredicates = new HashSet<>();
        private final Set<TagKey<Biome>> invalidTags = new HashSet<>();
        private final Set<String> invalidRegexes = new HashSet<>();

        public BiomeConditionBuilder acceptBiome(Identifier biomeId) {
            validIds.add(biomeId);
            return this;
        }

        public BiomeConditionBuilder acceptBiome(ResourceKey<Biome> biomeId) {
            validIds.add(biomeId.identifier());
            return this;
        }

        public BiomeConditionBuilder acceptBiomeRegex(String biomeRegex) {
            validRegexes.add(biomeRegex);
            return this;
        }

        public BiomeConditionBuilder acceptTag(TagKey<Biome> biomeTag) {
            validTags.add(biomeTag);
            return this;
        }

        public BiomeConditionBuilder acceptPredicate(Predicate<Biome> predicate) {
            validPredicates.add(predicate);
            return this;
        }

        public BiomeConditionBuilder rejectBiomeRegex(String biomeRegex) {
            invalidRegexes.add(biomeRegex);
            return this;
        }

        public BiomeConditionBuilder rejectTag(TagKey<Biome> biomeTag) {
            invalidTags.add(biomeTag);
            return this;
        }

        public BiomeCondition build() {
            boolean includeByDefault = validIds.isEmpty() && validRegexes.isEmpty() && validTags.isEmpty() && validPredicates.isEmpty();
            return new BiomeCondition(includeByDefault, validIds, validRegexes, validTags, validPredicates, invalidRegexes, invalidTags);
        }
    }
}