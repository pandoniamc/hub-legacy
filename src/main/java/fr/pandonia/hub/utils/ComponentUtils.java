package fr.pandonia.hub.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.text.NumberFormat;
import java.util.Locale;

public class ComponentUtils {

    private static final NumberFormat COINS_FORMAT = NumberFormat.getNumberInstance(Locale.FRANCE);

    public static Component stripItalic(Component component) {
        return component.decoration(TextDecoration.ITALIC, false);
    }

    public static Component getCoins(int coins) {
        return Component.text()
                .append(Component.text(COINS_FORMAT.format(coins), NamedTextColor.YELLOW))
                .appendSpace()
                .append(Component.text("⛁", NamedTextColor.GOLD))
                .build();
    }
}
