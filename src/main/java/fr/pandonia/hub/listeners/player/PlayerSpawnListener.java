package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.hotbar.HotbarItem;
import fr.pandonia.hub.player.HubPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.inventory.condition.InventoryCondition;

import java.util.Map;

public class PlayerSpawnListener implements EventListener<PlayerSpawnEvent> {

    private static final InventoryCondition CANCEL_ITEM_MOVEMENT = (player, slot, clickType, inventoryConditionResult) -> inventoryConditionResult.setCancel(true);

    @Override
    public Class<PlayerSpawnEvent> eventType() {
        return PlayerSpawnEvent.class;
    }

    @Override
    public Result run(PlayerSpawnEvent event) {
        HubPlayer player = (HubPlayer) event.getPlayer();

        // Hotbar
        Map.of(
                0, HotbarItem.COMPASS,
                1, HotbarItem.PROFILE,
                2, HotbarItem.SHOP,
                4, HotbarItem.COSMETICS,
                7, HotbarItem.JUMP,
                8, HotbarItem.HUB_SELECTOR
        ).forEach((slot, item) -> player.getInventory().setItemStack(slot, item.asItemStack(player, HotbarItem.DisplayType.HOTBAR)));

        // Prevent the player from moving the items in the hotbar
        player.getInventory().addInventoryCondition(CANCEL_ITEM_MOVEMENT);

        player.setDisplayName(player.getGroup().getCustomName(player.getUsername()));

        // Player list
        player.sendPlayerListHeaderAndFooter(
                Component.text()
                        .append(Component.text("Pandonia", NamedTextColor.BLUE, TextDecoration.BOLD))
                        .appendNewline(),
                Component.text()
                        .appendNewline()
                        .append(Component.text("mc.pandonia.fr", NamedTextColor.GRAY))
        );

        return Result.SUCCESS;
    }
}
