package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Grid;

public class GhostFleeStrategy extends GhostMovementStrategy {
    public GhostFleeStrategy(Ghost ghost, Grid grid) {
        super(ghost, grid);
    }

    @Override
    public void move(Direction direction) {
        // Logica di fuga
    }
}
