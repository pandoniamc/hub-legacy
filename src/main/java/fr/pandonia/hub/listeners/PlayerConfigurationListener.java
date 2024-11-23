package fr.pandonia.hub.listeners;

import fr.pandonia.hub.Instance;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;

public class PlayerConfigurationListener implements EventListener<AsyncPlayerConfigurationEvent> {

    private final Instance instance;

    public PlayerConfigurationListener(Instance instance) {
        this.instance = instance;
    }

    @Override
    public Class<AsyncPlayerConfigurationEvent> eventType() {
        return AsyncPlayerConfigurationEvent.class;
    }

    @Override
    public Result run(AsyncPlayerConfigurationEvent event) {
        Player player = event.getPlayer();
        player.setRespawnPoint(instance.getSpawnPosition());

        event.setSpawningInstance(instance);

        return Result.SUCCESS;
    }
}
