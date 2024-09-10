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

        Direction nextDirection;
        if (Math.abs(diffX) > Math.abs(diffY)) {
            nextDirection = diffX > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            nextDirection = diffY > 0 ? Direction.DOWN : Direction.UP;
        }

        if(!canMove(nextDirection)){
            if(nextDirection.equals(Direction.LEFT) || nextDirection.equals(Direction.RIGHT)){
                nextDirection = diffY > 0 ? Direction.DOWN : Direction.UP;
            } else {
                nextDirection = diffX > 0 ? Direction.RIGHT : Direction.LEFT;
            }
        }

        return nextDirection;
    }
}
