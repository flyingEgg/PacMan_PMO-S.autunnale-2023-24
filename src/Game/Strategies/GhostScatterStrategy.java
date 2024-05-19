package Game.Strategies;

import Entities.Ghost;
import Game.Grid;

public class GhostScatterStrategy extends GhostMovementStrategy {
    public GhostScatterStrategy(Ghost ghost, Grid grid) {
        super(ghost, grid);
    }
    @Override
    public void move(Direction direction) {
        // Logica di sparpagliamento
    }
}
