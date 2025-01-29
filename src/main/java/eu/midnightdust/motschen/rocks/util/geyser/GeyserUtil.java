package eu.midnightdust.motschen.rocks.util.geyser;

import eu.midnightdust.motschen.rocks.RocksMain;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.lifecycle.GeyserLoadResourcePacksEvent;

import java.net.URI;
import java.nio.file.Path;
import java.util.UUID;

import static eu.midnightdust.motschen.rocks.RocksMain.MOD_ID;

public class GeyserUtil implements EventRegistrar {
    static GeyserApi geyser;

    public static void init(RocksMain mainEntryPoint) {
        //GeyserConnection connection = GeyserApi.api().connectionByUuid(uuid);
        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            geyser = GeyserApi.api();
            EventRegistrar registrar = new GeyserUtil();
            geyser.eventBus().register(registrar, registrar);
            geyser.eventBus().register(registrar, new GeyserBlocks()); // register your mod & this class instance as a listener
        });
    }
    @Subscribe
    public void onGeyserLoadResourcePacksEvent(GeyserLoadResourcePacksEvent event) {
        //logger().info("Loading: " + event.resourcePacks().size() + " resource packs.");
        event.resourcePacks().add(FabricLoader.getInstance().getModContainer(MOD_ID).get().findPath("bedrock/rocks.zip").get());
        // you could add a resource pack with event.resourcePacks().add(path-to-pack)
    }
    public static boolean isOnBedrock(UUID uuid) {
        if (geyser == null) return false;
        return geyser.isBedrockPlayer(uuid);
    }
}
