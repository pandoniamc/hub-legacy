package fr.pandonia.hub.api.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum Group {

    ADMINISTRATOR("Administrateur", NamedTextColor.DARK_RED),
    MANAGER("Responsable", NamedTextColor.RED),
    MODERATOR("Modérateur", NamedTextColor.BLUE),
    HELPER("Helper", NamedTextColor.DARK_AQUA),
    DEVELOPER("Développeur", NamedTextColor.LIGHT_PURPLE),
    BUILDER("Builder", NamedTextColor.GREEN),
    COMMUNITY_MANAGER("Community Manager", NamedTextColor.YELLOW),
    VIP_PLUS("VIP+", NamedTextColor.AQUA),
    VIP("VIP", NamedTextColor.GOLD),
    DEFAULT("Joueur", NamedTextColor.GRAY) {
        @Override
        public Component getCustomName(String username) {
            return Component.text(username, NamedTextColor.GRAY);
        }
    };

    private final String name;
    private final TextColor color;

    Group(String name, TextColor color) {
        this.color = color;
        this.name = name;
    }

    public Component getCustomName() {
        return Component.text(this.name, this.color);
    }

    public Component getCustomName(String username) {
        return Component.text()
                .append(Component.text(this.name).decorate(TextDecoration.BOLD))
                .append(Component.text(" | "))
                .append(Component.text(username))
                .color(color)
                .build();
    }
}
