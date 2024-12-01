package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.events.PlayerTeleportEvent;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;

public class PlayerTeleportListener implements EventListener<PlayerTeleportEvent> {

    @Override
    public Class<PlayerTeleportEvent> eventType() {
        return PlayerTeleportEvent.class;
    }

    @Override
    public Result run(PlayerTeleportEvent event) {
        Player player = event.player();
        PlayerTeleportEvent.Location location = event.location();

        Pos pos = switch (location) {
            case JUMP -> new Pos(0, 100, 0);
        };

        player.teleport(pos);

        return Result.SUCCESS;
    }
}
