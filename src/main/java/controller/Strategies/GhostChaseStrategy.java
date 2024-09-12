package main.java.controller.Strategies;

import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.model.Grid.Grid;
import main.java.view.GUI.GamePanel;

public class GhostChaseStrategy extends GhostMovementStrategy {

    public GhostChaseStrategy(Ghost ghost, Grid grid, Model model, GamePanel gamePanel, boolean newStrat) {
        super(ghost, grid, model, gamePanel, newStrat);
    }

    @Override
    public Direction determineNextDirection() {
        Position currentPosition = ghost.getPosition();

        // Se il fantasma ha raggiunto (7, 9), disabilita lo stato di spawn
        if (currentPosition.equals(new Position(7, 9))) {
            ghost.setInSpawn(false);
        }

        // Se il fantasma non è in spawn e tenta di entrare in (8, 9), blocca il
        // movimento
        if (!ghost.isInSpawn() && currentPosition.equals(new Position(8, 9))) {
            return null; // Blocco del movimento
        }

        // Logica standard per l'inseguimento
        Position pacmanPosition = model.getPacman().getPosition();
        int diffX = pacmanPosition.getX() - ghost.getX();
        int diffY = pacmanPosition.getY() - ghost.getY();

        Direction nextDirection;
        if (Math.abs(diffX) > Math.abs(diffY)) {
            nextDirection = diffX > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            nextDirection = diffY > 0 ? Direction.DOWN : Direction.UP;
        }

        // Controlla se il fantasma può muoversi in quella direzione
        if (!canMove(nextDirection)) {
            // Prova a cambiare asse se la direzione scelta non è valida
            if (nextDirection == Direction.LEFT || nextDirection == Direction.RIGHT) {
                nextDirection = diffY > 0 ? Direction.DOWN : Direction.UP;
            } else {
                nextDirection = diffX > 0 ? Direction.RIGHT : Direction.LEFT;
            }
        }

        return canMove(nextDirection) ? nextDirection : findAlternativeDirection(); // Usa findAlternativeDirection se
                                                                                    // la direzione non è valida
    }
}
