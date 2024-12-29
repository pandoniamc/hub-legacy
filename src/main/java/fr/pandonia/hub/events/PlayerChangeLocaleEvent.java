package fr.pandonia.hub.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

import java.util.Locale;

public record PlayerChangeLocaleEvent(Player player, Locale locale) implements Event {

}
