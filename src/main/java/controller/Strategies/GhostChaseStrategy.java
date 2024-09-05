package main.java.controller.Strategies;

import main.java.model.Grid;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.view.GamePanel;

public class GhostChaseStrategy extends GhostMovementStrategy {

    public GhostChaseStrategy(Ghost ghost, Grid grid, Model model, GamePanel gamePanel) {
        super(ghost, grid, model, gamePanel);
    }

    @Override
    public Direction determineNextDirection() {
        Position pacmanPosition = model.getPacman().getPosition(); // Ottieni la posizione di Pacman

        if (ghost.getX() > pacmanPosition.getX())
            return Direction.LEFT;
        if (ghost.getX() < pacmanPosition.getX())
            return Direction.RIGHT;
        if (ghost.getY() > pacmanPosition.getY())
            return Direction.UP;
        return Direction.DOWN;
    }

    @Override
    public void move(Direction direction) {
        Position newPosition = calculateNewPosition(direction);
        if (isValidPosition(newPosition)) {
            ghost.setPosition(newPosition);
        }
    }
}
