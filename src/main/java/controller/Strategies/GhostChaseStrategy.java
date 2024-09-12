package main.java.controller.Strategies;

import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.model.Grid.Grid;
import main.java.view.GUI.GamePanel;

/**
 * Strategia di movimento per i fantasmi durante la fase di inseguimento.
 * Il fantasma cerca di raggiungere la posizione di Pacman.
 */
public class GhostChaseStrategy extends GhostMovementStrategy {

    /**
     * Costruttore per inizializzare la strategia di inseguimento del fantasma.
     * 
     * @param ghost     Il fantasma a cui applicare la strategia.
     * @param grid      La griglia di gioco.
     * @param model     Il modello di gioco.
     * @param gamePanel Il pannello di gioco.
     * @param newStrat  Indica se utilizzare una nuova strategia.
     */
    public GhostChaseStrategy(Ghost ghost, Grid grid, Model model, GamePanel gamePanel, boolean newStrat) {
        super(ghost, grid, model, gamePanel, newStrat);
    }

    /**
     * Determina la prossima direzione del fantasma durante la fase di inseguimento.
     * 
     * @return La direzione verso cui il fantasma si muoverà.
     */
    @Override
    public Direction determineNextDirection() {
        Position currentPosition = ghost.getPosition();

        // Disabilita lo stato di spawn se il fantasma raggiunge la posizione (7, 9)
        if (currentPosition.equals(new Position(7, 9))) {
            ghost.setInSpawn(false);
        }

        // Blocca il movimento se il fantasma è in posizione (8, 9) e non è in stato di
        // spawn
        if (!ghost.isInSpawn() && currentPosition.equals(new Position(8, 9))) {
            return null; // Blocco del movimento per evitare il posizionamento in (8, 9)
        }

        // Logica per inseguire Pacman
        Position pacmanPosition = model.getPacman().getPosition();
        int diffX = pacmanPosition.getX() - ghost.getX();
        int diffY = pacmanPosition.getY() - ghost.getY();

        // Determina la direzione verso Pacman
        Direction nextDirection;
        if (Math.abs(diffX) > Math.abs(diffY)) {
            nextDirection = diffX > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            nextDirection = diffY > 0 ? Direction.DOWN : Direction.UP;
        }

        // Verifica se il fantasma può muoversi nella direzione scelta
        if (!canMove(nextDirection)) {
            // Cambia asse se la direzione non è valida
            nextDirection = (nextDirection == Direction.LEFT || nextDirection == Direction.RIGHT)
                    ? (diffY > 0 ? Direction.DOWN : Direction.UP)
                    : (diffX > 0 ? Direction.RIGHT : Direction.LEFT);
        }

        // Restituisce la direzione scelta se valida, altrimenti trova una direzione
        // alternativa
        return canMove(nextDirection) ? nextDirection : findAlternativeDirection();
    }
}