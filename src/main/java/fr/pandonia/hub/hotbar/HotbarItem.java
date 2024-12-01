package fr.pandonia.hub.hotbar;

import fr.pandonia.hub.events.PlayerOpenGuiEvent;
import fr.pandonia.hub.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.HeadProfile;

import java.util.Arrays;
import java.util.Optional;

public enum HotbarItem {

    COMPASS("Menu principal", NamedTextColor.GREEN, Material.COMPASS) {
        @Override
        public void use(Player player) {
            MinecraftServer.getGlobalEventHandler().call(new PlayerOpenGuiEvent(player, PlayerOpenGuiEvent.GuiType.MAIN));
        }
    },
    PROFILE( "Profil", NamedTextColor.DARK_AQUA, Material.PLAYER_HEAD) {
        @Override
        public void use(Player player) {
            MinecraftServer.getGlobalEventHandler().call(new PlayerOpenGuiEvent(player, PlayerOpenGuiEvent.GuiType.PROFILE));
        }

        @Override
        public ItemStack asItemStack(Player player, DisplayType type) {
            ItemStack item = super.asItemStack(player, type);
            PlayerSkin skin = player.getSkin();

            if (skin != null) {
                // Should always be true as the server is in online mode
                item = item.with(ItemComponent.PROFILE, new HeadProfile(skin));
            }

            return item;
        }
    },
    SHOP("Boutique", NamedTextColor.GOLD, Material.GOLD_INGOT) {
        @Override
        public void use(Player player) {
            MinecraftServer.getGlobalEventHandler().call(new PlayerOpenGuiEvent(player, PlayerOpenGuiEvent.GuiType.SHOP));
        }
    },
    COSMETICS( "Cosmétiques", NamedTextColor.LIGHT_PURPLE, Material.CHEST) {
        @Override
        public void use(Player player) {
            MinecraftServer.getGlobalEventHandler().call(new PlayerOpenGuiEvent(player, PlayerOpenGuiEvent.GuiType.COSMETICS));
        }
    },
    JUMP("Jump", NamedTextColor.YELLOW, Material.FEATHER) {
        @Override
        public void use(Player player) {
            player.teleport(player.getPosition().add(0, 5, 0));
        }
    },
    HUB_SELECTOR("Hub", NamedTextColor.AQUA, Material.NETHER_STAR) {
        @Override
        public void use(Player player) {
            MinecraftServer.getGlobalEventHandler().call(new PlayerOpenGuiEvent(player, PlayerOpenGuiEvent.GuiType.HUB_SELECTOR));
        }
    };

    private final String name;
    private final TextColor color;
    private final Material material;

    HotbarItem(String name, TextColor color, Material material) {
        this.material = material;
        this.name = name;
        this.color = color;
    }

    public static Optional<HotbarItem> fromMaterial(Material material) {
        return Arrays.stream(values())
                .filter(item -> item.material == material)
                .findFirst();
    }

    public abstract void use(Player player);

    public ItemStack asItemStack(Player player, DisplayType type) {
        return ItemStack.builder(material)
                .customName(type.getCustomName(this))
                .build();
    }

    public enum DisplayType {
        HOTBAR {
            @Override
            public Component getCustomName(HotbarItem item) {
                return ComponentUtils.stripItalic(
                        Component.text()
                                .append(Component.text(item.name, item.color, TextDecoration.BOLD))
                                .appendSpace()
                                .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                                .appendSpace()
                                .append(Component.text("Clic droit", NamedTextColor.GRAY))
                                .build()
                );
            }
        },
        GUI {
            @Override
            public Component getCustomName(HotbarItem item) {
                return ComponentUtils.stripItalic(Component.text(item.name, item.color, TextDecoration.BOLD));
            }
        };

        public abstract Component getCustomName(HotbarItem item);
    }
}
