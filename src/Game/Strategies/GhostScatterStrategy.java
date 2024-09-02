package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Grid;
import Game.PacmanGrid;

public class GhostScatterStrategy extends GhostMovementStrategy {
    public GhostScatterStrategy(Ghost ghost, PacmanGrid grid) {
        super(ghost, grid);
    }
    @Override
    public void move(Direction direction) {
        // Logica di sparpagliamento
    }
}
