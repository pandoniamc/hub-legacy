package fr.pandonia.hub.player;

import fr.pandonia.hub.api.player.PlayerData;
import fr.pandonia.hub.api.player.PlayerService;
import net.minestom.server.entity.Player;
import net.minestom.server.network.PlayerProvider;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;

import java.util.UUID;

public class HubPlayerProvider implements PlayerProvider {
    
    private final PlayerService playerService;

    public HubPlayerProvider(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public Player createPlayer(PlayerConnection connection, GameProfile gameProfile) {
        UUID id = gameProfile.uuid();
        PlayerData data = playerService.getData(id);

        return new HubPlayer(connection, gameProfile, data);
    }
}
