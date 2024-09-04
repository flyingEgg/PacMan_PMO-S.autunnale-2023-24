package Game.Strategies;

import API.MovementStrategy;
import Entities.Ghost.Ghost;
import Game.Grid;
import Game.Position;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class GhostMovementStrategy implements MovementStrategy<Ghost> {
    protected final Ghost ghost;
    protected final Grid grid;
    protected Timer movementTimer;

    public GhostMovementStrategy(Ghost ghost, Grid grid) {
        this.ghost = ghost;
        this.grid = grid;
        initializeMovementTimer();
    }

    private void initializeMovementTimer() {
        movementTimer = new Timer(400, new ActionListener() { // Il timer si attiva ogni 400 ms (puoi regolarlo)
            @Override
            public void actionPerformed(ActionEvent e) {
                moveAutomatically();
            }
        });
        movementTimer.start();
    }

    private void moveAutomatically() {
        Direction direction = determineNextDirection();
        if (direction != null) {
            move(direction);
        }
    }

    protected abstract Direction determineNextDirection();

    @Override
    public void move(Direction direction) {
        Position newPosition = calculateNewPosition(direction);
        if (isValidPosition(newPosition)) {
            ghost.setPosition(newPosition);
        }
    }

    protected boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows()
                && !grid.getWallPositions().contains(position);
    }

    protected Position calculateNewPosition(Direction direction) {
        int newX = ghost.getX();
        int newY = ghost.getY();

        switch (direction) {
            case UP -> newY -= 1;
            case DOWN -> newY += 1;
            case LEFT -> newX -= 1;
            case RIGHT -> newX += 1;
        }

        return new Position(newX, newY);
    }
}
