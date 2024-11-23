package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.player.HubPlayer;
import fr.pandonia.hub.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.inventory.condition.InventoryCondition;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.HeadProfile;

public class PlayerSpawnListener implements EventListener<PlayerSpawnEvent> {

    private static final InventoryCondition CANCEL_ITEM_MOVEMENT = (player, slot, clickType, inventoryConditionResult) -> inventoryConditionResult.setCancel(true);

    @Override
    public Class<PlayerSpawnEvent> eventType() {
        return PlayerSpawnEvent.class;
    }

    @Override
    public Result run(PlayerSpawnEvent event) {
        HubPlayer player = (HubPlayer) event.getPlayer();

        for (HotbarItem item : HotbarItem.values()) {
            player.getInventory().setItemStack(item.slot, item.asItemStack(player));
        }

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

    private enum HotbarItem {

        COMPASS(0, Material.COMPASS, "Menu principal", NamedTextColor.GREEN),
        PROFILE(1, Material.PLAYER_HEAD, "Profil", NamedTextColor.GOLD) {
            @Override
            public ItemStack asItemStack(Player player) {
                ItemStack item = super.asItemStack(player);
                PlayerSkin skin = player.getSkin();

                if (skin != null) {
                    // Should always be true as the server is in online mode
                    item = item.with(ItemComponent.PROFILE, new HeadProfile(skin));
                }

                return item;
            }
        },
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

        public ItemStack asItemStack(Player player) {
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
