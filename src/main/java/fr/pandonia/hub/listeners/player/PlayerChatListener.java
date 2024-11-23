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

    @Override
    public Result run(PlayerChatEvent event) {
        event.setChatFormat(e -> {
            Player player = e.getPlayer();
            String message = e.getMessage();

            //noinspection DataFlowIssue
            return Component.text()
                    .append(player.getDisplayName())
                    .appendSpace()
                    .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                    .appendSpace()
                    .append(Component.text(message))
                    .build();
        });

        return Result.SUCCESS;
    }
}
