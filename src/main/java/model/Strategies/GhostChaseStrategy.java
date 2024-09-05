package main.java.model.Strategies;

import main.java.view.GamePanel;
import main.java.model.Game;
import main.java.model.Grid;
import main.java.model.API.Direction;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;

public class GhostChaseStrategy extends GhostMovementStrategy {

    public GhostChaseStrategy(Ghost ghost, Grid grid, Game game, GamePanel gamePanel) {
        super(ghost, grid, game, gamePanel);
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
