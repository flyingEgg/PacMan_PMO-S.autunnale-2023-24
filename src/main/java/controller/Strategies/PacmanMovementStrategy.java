package main.java.controller.Strategies;

import main.java.model.Grid;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.MovementStrategy;
import main.java.model.API.Position;
import main.java.model.Entities.Pacman;
import main.java.model.Exceptions.IllegalEntityMovementException;

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
        int newX = pacman.getX();
        int newY = pacman.getY();

        switch (direction) {
            case UP -> newY -= 1;
            case DOWN -> newY += 1;
            case LEFT -> newX -= 1;
            case RIGHT -> newX += 1;
        }

        Position newPosition = new Position(newX, newY);

        if (isValidPosition(newPosition)) {
            handleTeleportation(newPosition); // Check for and handle teleportation
            redrawPacman(newPosition);
        } else {
            throw new IllegalEntityMovementException("Invalid movement for Pacman");
        }
    }

    private void handleTeleportation(Position newPosition) {
        // Use Model to handle magic coordinates (teleportation)
        model.handleMagicCoords(newPosition).ifPresent(teleportPosition -> {
            redrawPacman(teleportPosition);
        });
    }

    private void redrawPacman(Position pacPos) {
        this.grid.removeComponent(pacman); // Remove Pacman from current position
        this.pacman.setPosition(pacPos); // Set new position for Pacman
        this.grid.addComponent(pacman);
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
