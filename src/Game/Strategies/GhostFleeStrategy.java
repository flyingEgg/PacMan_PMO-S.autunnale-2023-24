package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Grid;
import Game.Position;

public class GhostFleeStrategy extends GhostMovementStrategy {

    public GhostFleeStrategy(Ghost ghost, Grid grid) {
        super(ghost, grid);
    }

    @Override
    protected Direction determineNextDirection() {
        // Logica semplice per fuggire da Pacman
        Position pacmanPosition = grid.getPacmanStartPosition(); // Supponendo di avere accesso alla posizione di Pacman
        if (ghost.getX() > pacmanPosition.getX())
            return Direction.RIGHT;
        if (ghost.getX() < pacmanPosition.getX())
            return Direction.LEFT;
        if (ghost.getY() > pacmanPosition.getY())
            return Direction.DOWN;
        return Direction.UP;
    }
}
