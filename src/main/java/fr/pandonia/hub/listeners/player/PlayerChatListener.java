package fr.pandonia.hub.listeners.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerChatEvent;

public class PlayerChatListener implements EventListener<PlayerChatEvent> {

    @Override
    public Class<PlayerChatEvent> eventType() {
        return PlayerChatEvent.class;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Result run(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getRawMessage();

        event.setFormattedMessage(
                Component.text()
                    .append(player.getDisplayName())
                    .appendSpace()
                    .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                    .appendSpace()
                    .append(Component.text(message))
                    .build()
        );

        return Result.SUCCESS;
    }
}
