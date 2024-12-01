package fr.pandonia.hub.utils;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.trait.InventoryEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class Gui extends Inventory {

    private final Map<Integer, Consumer<Player>> handlers = new HashMap<>();

    public Gui(InventoryType inventoryType, String title, Background background) {
        super(inventoryType, title);

        for (int slot : background.slots()) {
            setItemStack(slot, ItemStack.builder(background.material())
                    .customName(Component.empty())
                    .build()
            );
        }

        EventNode<InventoryEvent> eventNode = EventNode.type("click", EventFilter.INVENTORY, (event, inventory) -> inventory == this)
                .addListener(InventoryPreClickEvent.class, event -> {
                    Player player = event.getPlayer();
                    int slot = event.getSlot();

                    Optional.ofNullable(handlers.get(slot)).ifPresent(handler -> {
                        player.closeInventory();
                        handler.accept(player);
                    });

                    event.setCancelled(true);
                });

        MinecraftServer.getGlobalEventHandler().addChild(eventNode);
    }

    public void setItemStack(int slot, ItemStack itemStack, Consumer<Player> handler) {
        setItemStack(slot, itemStack);
        handlers.put(slot, handler);
    }

    public record Background(int[] slots, Material material) {

    }
}
