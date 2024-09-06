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
        Position pacmanPosition = model.getPacman().getPosition();
        int diffX = pacmanPosition.getX() - ghost.getX();
        int diffY = pacmanPosition.getY() - ghost.getY();

        if (Math.abs(diffX) > Math.abs(diffY)) {
            return diffX > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            return diffY > 0 ? Direction.DOWN : Direction.UP;
        }
    }
}
