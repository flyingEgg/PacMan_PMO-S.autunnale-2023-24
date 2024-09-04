package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Game;
import Game.Grid;
import Game.Position;

public class GhostFleeStrategy extends GhostMovementStrategy {

    public GhostFleeStrategy(Ghost ghost, Grid grid, Game game) {
        super(ghost, grid, game);
    }

    @Override
    public Direction determineNextDirection() {
        Position pacmanPosition = game.getPacman().getPosition();

        if (ghost.getX() > pacmanPosition.getX())
            return Direction.RIGHT;
        if (ghost.getX() < pacmanPosition.getX())
            return Direction.LEFT;
        if (ghost.getY() > pacmanPosition.getY())
            return Direction.DOWN;
        return Direction.UP;
    }
}
