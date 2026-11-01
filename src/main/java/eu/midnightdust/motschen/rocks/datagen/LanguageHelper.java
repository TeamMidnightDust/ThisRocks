package eu.midnightdust.motschen.rocks.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

public class LanguageHelper {

    public LanguageHelper(String language) {
    }

    public String translate(String translationKey) {
        return Language.DEFAULT_INSTANCE.getOrDefault(translationKey);
    }
}
