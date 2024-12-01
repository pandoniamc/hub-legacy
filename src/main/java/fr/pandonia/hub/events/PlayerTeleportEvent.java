package fr.pandonia.hub.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

public record PlayerTeleportEvent(Player player, Location location) implements Event {

    public enum Location {
        JUMP
    }
}
