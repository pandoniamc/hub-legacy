package fr.pandonia.hub.player;

import fr.pandonia.hub.api.economy.Purse;
import fr.pandonia.hub.api.group.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;

import java.util.UUID;

public class HubPlayer extends Player {

    private final Group group;
    private final Purse purse;

    public HubPlayer(Identifier identifier, Group group, Purse purse, PlayerConnection playerConnection) {
        super(identifier.uuid(), identifier.username(), playerConnection);

        this.group = group;
        this.purse = purse;
    }

    public Group getGroup() {
        return group;
    }

    public Purse getPurse() {
        return purse;
    }

    public record Identifier(UUID uuid, String username) {

    }
}
