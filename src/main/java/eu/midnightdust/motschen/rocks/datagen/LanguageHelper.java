package eu.midnightdust.motschen.rocks.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

public class LanguageHelper {
    JsonObject language;

    public LanguageHelper(String language) {
        try {
            var langFile = Minecraft.getInstance().getVanillaPackResources().getResource(PackType.CLIENT_RESOURCES, Identifier.withDefaultNamespace(String.format("lang/%s.json", language)));
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
