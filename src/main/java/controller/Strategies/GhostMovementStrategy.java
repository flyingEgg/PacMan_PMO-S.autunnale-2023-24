package main.java.controller.Strategies;

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
        movementTimer = new Timer(400, e -> moveAutomatically());
        movementTimer.start();
    }

    private void moveAutomatically() {
        if (isCollidingWithWall()) {
            reverseDirection();
        }

        if (isSnappedToGrid()) {
            changeDirection();
        }

        Direction direction = ghost.getDirection();
        move(direction);

        if (gamePanel != null) {
            gamePanel.repaint();
        }
    }

    protected void changeDirection() {
        Direction currentDirection = ghost.getDirection();

        if (rand.nextInt(3) == 0) {
            if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
                if (rand.nextInt(2) == 0 && canMoveLeft()) {
                    ghost.setDirection(Direction.LEFT);
                } else if (canMoveRight()) {
                    ghost.setDirection(Direction.RIGHT);
                }
            } else if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
                if (rand.nextInt(2) == 0 && canMoveUp()) {
                    ghost.setDirection(Direction.UP);
                } else if (canMoveDown()) {
                    ghost.setDirection(Direction.DOWN);
                }
            }
        }
    }

    // Called when the level changes to increase speed
    public void setSpeed(int s) {
        this.speed = s;
    }

    protected boolean canMoveLeft() {
        Position leftPos = calculateNewPosition(Direction.LEFT);
        return isValidPosition(leftPos);
    }

    protected boolean canMoveRight() {
        Position rightPos = calculateNewPosition(Direction.RIGHT);
        return isValidPosition(rightPos);
    }

    protected boolean canMoveUp() {
        Position upPos = calculateNewPosition(Direction.UP);
        return isValidPosition(upPos);
    }

    protected boolean canMoveDown() {
        Position downPos = calculateNewPosition(Direction.DOWN);
        return isValidPosition(downPos);
    }

    protected boolean isCollidingWithWall() {
        Position nextPosition = calculateNewPosition(ghost.getDirection());
        return !isValidPosition(nextPosition); // If isValidPosition returns false, it means there's a wall
    }

    protected boolean isSnappedToGrid() {
        int x = ghost.getX();
        int y = ghost.getY();
        return (x % Grid.CELL_SIZE == 0) && (y % Grid.CELL_SIZE == 0);
    }

    protected void reverseDirection() {
        Direction currDirection = ghost.getDirection();
        Direction revDirection = switch (currDirection) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
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
        return x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows()
                && !grid.getWallPositions().contains(position);
    }

    protected Position calculateNewPosition(Direction direction) {
        int newX = ghost.getX();
        int newY = ghost.getY();

        if (direction == null) {
            System.out.println("Direction null. Setting default to UP");
            direction = Direction.UP;
        }

        switch (direction) {
            case UP -> newY -= speed;
            case DOWN -> newY += speed;
            case LEFT -> newX -= speed;
            case RIGHT -> newX += speed;
        }

        return new Position(newX, newY);
    }
}