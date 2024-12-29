package fr.pandonia.hub;

import fr.pandonia.hub.api.configuration.PropertiesConfiguration;
import fr.pandonia.hub.api.game.GameService;
import fr.pandonia.hub.api.game.GameServiceImpl;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.player.PlayerServiceImpl;
import fr.pandonia.hub.api.server.ServerService;
import fr.pandonia.hub.api.server.ServerServiceImpl;
import fr.pandonia.hub.api.sql.HikariConnectionProvider;
import fr.pandonia.hub.api.sql.SqlCredentials;
import fr.pandonia.hub.listeners.item.ItemDropListener;
import fr.pandonia.hub.listeners.player.*;
import fr.pandonia.hub.player.HubPlayerProvider;
import fr.pandonia.hub.sidebar.SidebarService;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.network.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        Instance instance = new Instance();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        instanceManager.registerInstance(instance);

        PropertiesConfiguration configuration = new PropertiesConfiguration();

        try {
            configuration.load();
        } catch (Exception e) {
            LOGGER.error("Failed to load configuration", e);
        }

        SqlCredentials sqlCredentials = SqlCredentials.fromConfiguration(configuration);

        HikariConnectionProvider connectionProvider = new HikariConnectionProvider(sqlCredentials);
        new Thread(connectionProvider::open).start();

        Runtime.getRuntime().addShutdownHook(new Thread(connectionProvider::close));

        GameService gameService = new GameServiceImpl();
        PlayerService playerService = new PlayerServiceImpl(connectionProvider);
        ServerService serverService = new ServerServiceImpl(instance);

        SidebarService sidebarService = new SidebarService(serverService);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(new ItemDropListener());
        globalEventHandler.addListener(new PlayerBlockInteractListener());
        globalEventHandler.addListener(new PlayerBlockPlaceListener());
        globalEventHandler.addListener(new PlayerChangeLocaleListener(playerService));
        globalEventHandler.addListener(new PlayerChatListener());
        globalEventHandler.addListener(new PlayerConfigurationListener(instance));
        globalEventHandler.addListener(new PlayerDisconnectListener(sidebarService));
        globalEventHandler.addListener(new PlayerOpenGuiListener(gameService));
        globalEventHandler.addListener(new PlayerSpawnListener(sidebarService));
        globalEventHandler.addListener(new PlayerTeleportListener());
        globalEventHandler.addListener(new PlayerUseItemListener());

        ConnectionManager connectionManager = MinecraftServer.getConnectionManager();
        connectionManager.setPlayerProvider(new HubPlayerProvider(playerService));

        MojangAuth.init();

        server.start(configuration.getServerHost(), configuration.getServerPort());

        LOGGER.info("Server started on {}:{}", configuration.getServerHost(), configuration.getServerPort());
    }
}
