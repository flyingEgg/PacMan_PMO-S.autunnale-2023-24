package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Grid;
import Game.PacmanGrid;

public class GhostFleeStrategy extends GhostMovementStrategy {
    public GhostFleeStrategy(Ghost ghost, PacmanGrid grid) {
        super(ghost, grid);
    }

    @Override
    public void move(Direction direction) {
        // Logica di fuga
    }
}
