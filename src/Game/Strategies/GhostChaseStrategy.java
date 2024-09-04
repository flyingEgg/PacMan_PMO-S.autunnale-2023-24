package Game.Strategies;

import Entities.Ghost.Ghost;
import Game.Game;
import Game.Grid;
import Game.Position;
import Game.GUI.GamePanel;

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
