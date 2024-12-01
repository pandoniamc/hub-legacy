package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.hotbar.HotbarItem;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.Material;

public class PlayerUseItemListener implements EventListener<PlayerUseItemEvent> {

    @Override
    public Class<PlayerUseItemEvent> eventType() {
        return PlayerUseItemEvent.class;
    }

    @Override
    public Result run(PlayerUseItemEvent event) {
        Player player = event.getPlayer();
        Material material = event.getItemStack().material();

        HotbarItem.fromMaterial(material)
                .ifPresent(item -> item.use(player));

        return Result.SUCCESS;
    }
}
