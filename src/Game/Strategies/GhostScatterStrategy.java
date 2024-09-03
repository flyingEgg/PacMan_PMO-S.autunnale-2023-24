package Game.Strategies;

import Entities.Ghost.Color;
import Entities.Ghost.Ghost;
import Game.PacmanGrid;
import Game.Position;

public class GhostScatterStrategy extends GhostMovementStrategy {

    private final Position scatterTarget;

    public GhostScatterStrategy(Ghost ghost, PacmanGrid grid) {
        super(ghost, grid);
        this.scatterTarget = getScatterTarget(ghost.getColor(), grid);
    }

    private Position getScatterTarget(Color color, PacmanGrid grid) {
        return switch (color) {
            case RED -> new Position(0, 0); // Angolo in alto a sinistra
            case ORANGE -> new Position(grid.getColumns() - 1, 0); // Angolo in alto a destra
            case PINK -> new Position(0, grid.getRows() - 1); // Angolo in basso a sinistra
            case BLUE -> new Position(grid.getColumns() - 1, grid.getRows() - 1); // Angolo in basso a destra
        };
    }

    @Override
    protected Direction determineNextDirection() {
        if (ghost.getX() > scatterTarget.getX())
            return Direction.LEFT;
        if (ghost.getX() < scatterTarget.getX())
            return Direction.RIGHT;
        if (ghost.getY() > scatterTarget.getY())
            return Direction.UP;
        return Direction.DOWN;
    }
}
