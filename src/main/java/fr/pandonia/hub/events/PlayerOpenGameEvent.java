package fr.pandonia.hub.events;

import fr.pandonia.hub.api.game.Game;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

public record PlayerOpenGameEvent(Player player, Game game) implements Event {

}
