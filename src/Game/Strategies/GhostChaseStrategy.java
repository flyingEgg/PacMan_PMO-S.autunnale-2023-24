package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Grid;
import Game.Position;

public class GhostChaseStrategy extends GhostMovementStrategy {
    private final Position targetPosition;

    public GhostChaseStrategy(Ghost ghost, Grid grid, Position targetPosition) {
        super(ghost, grid);
        this.targetPosition = targetPosition;
    }

    @Override
    public void move(Direction direction) {
        Position newPosition = calculateNewPosition(direction);
        if (isValidPosition(newPosition)) {
            ghost.setPosition(newPosition);
        }
    }
}
