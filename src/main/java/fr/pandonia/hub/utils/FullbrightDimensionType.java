package fr.pandonia.hub.utils;

import net.minestom.server.MinecraftServer;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.world.DimensionType;

public class FullbrightDimensionType {

    public static final DynamicRegistry.Key<DimensionType> INSTANCE = MinecraftServer.getDimensionTypeRegistry().register("lobby:fullbright",
            DimensionType.builder()
                    .ambientLight(2.0f)
                    .build()
    );
}
