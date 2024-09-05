package main.java.model.Strategies;

import Game.Game;
import Game.Grid;
import Game.Grid;
import Game.Posit
import Game.GUI.GameP
import main.java.view.GamePanel;
import main.java.model.API.Direction;.Position;d;e;ities.Ghost.Ghost;

public class GhostScatterStrategy extends GhostMovementStrategy {

    private final Position scatterTarget;

    public GhostScatterStrategy(Ghost ghost, Grid grid, Game game, GamePanel gamePanel) {
        super(ghost, grid, game, gamePanel);
        this.scatterTarget = getScatterTarget(ghost.getColor(), grid);
    }

    private Position getScatterTarget(Color color, Grid grid) {
        return switch (color) {
            case RED -> new Position(0, 0); // Angolo in alto a sinistra
            case ORANGE -> new Position(grid.getColumns() - 1, 0); // Angolo in alto a destra
            case PINK -> new Position(0, grid.getRows() - 1); // Angolo in basso a sinistra
            case BLUE -> new Position(grid.getColumns() - 1, grid.getRows() - 1); // Angolo in basso a destra
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
}
