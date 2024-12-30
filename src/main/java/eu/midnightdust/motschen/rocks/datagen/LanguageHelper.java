package eu.midnightdust.motschen.rocks.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LanguageHelper {
    JsonObject language;

    public LanguageHelper(String language) {
        try {
            var langFile = MinecraftClient.getInstance().getDefaultResourcePack().open(ResourceType.CLIENT_RESOURCES, Identifier.ofVanilla(String.format("lang/%s.json", language)));
            if (langFile == null) throw new RuntimeException("Unable to load language "+language);
            this.language = new Gson().fromJson(new InputStreamReader(langFile.get(), StandardCharsets.UTF_8), JsonObject.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String translate(String translationKey) {
        return language.get(translationKey).getAsString();
    }
}
