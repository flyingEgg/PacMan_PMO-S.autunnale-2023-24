package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Game;
import Game.Grid;
import Game.Position;
import Game.GUI.GamePanel;

public class GhostFleeStrategy extends GhostMovementStrategy {

    public GhostFleeStrategy(Ghost ghost, Grid grid, Game game, GamePanel gamePanel) {
        super(ghost, grid, game, gamePanel);
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
