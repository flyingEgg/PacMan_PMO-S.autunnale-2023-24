package main.java.controller.Strategies;

import main.java.model.Grid;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.view.GamePanel;

public class GhostFleeStrategy extends GhostMovementStrategy {

    public GhostFleeStrategy(Ghost ghost, Grid grid, Model model, GamePanel gamePanel) {
        super(ghost, grid, model, gamePanel);
    }

    @Override
    public Direction determineNextDirection() {
        Position currentPosition = ghost.getPosition();

        // Blocco del movimento in (8, 9) se non in stato di spawn
        if (!ghost.isInSpawn() && currentPosition.equals(new Position(8, 9))) {
            return null; // Blocco del movimento
        }

        // Logica per fuggire da Pacman
        Position pacmanPosition = model.getPacman().getPosition();
        int diffX = ghost.getX() - pacmanPosition.getX();
        int diffY = ghost.getY() - pacmanPosition.getY();

        Direction nextDirection;
        if (Math.abs(diffX) > Math.abs(diffY)) {
            nextDirection = diffX > 0 ? Direction.LEFT : Direction.RIGHT;
        } else {
            nextDirection = diffY > 0 ? Direction.UP : Direction.DOWN;
        }

        // Controlla se il fantasma può muoversi in quella direzione
        if (!canMove(nextDirection)) {
            // Prova a cambiare asse se la direzione scelta non è valida
            if (nextDirection == Direction.LEFT || nextDirection == Direction.RIGHT) {
                nextDirection = diffY > 0 ? Direction.UP : Direction.DOWN;
            } else {
                nextDirection = diffX > 0 ? Direction.LEFT : Direction.RIGHT;
            }
        }

        return canMove(nextDirection) ? nextDirection : findAlternativeDirection(); // Usa findAlternativeDirection se
                                                                                    // la direzione non è valida
    }
}
