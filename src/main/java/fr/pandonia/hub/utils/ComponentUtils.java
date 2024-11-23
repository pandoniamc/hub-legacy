package fr.pandonia.hub.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class ComponentUtils {

    public static Component stripItalic(Component component) {
        return component.decoration(TextDecoration.ITALIC, false);
    }
}
