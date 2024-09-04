package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Game;
import Game.Grid;
import Game.Position;

public class GhostChaseStrategy extends GhostMovementStrategy {

    public GhostChaseStrategy(Ghost ghost, Grid grid, Game game) {
        super(ghost, grid, game);
    }

    @Override
    public Direction determineNextDirection() {
        Position pacmanPosition = game.getPacman().getPosition(); // Ottieni la posizione di Pacman

        if (ghost.getX() > pacmanPosition.getX())
            return Direction.LEFT;
        if (ghost.getX() < pacmanPosition.getX())
            return Direction.RIGHT;
        if (ghost.getY() > pacmanPosition.getY())
            return Direction.UP;
        return Direction.DOWN;
    }
}
