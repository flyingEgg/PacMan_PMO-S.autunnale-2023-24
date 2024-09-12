package main.java.controller.Strategies;

import main.java.model.Model;
import main.java.model.Movement.Direction;
import main.java.model.API.MovementStrategy;
import main.java.model.Movement.Position;
import main.java.model.Entities.Pacman;
import main.java.model.Exceptions.IllegalEntityMovementException;
import main.java.model.Grid.Grid;

import java.util.Map;

/**
 * Strategia di movimento per Pacman.
 * 
 * Questa strategia gestisce il movimento di Pacman sulla griglia di gioco,
 * inclusa la gestione della collisione con muri e la raccolta di punti.
 */
public class PacmanMovementStrategy implements MovementStrategy<Pacman> {

    private final Pacman pacman;
    private final Grid grid;
    private final Model model;

    /**
     * Costruttore della strategia di movimento per Pacman.
     * 
     * @param p Il Pacman associato a questa strategia.
     * @param g La griglia di gioco.
     * @param m Il modello di gioco.
     */
    public PacmanMovementStrategy(Pacman p, Grid g, Model m) {
        this.pacman = p;
        this.grid = g;
        this.model = m;
    }

    @Override
    public void move(Direction direction) {
        // Crea una mappa dei cambiamenti di direzione
        Map<Direction, int[]> directionMap = Map.of(
                Direction.UP, new int[] { 0, -1 },
                Direction.DOWN, new int[] { 0, 1 },
                Direction.LEFT, new int[] { -1, 0 },
                Direction.RIGHT, new int[] { 1, 0 });

        // Ottieni il cambiamento di direzione corrispondente
        int[] delta = directionMap.get(direction);
        if (delta == null) {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        int newX = pacman.getX() + delta[0];
        int newY = pacman.getY() + delta[1];
        Position newPosition = new Position(newX, newY);

        // Verifica se la nuova posizione è valida
        if (isValidPosition(newPosition)) {
            // Gestisce le coordinate magiche e ridisegna Pacman
            model.handleMagicCoords(newPosition).ifPresentOrElse(
                    this::redrawPacman, // Rende Pacman nella nuova posizione
                    () -> redrawPacman(newPosition) // Rende Pacman nella nuova posizione
            );

            model.handleDotEat(); // Gestisce la raccolta dei punti
            model.winGame(); // Verifica se Pacman ha vinto
        } else {
            throw new IllegalEntityMovementException("Invalid movement for Pacman");
        }
    }

    /**
     * Ridisegna Pacman nella nuova posizione.
     * 
     * @param pacPos La nuova posizione di Pacman.
     */
    private void redrawPacman(Position pacPos) {
        this.grid.removeComponent(pacman); // Rimuove Pacman dalla posizione attuale
        this.pacman.setPosition(pacPos); // Imposta la nuova posizione
        this.grid.addComponent(pacman); // Aggiunge Pacman alla nuova posizione
    }

    /**
     * Verifica se una posizione è valida.
     * 
     * @param position La posizione da verificare.
     * @return True se la posizione è valida, altrimenti false.
     */
    private boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        // Verifica se la posizione è dentro i confini della griglia
        if (x < 0 || x >= grid.getColumns() || y < 0 || y >= grid.getRows()) {
            return false;
        }

        // Verifica se la posizione è un muro
        return !isPacmanBumpingWall(position);
    }

    /**
     * Verifica se Pacman sta sbattendo contro un muro.
     * 
     * @param pacPos La posizione da verificare.
     * @return True se Pacman sta sbattendo contro un muro, altrimenti false.
     */
    private boolean isPacmanBumpingWall(Position pacPos) {
        return grid.getWallPositions().contains(pacPos);
    }
}