package main.java.controller.Strategies;

import main.java.model.Grid;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.MovementStrategy;
import main.java.model.API.Position;
import main.java.model.Entities.Pacman;
import main.java.model.Exceptions.IllegalEntityMovementException;

import java.util.EnumMap;
import java.util.Map;

public class PacmanMovementStrategy implements MovementStrategy<Pacman> {
    private final Pacman pacman;
    private final Grid grid;
    private final Model model;

    public PacmanMovementStrategy(Pacman p, Grid g, Model m) {
        this.pacman = p;
        this.grid = g;
        this.model = m;
    }

    @Override
    public void move(Direction direction) {
        Map<Direction, int[]> directionMap = new EnumMap<>(Direction.class);        // Mappa dei cambiamenti di direzione
        directionMap.put(Direction.UP, new int[]{0, -1});
        directionMap.put(Direction.DOWN, new int[]{0, 1});
        directionMap.put(Direction.LEFT, new int[]{-1, 0});
        directionMap.put(Direction.RIGHT, new int[]{1, 0});

        int[] delta = directionMap.get(direction);
        int newX = pacman.getX() + delta[0];
        int newY = pacman.getY() + delta[1];

        Position newPosition = new Position(newX, newY);


        if (isValidPosition(newPosition)) {                                         // Validazione della posizione e
            model.handleMagicCoords(newPosition).                                   // re-renderizzazione di Pacman
                    ifPresentOrElse(this::redrawPacman,
                            () -> redrawPacman(newPosition));

            model.handleSmallDotEat();
            model.handleBigDotEat();

            model.winGame();
        } else {
            throw new IllegalEntityMovementException("Invalid movement for Pacman");
        }
    }

    private void handleTeleportation(Position newPosition) {
        model.handleMagicCoords(newPosition).ifPresent(teleportPosition -> redrawPacman(teleportPosition));
    }

    private void redrawPacman(Position pacPos) {
        this.grid.removeComponent(pacman);  // Rimuove Pacman dalla posizione attuale
        this.pacman.setPosition(pacPos);    // Imposta una posizione nuova
        this.grid.addComponent(pacman);     // Aggiunge Pacman alla nuova posizione
    }

    private boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        if (x < 0 || x >= grid.getColumns() || y < 0 || y >= grid.getRows()) {
            return false;
        }

        return !isPacmanBumpingWall(position);
    }

    private boolean isPacmanBumpingWall(Position pacPos) {
        return grid.getWallPositions().contains(pacPos);
    }
}
