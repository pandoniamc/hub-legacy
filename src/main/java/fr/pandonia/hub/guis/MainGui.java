package fr.pandonia.hub.guis;

import fr.pandonia.hub.api.game.Game;
import fr.pandonia.hub.events.PlayerOpenGameEvent;
import fr.pandonia.hub.utils.Gui;
import fr.pandonia.hub.hotbar.HotbarItem;
import fr.pandonia.hub.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainGui extends Gui {

    private static final Map<String, String> LINKS = Map.of(
            "Site", "https://pandonia.fr/",
            "Discord", "https://pandonia.fr/discord",
            "Twitter", "https://twitter.com/PandoniaMC",
            "Boutique", "https://store.pandonia.fr/"
    );

    public MainGui(Player player, Map<Game, Integer> playerCount) {
        super(InventoryType.CHEST_6_ROW, "Menu principal",
                new Background(
                        new int[]{
                                0, 1, 7, 8,
                                9, 17,
                                36, 44,
                                45, 46, 52, 53
                        },
                        Material.GREEN_STAINED_GLASS_PANE
                )
        );

        setItemStack(4, ItemStack.builder(Material.OAK_SIGN)
                .customName(ComponentUtils.stripItalic(Component.text("Informations", NamedTextColor.GRAY, TextDecoration.BOLD)))
                .lore(getHelp())
                .build()
        );

        Map.of(
                21, Game.ARENA,
                22, Game.CAPTURE_THE_SHEEP,
                23, Game.BEDWARS,
                29, Game.TRAITOR,
                30, Game.HEROES,
                31, Game.BOMBERMAN,
                32, Game.SURVIVOR,
                33, Game.TNT_TAG
        ).forEach((slot, game) -> setItemStack(slot, game.asItemStack(playerCount.getOrDefault(game, 0)), p ->
                MinecraftServer.getGlobalEventHandler().call(new PlayerOpenGameEvent(p, game))
        ));

        Map.of(
                26, HotbarItem.PROFILE,
                35, HotbarItem.SHOP
        ).forEach((slot, item) -> setItemStack(slot, item.asItemStack(player, HotbarItem.DisplayType.GUI), item::use));
    }

    private List<Component> getHelp() {
        List<Component> lore = new ArrayList<>();

        lore.add(Component.text("Liens", NamedTextColor.DARK_GRAY));
        lore.addAll(
                LINKS.entrySet().stream()
                        .map(entry -> {
                            String name = entry.getKey();
                            String url = entry.getValue();

                            return Component.text()
                                    .appendSpace()
                                    .append(Component.text(name, NamedTextColor.WHITE))
                                    .appendSpace()
                                    .append(Component.text(":", NamedTextColor.GRAY))
                                    .appendSpace()
                                    .append(Component.text(url, NamedTextColor.AQUA))
                                    .build();
                        })
                        .toList()
        );
        lore.add(Component.empty());
        lore.add(Component.text("Besoin d'aide ?", NamedTextColor.BLUE));
        lore.add(
                Component.text()
                        .append(Component.text("»", NamedTextColor.DARK_GRAY))
                        .appendSpace()
                        .append(Component.text("/", NamedTextColor.GOLD))
                        .append(Component.text("help", NamedTextColor.WHITE))
                        .build()
        );

        return lore.stream()
                .map(ComponentUtils::stripItalic)
                .toList();
    }
}
