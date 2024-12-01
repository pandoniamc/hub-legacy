package fr.pandonia.hub.listeners.player;

import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;

public class PlayerBlockPlaceListener implements EventListener<PlayerBlockPlaceEvent> {

    @Override
    public Class<PlayerBlockPlaceEvent> eventType() {
        return PlayerBlockPlaceEvent.class;
    }

    @Override
    public Result run(PlayerBlockPlaceEvent event) {
        event.setCancelled(true);

        return Result.SUCCESS;
    }
}
