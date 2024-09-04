package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Grid;
import Game.Position;

public class GhostChaseStrategy extends GhostMovementStrategy {

    public GhostChaseStrategy(Ghost ghost, Grid grid) {
        super(ghost, grid);
    }

    @Override
    protected Direction determineNextDirection() {
        // Logica semplice per seguire Pacman
        Position pacmanPosition = grid.getPacmanStartPosition(); // Supponendo di avere accesso alla posizione di Pacman
        if (ghost.getX() > pacmanPosition.getX())
            return Direction.LEFT;
        if (ghost.getX() < pacmanPosition.getX())
            return Direction.RIGHT;
        if (ghost.getY() > pacmanPosition.getY())
            return Direction.UP;
        return Direction.DOWN;
    }
}
