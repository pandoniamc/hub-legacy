package fr.pandonia.hub.api.game;

import fr.pandonia.hub.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.ArrayList;
import java.util.List;

public enum Game {

    ARENA("Arena", NamedTextColor.BLUE, "1.0", Material.DIAMOND_SWORD, List.of(
            "Un jeu de combat en arène.",
            "Choisissez votre classe, combattez",
            "vos adversaires et tentez de",
            "devenir le meilleur gladiateur",
            "de la saison !"
    ), GameCategory.PVP, GameCategory.SOLO) {
        private static final int SEASON = 1;

        @Override
        protected Component getCustomName() {
            return ComponentUtils.stripItalic(
                    Component.text()
                            .append(Component.text(name, color, TextDecoration.BOLD))
                            .appendSpace()
                            .append(Component.text("(v%s)".formatted(version), NamedTextColor.GRAY))
                            .appendSpace()
                            .append(Component.text("»", NamedTextColor.DARK_AQUA, TextDecoration.BOLD))
                            .appendSpace()
                            .append(Component.text("Saison %d".formatted(SEASON), NamedTextColor.AQUA, TextDecoration.BOLD))
                            .appendSpace()
                            .append(Component.text("«", NamedTextColor.DARK_AQUA, TextDecoration.BOLD))
                            .build()
            );
        }
    },
    BEDWARS("Bedwars", NamedTextColor.GOLD, "1.0", Material.RED_BED, List.of(
            "Protégez votre lit et tentez de",
            "détruire celui de vos adversaires",
            "pour remporter la partie !"
    ), GameCategory.PVP),
    BOMBERMAN("Bomberman", NamedTextColor.RED, "1.0", Material.TNT_MINECART, List.of(
            "Posez des bombes et détruisez",
            "les briques pour récupérer des",
            "bonus et faites exploser ",
            "vos adversaires !"
    ), GameCategory.PVP, GameCategory.SOLO),
    CAPTURE_THE_SHEEP("Capture The Sheep", NamedTextColor.YELLOW, "1.0", Material.WHITE_WOOL, List.of(
            "Un jeu de capture de drapeau",
            "où vous devez voler le mouton",
            "de l'équipe adverse et le ramener",
            "à votre base pour remporter la",
            "partie !"
    ), GameCategory.PVP, GameCategory.TEAM),
    HEROES("Heroes", NamedTextColor.LIGHT_PURPLE, "1.0", Material.DIAMOND_CHESTPLATE, List.of(
            "Participez à plusieurs mini-jeux",
            "et tentez de remporter le plus de",
            "points pour devenir le héros !"
    ), GameCategory.PVP, GameCategory.SOLO),
    SURVIVOR("Survivor", NamedTextColor.GREEN, "1.0", Material.ZOMBIE_HEAD, List.of(
            "Survivez aux vagues de monstres",
            "et tentez de rester en vie le plus",
            "longtemps possible !"
    ), GameCategory.PVE),
    TRAITOR("Traitor", NamedTextColor.WHITE, "1.0", Material.PLAYER_HEAD, List.of(
            "Un jeu de déduction où vous devez",
            "découvrir qui sont les traîtres",
            "parmi les innocents avant qu'ils",
            "ne vous éliminent !"
    ), GameCategory.ROLE_PLAY),
    TNT_TAG("TNT Tag", NamedTextColor.RED, "1.0", Material.TNT, List.of(
            "Fuyez les joueurs portant le TNT",
            "ou tentez de la transmettre",
            "avant qu'elle n'explose !"
    ), GameCategory.PVP, GameCategory.SOLO);

    protected final String name;
    protected final TextColor color;
    protected final String version;
    private final Material material;
    private final List<String> description;
    private final List<GameCategory> categories;

    Game(String name, TextColor color, String version, Material material, List<String> description, GameCategory... categories) {
        this.name = name;
        this.color = color;
        this.version = version;
        this.material = material;
        this.description = description;
        this.categories = List.of(categories);
    }

    public ItemStack asItemStack(int playerCount) {
        return ItemStack.builder(material)
                .customName(getCustomName())
                .lore(getLore(playerCount))
                .hideExtraTooltip()
                .build();
    }

    protected Component getCustomName() {
        return ComponentUtils.stripItalic(
                Component.text()
                        .append(Component.text(name, color, TextDecoration.BOLD))
                        .appendSpace()
                        .append(Component.text("(v%s)".formatted(version), NamedTextColor.GRAY))
                        .build()
        );
    }

    private List<Component> getLore(int playerCount) {
        List<Component> lore = new ArrayList<>();

        lore.add(
                Component.text()
                        .append(Component.text("Genre", NamedTextColor.DARK_GRAY))
                        .appendSpace()
                        .append(Component.text(":", NamedTextColor.GRAY))
                        .appendSpace()
                        .append(
                                Component.join(
                                        JoinConfiguration.separator(Component.text("/", NamedTextColor.GRAY)),
                                        categories
                                                .stream()
                                                .map(category -> Component.text(category.getName(), NamedTextColor.WHITE))
                                                .toList()
                                )
                        )
                        .build()
        );
        lore.add(Component.empty());
        lore.add(Component.text("DESCRIPTION", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));
        lore.addAll(description.stream().map(line -> Component.text(line, NamedTextColor.GRAY)).toList());
        lore.add(Component.empty());
        lore.add(Component.text("INFORMATIONS", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD));
        lore.add(
                Component.text()
                        .appendSpace()
                        .append(Component.text("Joueurs", NamedTextColor.WHITE))
                        .appendSpace()
                        .append(Component.text(":", NamedTextColor.GRAY))
                        .appendSpace()
                        .append(Component.text(playerCount, NamedTextColor.YELLOW))
                        .build()
        );
        lore.add(
                Component.text()
                        .appendSpace()
                        .append(Component.text("Statut", NamedTextColor.WHITE))
                        .appendSpace()
                        .append(Component.text(":", NamedTextColor.GRAY))
                        .appendSpace()
                        .append(Component.text("Ouvert", NamedTextColor.GREEN))
                        .build()
        );
        lore.add(Component.empty());
        lore.add(
                Component.text()
                        .append(Component.text("»", NamedTextColor.DARK_AQUA, TextDecoration.BOLD))
                        .appendSpace()
                        .append(Component.text("Cliquez pour rejoindre", NamedTextColor.AQUA))
                        .build()
        );

        return lore.stream()
                .map(ComponentUtils::stripItalic)
                .toList();
    }
}
