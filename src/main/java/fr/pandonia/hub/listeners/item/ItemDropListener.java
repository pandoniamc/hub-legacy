package fr.pandonia.hub.listeners.item;

import net.minestom.server.event.EventListener;
import net.minestom.server.event.item.ItemDropEvent;

public class ItemDropListener implements EventListener<ItemDropEvent> {

    @Override
    public Class<ItemDropEvent> eventType() {
        return ItemDropEvent.class;
    }

    @Override
    public Result run(ItemDropEvent event) {
        event.setCancelled(true);

        return Result.SUCCESS;
    }
}
