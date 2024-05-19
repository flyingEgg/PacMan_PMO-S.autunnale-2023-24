package Game.Strategies;

import Entities.Ghost;
import Game.Grid;

public class GhostChaseStrategy extends GhostMovementStrategy {
    public GhostChaseStrategy(Ghost ghost, Grid grid) {
        super(ghost, grid);
    }

    @Override
    public void move(Direction direction) {
        // Logica di inseguimento
    }
}
