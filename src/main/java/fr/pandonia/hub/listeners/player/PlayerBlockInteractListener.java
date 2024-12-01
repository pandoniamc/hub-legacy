package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.hotbar.HotbarItem;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.item.Material;

public class PlayerBlockInteractListener implements EventListener<PlayerBlockInteractEvent> {

    @Override
    public Class<PlayerBlockInteractEvent> eventType() {
        return PlayerBlockInteractEvent.class;
    }

    @Override
    public Result run(PlayerBlockInteractEvent event) {
        Player player = event.getPlayer();
        Material material = player.getItemInHand(event.getHand()).material();

        HotbarItem.fromMaterial(material)
                .ifPresent(hotbarItem -> hotbarItem.use(player));

        return Result.SUCCESS;
    }
}
