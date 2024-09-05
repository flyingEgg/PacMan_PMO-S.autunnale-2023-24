package main.java.controller.Strategies;

import main.java.model.Game;
import main.java.model.Grid;
import main.java.model.API.Direction;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.view.GamePanel;

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
