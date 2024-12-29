package fr.pandonia.hub.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

public record PlayerOpenGuiEvent(Player player, GuiType type) implements Event {

    public enum GuiType {
        MAIN,
        PROFILE,
        SHOP,
        COSMETICS,
        HUB_SELECTOR,
        FRIENDS,
        GUILD,
        PARTY,
        BLOCKED_PLAYERS,
        ACHIEVEMENTS,
        CHALLENGES,
        BOOSTERS,
        SETTINGS,
    }
}
