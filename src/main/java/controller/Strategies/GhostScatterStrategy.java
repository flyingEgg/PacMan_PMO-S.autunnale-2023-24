package main.java.controller.Strategies;

import main.java.model.Model;
import main.java.model.Movement.Direction;
import main.java.model.Movement.Position;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.GhostColor;
import main.java.model.Grid.Grid;
import main.java.view.GUI.GamePanelView;

/**
 * Strategia di movimento per il fantasma durante la fase di scatter.
 * 
 * Questa strategia guida il fantasma verso una posizione specifica (scatter
 * target)
 * in base al colore del fantasma.
 */
public class GhostScatterStrategy extends GhostMovementStrategy {

    private final Position scatterTarget;

    /**
     * Costruttore della strategia di movimento scatter per il fantasma.
     * 
     * @param ghost     Il fantasma associato a questa strategia.
     * @param grid      La griglia di gioco.
     * @param model     Il modello di gioco.
     * @param gamePanel Il pannello di gioco per la visualizzazione.
     * @param newStrat  Indica se è una nuova strategia.
     */
    public GhostScatterStrategy(Ghost ghost, Grid grid, Model model, GamePanelView gamePanel, boolean newStrat) {
        super(ghost, grid, model, gamePanel, newStrat);
        this.scatterTarget = determineScatterTarget(ghost.getColor(), grid);
    }

    /**
     * Determina la posizione di destinazione dello scatter in base al colore del
     * fantasma.
     * 
     * @param color Il colore del fantasma.
     * @param grid  La griglia di gioco.
     * @return La posizione di destinazione dello scatter.
     */
    private Position determineScatterTarget(GhostColor color, Grid grid) {
        return switch (color) {
            case RED -> new Position(0, 0);
            case ORANGE -> new Position(grid.getColumns() - 1, 0);
            case PINK -> new Position(0, grid.getRows() - 1);
            case BLUE -> new Position(grid.getColumns() - 1, grid.getRows() - 1);
        };
    }

    @Override
    public Direction determineNextDirection() {
        // Calcola la direzione per muoversi verso la destinazione dello scatter
        if (ghost.getX() > scatterTarget.getX()) {
            return Direction.LEFT;
        } else if (ghost.getX() < scatterTarget.getX()) {
            return Direction.RIGHT;
        } else if (ghost.getY() > scatterTarget.getY()) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }
}