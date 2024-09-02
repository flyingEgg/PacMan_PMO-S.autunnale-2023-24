package Game.Strategies;

import API.MovementStrategy;
import Entities.Ghost.Ghost;
import Game.Position;
import Game.Grid;

public abstract class GhostMovementStrategy implements MovementStrategy<Ghost> {
    protected final Ghost ghost;
    protected final Grid grid;

    public GhostMovementStrategy(Ghost ghost, Grid grid) {
        this.ghost = ghost;
        this.grid = grid;
    }

    public abstract void move(Direction direction);

    protected boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows()
                && !grid.getWallPositions().contains(position);
    }

    protected Position calculateNewPosition(Direction direction) {
        int newX = ghost.getX();
        int newY = ghost.getY();

        switch (direction) {
            case UP -> newY -= 1;
            case DOWN -> newY += 1;
            case LEFT -> newX -= 1;
            case RIGHT -> newX += 1;
        }

        return new Position(newX, newY);
    }
}
