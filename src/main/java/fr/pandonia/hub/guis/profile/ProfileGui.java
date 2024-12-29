package fr.pandonia.hub.guis.profile;

import fr.pandonia.hub.events.PlayerChangeLocaleEvent;
import fr.pandonia.hub.player.HubPlayer;
import fr.pandonia.hub.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.HeadProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class ProfileGui extends AbstractProfileGui {

    private static final List<Locale> LOCALES = List.of(
            Locale.FRENCH,
            Locale.ENGLISH
    );

    @SuppressWarnings("DataFlowIssue")
    public ProfileGui(HubPlayer player) {
        super(SubGui.PROFILE);

        setItemStack(22, ItemStack.builder(Material.PLAYER_HEAD)
                .customName(ComponentUtils.stripItalic(player.getName().color(NamedTextColor.DARK_AQUA).decorate(TextDecoration.BOLD)))
                .lore(
                        ComponentUtils.stripItalic(
                                Component.text()
                                        .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                                        .appendSpace()
                                        .append(Component.text("Grade", NamedTextColor.WHITE))
                                        .appendSpace()
                                        .append(Component.text(":", NamedTextColor.GRAY))
                                        .appendSpace()
                                        .append(player.getGroup().getCustomName())
                                        .build()
                        ),
                        ComponentUtils.stripItalic(
                                Component.text()
                                        .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                                        .appendSpace()
                                        .append(Component.text("Coins", NamedTextColor.WHITE))
                                        .appendSpace()
                                        .append(Component.text(":", NamedTextColor.GRAY))
                                        .appendSpace()
                                        .append(ComponentUtils.getCoins(player.getPurse().getCoins()))
                                        .build()
                        )
                )
                .set(ItemComponent.PROFILE, new HeadProfile(player.getSkin()))
                .build()
        );

        setItemStack(30, buildGuiItem(Material.ITEM_FRAME, "Succès", NamedTextColor.GOLD, "Consultez vos succès."));
        setItemStack(32, buildGuiItem(Material.TARGET, "Défis", NamedTextColor.YELLOW, "Consultez les défis quotidiens."));

        setItemStack(39, buildGuiItem(Material.EXPERIENCE_BOTTLE, "Boosters", NamedTextColor.AQUA, "Activez les boosters que", "vous possédez."));
        setItemStack(40, buildGuiItem(Material.COMPARATOR, "Paramètres", NamedTextColor.DARK_GRAY, "Modifiez vos paramètres de jeu."));
        refreshLocale(player);
    }

    private void refreshLocale(HubPlayer player) {
        setItemStack(41, ItemStack.builder(Material.PLAYER_HEAD)
                        .customName(ComponentUtils.stripItalic(Component.text("Langue", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD)))
                        .lore(buildLanguageLore(player))
                        .build(),
                p -> {
                    int currentLocaleIndex = LOCALES.indexOf(p.getLocale());
                    Locale newLocale = LOCALES.get((currentLocaleIndex + 1) % LOCALES.size());
                    MinecraftServer.getGlobalEventHandler().call(new PlayerChangeLocaleEvent(player, newLocale));
                    refreshLocale(player);
                }
        );
    }

    private ItemStack buildGuiItem(Material material, String name, TextColor color, String... description) {
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
                                .append(Component.text("Cliquez pour y accéder", NamedTextColor.AQUA))
                                .build()
                )
        );

        return ItemStack.builder(material)
                .customName(ComponentUtils.stripItalic(Component.text(name, color, TextDecoration.BOLD)))
                .lore(lore)
                .build();
    }

    private List<Component> buildLanguageLore(HubPlayer player) {
        return LOCALES
                .stream()
                .map(locale ->
                        ComponentUtils.stripItalic(
                                Component.text()
                                        .append(Component.text("▪"))
                                        .appendSpace()
                                        .append(Component.text(locale.getLanguage()))
                                        .color(locale == player.getLocale() ? NamedTextColor.GREEN : NamedTextColor.GRAY)
                                        .build()
                        )
                )
                .toList();
    }
}
