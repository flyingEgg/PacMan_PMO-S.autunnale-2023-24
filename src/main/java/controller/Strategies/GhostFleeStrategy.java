package main.java.controller.Strategies;

import main.java.model.Model;
import main.java.model.Movement.Direction;
import main.java.model.Movement.Position;
import main.java.model.Entities.Ghost;
import main.java.model.Grid.Grid;
import main.java.view.GUI.GamePanelView;

/**
 * Strategia di movimento per i fantasmi durante la fase di fuga.
 * Il fantasma cerca di allontanarsi dalla posizione di Pacman.
 */
public class GhostFleeStrategy extends GhostMovementStrategy {

    /**
     * Costruttore per inizializzare la strategia di fuga del fantasma.
     * 
     * @param ghost     Il fantasma a cui applicare la strategia.
     * @param grid      La griglia di gioco.
     * @param model     Il modello di gioco.
     * @param gamePanel Il pannello di gioco.
     * @param newStrat  Indica se utilizzare una nuova strategia.
     */
    public GhostFleeStrategy(Ghost ghost, Grid grid, Model model, GamePanelView gamePanel, boolean newStrat) {
        super(ghost, grid, model, gamePanel, newStrat);
    }

    /**
     * Determina la prossima direzione del fantasma durante la fase di fuga.
     * 
     * @return La direzione verso cui il fantasma si muoverà.
     */
    @Override
    public Direction determineNextDirection() {
        Position currentPosition = ghost.getPosition();

        // Blocca il movimento se il fantasma è in posizione (8, 9) e non è in stato di
        // spawn
        if (!ghost.isInSpawn() && currentPosition.equals(new Position(8, 9))) {
            return null; // Blocco del movimento per evitare il posizionamento in (8, 9)
        }

        // Logica per fuggire da Pacman
        Position pacmanPosition = model.getPacman().getPosition();
        int diffX = ghost.getX() - pacmanPosition.getX();
        int diffY = ghost.getY() - pacmanPosition.getY();

        // Determina la direzione opposta a Pacman
        Direction nextDirection;
        if (Math.abs(diffX) > Math.abs(diffY)) {
            nextDirection = diffX > 0 ? Direction.LEFT : Direction.RIGHT;
        } else {
            nextDirection = diffY > 0 ? Direction.UP : Direction.DOWN;
        }

        // Verifica se il fantasma può muoversi nella direzione scelta
        if (!canMove(nextDirection)) {
            // Cambia asse se la direzione non è valida
            nextDirection = (nextDirection == Direction.LEFT || nextDirection == Direction.RIGHT)
                    ? (diffY > 0 ? Direction.UP : Direction.DOWN)
                    : (diffX > 0 ? Direction.LEFT : Direction.RIGHT);
        }

        // Restituisce la direzione scelta se valida, altrimenti trova una direzione
        // alternativa
        return canMove(nextDirection) ? nextDirection : findAlternativeDirection();
    }
}