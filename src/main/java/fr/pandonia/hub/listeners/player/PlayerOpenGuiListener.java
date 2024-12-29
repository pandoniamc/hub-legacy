package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.game.Game;
import fr.pandonia.hub.api.game.GameService;
import fr.pandonia.hub.events.PlayerOpenGuiEvent;
import fr.pandonia.hub.guis.MainGui;
import fr.pandonia.hub.guis.profile.*;
import fr.pandonia.hub.player.HubPlayer;
import net.minestom.server.event.EventListener;

import java.util.Map;

public class PlayerOpenGuiListener implements EventListener<PlayerOpenGuiEvent> {

    private final GameService gameService;

    public PlayerOpenGuiListener(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Class<PlayerOpenGuiEvent> eventType() {
        return PlayerOpenGuiEvent.class;
    }

    @Override
    public Result run(PlayerOpenGuiEvent event) {
        HubPlayer player = (HubPlayer) event.player();
        PlayerOpenGuiEvent.GuiType type = event.type();

        switch (type) {
            case MAIN -> {
                Map<Game, Integer> playerCount = gameService.getPlayerCount();
                player.openInventory(new MainGui(player, playerCount));
            }
            case PROFILE -> player.openInventory(new ProfileGui(player));
            case FRIENDS -> player.openInventory(new FriendsGui());
            case GUILD -> player.openInventory(new GuildGui());
            case PARTY -> player.openInventory(new PartyGui());
            case BLOCKED_PLAYERS -> player.openInventory(new BlockedPlayersGui());
        }

        return Result.SUCCESS;
    }
}
