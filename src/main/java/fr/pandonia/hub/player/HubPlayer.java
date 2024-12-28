package fr.pandonia.hub.player;

import fr.pandonia.hub.api.player.PlayerData;
import fr.pandonia.hub.api.player.Purse;
import fr.pandonia.hub.api.group.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;

public class HubPlayer extends Player {

    private final PlayerData data;

    public HubPlayer(PlayerConnection playerConnection, GameProfile gameProfile, PlayerData data) {
        super(playerConnection, gameProfile);

        this.data = data;
    }

    public Group getGroup() {
        return data.group();
    }

    public Purse getPurse() {
        return data.purse();
    }
}
