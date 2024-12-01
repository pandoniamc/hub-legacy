package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.sidebar.SidebarService;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerDisconnectEvent;

public class PlayerDisconnectListener implements EventListener<PlayerDisconnectEvent> {

    private final SidebarService sidebarService;

    public PlayerDisconnectListener(SidebarService sidebarService) {
        this.sidebarService = sidebarService;
    }

    @Override
    public Class<PlayerDisconnectEvent> eventType() {
        return PlayerDisconnectEvent.class;
    }

    @Override
    public Result run(PlayerDisconnectEvent event) {
        Player player = event.getPlayer();

        sidebarService.removeViewer(player);

        return Result.SUCCESS;
    }
}
