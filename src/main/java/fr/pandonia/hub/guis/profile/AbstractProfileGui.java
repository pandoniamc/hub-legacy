package fr.pandonia.hub.guis.profile;

import fr.pandonia.hub.events.PlayerOpenGuiEvent;
import fr.pandonia.hub.utils.ComponentUtils;
import fr.pandonia.hub.utils.Gui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractProfileGui extends Gui {

    public AbstractProfileGui(SubGui gui) {
        super(InventoryType.CHEST_6_ROW, gui.title,
                new Background(
                        new int[]{
                                0, 1, 7, 8,
                                9, 17,
                                36, 44,
                                45, 46, 52, 53
                        },
                        gui.background
                )
        );

        for (int i = 0; i < SubGui.values().length; i++) {
            SubGui subGui = SubGui.values()[i];
            setItemStack(2 + i, subGui.asItemStack(), subGui.guiType);
            setItemStack(9 + 2 + i,
                    ItemStack.builder(
                            subGui == gui
                                    ? Material.GREEN_STAINED_GLASS_PANE
                                    : Material.GRAY_STAINED_GLASS_PANE
                    )
                            .customName(Component.empty())
                            .build()
            );
        }
    }

    public enum SubGui {

        PROFILE("Profil", Material.BLUE_STAINED_GLASS_PANE, Material.PLAYER_HEAD, NamedTextColor.DARK_AQUA, PlayerOpenGuiEvent.GuiType.PROFILE,
                "Consultez vos statistiques,",
                "vos succès et configurez",
                "votre expérience de jeu."),
        FRIENDS("Amis", Material.GREEN_STAINED_GLASS_PANE, Material.CAKE, NamedTextColor.GREEN, PlayerOpenGuiEvent.GuiType.FRIENDS,
                "Ajoutez un joueur à votre liste d'amis",
                "ou interagissez avec vos amis."),
        GUILD("Guilde", Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.WHITE_BANNER, NamedTextColor.AQUA, PlayerOpenGuiEvent.GuiType.GUILD,
                "Créez, rejoignez une guilde",
                "ou consultez les informations",
                "de votre guilde."),
        PARTY("Partie", Material.ORANGE_STAINED_GLASS_PANE, Material.TOTEM_OF_UNDYING, NamedTextColor.GOLD, PlayerOpenGuiEvent.GuiType.PARTY,
                "Créez une partie et invitez",
                "des joueurs pour jouer ensemble."),
        BLOCKED("Joueurs bloqués", Material.RED_STAINED_GLASS_PANE, Material.BARRIER, NamedTextColor.RED, PlayerOpenGuiEvent.GuiType.BLOCKED_PLAYERS,
                "Consultez la liste des joueurs",
                "que vous avez bloqués.");

        private final String title;
        private final Material background;
        private final Material material;
        private final TextColor color;
        private final PlayerOpenGuiEvent.GuiType guiType;
        private final String[] description;

        SubGui(String title, Material background, Material material, TextColor color, PlayerOpenGuiEvent.GuiType guiType, String... description) {
            this.title = title;
            this.background = background;
            this.material = material;
            this.color = color;
            this.guiType = guiType;
            this.description = description;
        }

        public ItemStack asItemStack() {
            List<Component> lore = new ArrayList<>(
                    Stream.of(description)
                            .map(line -> ComponentUtils.stripItalic(Component.text(line, NamedTextColor.GRAY)))
                            .toList()
            );
            lore.add(Component.empty());
            lore.add(
                    ComponentUtils.stripItalic(
                            Component.text()
                                    .append(Component.text("»", NamedTextColor.DARK_AQUA, TextDecoration.BOLD))
                                    .appendSpace()
                                    .append(Component.text("Cliquez pour changer de menu", NamedTextColor.AQUA))
                                    .build()
                    )
            );

            return ItemStack.builder(material)
                    .customName(ComponentUtils.stripItalic(Component.text(title, color, TextDecoration.BOLD)))
                    .lore(lore)
                    .build();
        }
    }
}
