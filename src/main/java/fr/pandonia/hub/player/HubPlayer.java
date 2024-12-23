package fr.pandonia.hub.player;

import fr.pandonia.hub.api.economy.Purse;
import fr.pandonia.hub.api.group.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;

public class HubPlayer extends Player {

    private final Group group;
    private final Purse purse;

    public HubPlayer(PlayerConnection playerConnection, GameProfile gameProfile, Group group, Purse purse) {
        super(playerConnection, gameProfile);

        this.group = group;
        this.purse = purse;
    }

    public Group getGroup() {
        return group;
    }

    public Purse getPurse() {
        return purse;
    }
}
