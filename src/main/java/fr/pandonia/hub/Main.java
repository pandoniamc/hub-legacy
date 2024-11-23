package fr.pandonia.hub;

import fr.pandonia.hub.listeners.PlayerConfigurationListener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.instance.InstanceManager;

public class Main {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 25565;

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        Instance instance = new Instance();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        instanceManager.registerInstance(instance);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(new PlayerConfigurationListener(instance));

        server.start(HOST, PORT);
    }
}
