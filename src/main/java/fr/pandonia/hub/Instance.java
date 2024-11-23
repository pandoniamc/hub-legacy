package fr.pandonia.hub;

import fr.pandonia.hub.utils.FullbrightDimensionType;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

import java.util.UUID;

public class Instance extends InstanceContainer {

    private static final int GROUND_Y = 64;

    public Instance() {
        super(UUID.randomUUID(), FullbrightDimensionType.INSTANCE);

        setGenerator(unit -> unit.modifier().fillHeight(0, GROUND_Y, Block.DIRT));
    }

    public Pos getSpawnPosition() {
        return new Pos(0, GROUND_Y + 1, 0);
    }
}
