package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.inventory.condition.InventoryCondition;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class PlayerSpawnListener implements EventListener<PlayerSpawnEvent> {

    private static final InventoryCondition CANCEL_ITEM_MOVEMENT = (player, slot, clickType, inventoryConditionResult) -> inventoryConditionResult.setCancel(true);

    @Override
    public Class<PlayerSpawnEvent> eventType() {
        return PlayerSpawnEvent.class;
    }

    @Override
    public Result run(PlayerSpawnEvent event) {
        Player player = event.getPlayer();

        for (HotbarItem item : HotbarItem.values()) {
            player.getInventory().setItemStack(item.slot, item.asItemStack());
        }

        // Prevent the player from moving the items in the hotbar
        player.getInventory().addInventoryCondition(CANCEL_ITEM_MOVEMENT);

        return Result.SUCCESS;
    }

    private enum HotbarItem {

        COMPASS(0, Material.COMPASS, "Menu principal", NamedTextColor.GREEN),
        PROFILE(1, Material.PLAYER_HEAD, "Profil", NamedTextColor.GOLD),
        COSMETICS(4, Material.CHEST, "Cosmétiques", NamedTextColor.LIGHT_PURPLE),
        JUMP(7, Material.FEATHER, "Jump", NamedTextColor.YELLOW),
        HUB_SELECTOR(8, Material.NETHER_STAR, "Hub", NamedTextColor.AQUA);

        private final int slot;
        private final Material material;
        private final String name;
        private final TextColor color;

        HotbarItem(int slot, Material material, String name, TextColor color) {
            this.slot = slot;
            this.material = material;
            this.name = name;
            this.color = color;
        }

        public ItemStack asItemStack() {
            return ItemStack.builder(material)
                    .customName(
                            ComponentUtils.stripItalic(
                                    Component.text()
                                            .append(Component.text(name, color, TextDecoration.BOLD))
                                            .appendSpace()
                                            .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                                            .appendSpace()
                                            .append(Component.text("Clic droit", NamedTextColor.GRAY))
                                            .build()
                            )
                    )
                    .build();
        }
    }
}
