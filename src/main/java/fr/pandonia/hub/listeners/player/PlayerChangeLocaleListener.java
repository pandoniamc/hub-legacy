package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.events.PlayerChangeLocaleEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;

import java.util.Locale;

public class PlayerChangeLocaleListener implements EventListener<PlayerChangeLocaleEvent> {

    private final PlayerService playerService;

    public PlayerChangeLocaleListener(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public Class<PlayerChangeLocaleEvent> eventType() {
        return PlayerChangeLocaleEvent.class;
    }

    @Override
    public Result run(PlayerChangeLocaleEvent event) {
        Player player = event.player();
        Locale locale = event.locale();

        player.setLocale(locale);
        playerService.updateLocale(player.getUuid(), locale);

        return Result.SUCCESS;
    }
}
