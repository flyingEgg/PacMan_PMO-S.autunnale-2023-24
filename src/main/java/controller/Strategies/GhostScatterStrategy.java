package main.java.controller.Strategies;

import main.java.model.Grid;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.GhostColor;
import main.java.view.GamePanel;

public class GhostScatterStrategy extends GhostMovementStrategy {

    private final Position scatterTarget;

    public GhostScatterStrategy(Ghost ghost, Grid grid, Model model, GamePanel gamePanel) {
        super(ghost, grid, model, gamePanel);
        this.scatterTarget = determineScatterTarget(ghost.getColor(), grid);
    }

    private Position determineScatterTarget(GhostColor color, Grid grid) {
        return switch (color) {
            case RED -> new Position(0, 0);
            case ORANGE -> new Position(grid.getColumns() - 1, 0);
            case PINK -> new Position(0, grid.getRows() - 1);
            case BLUE -> new Position(grid.getColumns() - 1, grid.getRows() - 1);
        };
    }

    @Override
    public Direction determineNextDirection() {
        if (ghost.getX() > scatterTarget.getX())
            return Direction.LEFT;
        if (ghost.getX() < scatterTarget.getX())
            return Direction.RIGHT;
        if (ghost.getY() > scatterTarget.getY())
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
