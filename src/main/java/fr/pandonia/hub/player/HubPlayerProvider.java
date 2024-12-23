package fr.pandonia.hub.player;

import fr.pandonia.hub.api.economy.Purse;
import fr.pandonia.hub.api.group.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.network.PlayerProvider;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;

public class HubPlayerProvider implements PlayerProvider {

    @Override
    public Player createPlayer(PlayerConnection connection, GameProfile gameProfile) {
        return new HubPlayer(connection, gameProfile, Group.DEFAULT, new Purse(0));
    }
}
