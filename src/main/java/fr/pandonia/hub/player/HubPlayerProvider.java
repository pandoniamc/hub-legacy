package fr.pandonia.hub.player;

import fr.pandonia.hub.api.group.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.network.PlayerProvider;
import net.minestom.server.network.player.PlayerConnection;

import java.util.UUID;

public class HubPlayerProvider implements PlayerProvider {

    @Override
    public Player createPlayer(UUID uuid, String username, PlayerConnection connection) {
        return new HubPlayer(new HubPlayer.Identifier(uuid, username), Group.DEFAULT, connection);
    }
}
