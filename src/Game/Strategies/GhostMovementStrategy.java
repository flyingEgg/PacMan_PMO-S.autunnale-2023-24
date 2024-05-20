package Game.Strategies;

import API.MovementStrategy;
import Entities.Ghost;
import Game.Composite.Position;
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
        return x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows(); // serve controllo per i muri
    }
}
