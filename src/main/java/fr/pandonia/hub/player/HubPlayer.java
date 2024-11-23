package fr.pandonia.hub.player;

import fr.pandonia.hub.api.group.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;

import java.util.UUID;

public class HubPlayer extends Player {

    private final Group group;

    public HubPlayer(Identifier identifier, Group group, PlayerConnection playerConnection) {
        super(identifier.uuid(), identifier.username(), playerConnection);

        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public record Identifier(UUID uuid, String username) {

    }
}
