package fr.pandonia.hub;

import fr.pandonia.hub.api.game.GameService;
import fr.pandonia.hub.api.game.GameServiceImpl;
import fr.pandonia.hub.listeners.item.ItemDropListener;
import fr.pandonia.hub.listeners.player.*;
import fr.pandonia.hub.player.HubPlayerProvider;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.network.ConnectionManager;

public class Main {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 25565;

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        Instance instance = new Instance();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        instanceManager.registerInstance(instance);

        GameService gameService = new GameServiceImpl();

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(new ItemDropListener());
        globalEventHandler.addListener(new PlayerBlockInteractListener());
        globalEventHandler.addListener(new PlayerBlockPlaceListener());
        globalEventHandler.addListener(new PlayerChatListener());
        globalEventHandler.addListener(new PlayerConfigurationListener(instance));
        globalEventHandler.addListener(new PlayerOpenGuiListener(gameService));
        globalEventHandler.addListener(new PlayerSpawnListener());
        globalEventHandler.addListener(new PlayerTeleportListener());
        globalEventHandler.addListener(new PlayerUseItemListener());

        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        connectionManager.setPlayerProvider(new HubPlayerProvider());

        MojangAuth.init();

        server.start(HOST, PORT);
    }
}
