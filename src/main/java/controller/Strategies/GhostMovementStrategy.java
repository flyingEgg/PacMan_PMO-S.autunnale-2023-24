package main.java.controller.Strategies;

import main.java.model.Exceptions.IllegalEntityMovementException;
import main.java.model.Grid;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.MovementStrategy;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;
import main.java.view.GamePanel;

import javax.swing.Timer;
import java.util.Random;

public abstract class GhostMovementStrategy implements MovementStrategy<Ghost> {
    protected final Ghost ghost;
    protected final Grid grid;
    protected final Model model;
    protected final GamePanel gamePanel;
    protected Timer movementTimer;
    private int speed;
    private final Random rand;

    public GhostMovementStrategy(Ghost ghost, Grid grid, Model model, GamePanel gamePanel) {
        this.ghost = ghost;
        this.grid = grid;
        this.model = model;
        this.gamePanel = gamePanel;
        this.speed = 1;
        this.rand = new Random();
        initializeMovementTimer();
    }

    private void initializeMovementTimer() {
        movementTimer = new Timer(800, e -> moveAutomatically());
        movementTimer.start();
    }

    private void moveAutomatically() {

        Position nextPosition = calculateNewPosition(ghost.getDirection());

        if (!isValidPosition(nextPosition)) {
            reverseDirection();
            nextPosition = calculateNewPosition(ghost.getDirection());
        }

        if (isValidPosition(nextPosition)) {
            ghost.setPosition(nextPosition);
        } else {
            System.out.println("ATTENZIONE: Un fantasma sta tentando di accedere ad una posizione illegale: " + nextPosition);
        }

        if (isSnappedToGrid()) {
            changeDirection();
        }

        if (gamePanel != null) {
            gamePanel.repaint();
        }
    }

    protected void changeDirection() {
        Direction currentDirection = ghost.getDirection();

        if (rand.nextInt(3) == 0) {
            if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
                if (rand.nextInt(2) == 0 && canMove(Direction.LEFT)) {
                    ghost.setDirection(Direction.LEFT);
                } else if (canMove(Direction.RIGHT)) {
                    ghost.setDirection(Direction.RIGHT);
                }
            } else if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
                if (rand.nextInt(2) == 0 && canMove(Direction.UP)) {
                    ghost.setDirection(Direction.UP);
                } else if (canMove(Direction.DOWN)) {
                    ghost.setDirection(Direction.DOWN);
                }
            }
        }
    }

    // Called when the level changes to increase speed
    public void setSpeed(int s) {
        this.speed = s;
    }

    protected boolean canMove(Direction direction) {
        Position position = calculateNewPosition(direction);
        return isValidPosition(position);
    }

    protected boolean isSnappedToGrid() {
        int x = ghost.getX();
        int y = ghost.getY();

        boolean snapped = (x % Grid.CELL_SIZE == 0) && (y % Grid.CELL_SIZE == 0);
        System.out.println("Ghost at " + ghost.getPosition() + " snapped to grid: " + snapped);
        return snapped;
    }

    protected void reverseDirection() {
        Direction currDirection = ghost.getDirection();
        Direction revDirection = switch (currDirection) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };

        System.out.println("Reverse: " + currDirection + " -> " + revDirection);

        ghost.setDirection(revDirection);
    }

    public abstract Direction determineNextDirection();

    @Override
    public void move(Direction direction) {
        Position newPosition = calculateNewPosition(direction);
        model.handleMagicCoords(newPosition).ifPresentOrElse(
                teleportPosition -> ghost.setPosition(teleportPosition),
                () -> ghost.setPosition(newPosition));
    }

    protected boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        boolean withinBounds = (x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows());
        boolean isNotWall = !grid.getWallPositions().contains(position);

        return withinBounds && isNotWall;
    }

    protected Position calculateNewPosition(Direction direction) {
        int newX = ghost.getX();
        int newY = ghost.getY();

        /*if (direction == null) {
            System.out.println("Direction null. Setting default to UP");
            direction = Direction.UP;
        }*/

        if (direction != null) {
            switch (direction) {
                case UP -> newY -= 1;
                case DOWN -> newY += 1;
                case LEFT -> newX -= 1;
                case RIGHT -> newX += 1;
            }
        }

        return new Position(newX, newY);
    }
}