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
import java.util.List;
import java.util.Random;

public abstract class GhostMovementStrategy implements MovementStrategy<Ghost> {
    private final Grid grid;
    private final GamePanel gamePanel;
    private final List<Direction> initialMoves;
    private int initialMoveIndex = 0;
    private final Random rand;
    protected final Ghost ghost;
    protected final Model model;

    public GhostMovementStrategy(Ghost ghost, Grid grid, Model model, GamePanel gamePanel) {
        this.ghost = ghost;
        this.grid = grid;
        this.model = model;
        this.gamePanel = gamePanel;
        this.initialMoves = determineInitialMoves();
        this.rand = new Random();
        //initializeMovementTimer();
    }

    private void initializeMovementTimer() {
        Timer movementTimer = new Timer(800, e -> movementService());
        movementTimer.start();
    }


    public void movementService() {
        try {
            if (initialMoveIndex < initialMoves.size()) {
                Direction initialDirection = initialMoves.get(initialMoveIndex);
                if (canMove(initialDirection)) {
                    move(initialDirection);
                    this.initialMoveIndex++;
                    System.out.println("Indice initial move per il fantasma "+ghost.getColor()+" "+initialMoveIndex);
                } else {
                    reverseDirection();
                }
            } else {

                if (isSnappedToGrid()) {
                    changeDirection();  // Change direction when ghost snaps to grid
                }

                Direction direction = determineNextDirection();
                if(canMove(direction)){
                    ghost.setDirection(direction);
                    move(direction);
                } else {
                    reverseDirection();
                }
            }

        } catch (IllegalEntityMovementException e) {
            reverseDirection();
            //System.out.println(e.getMessage());
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

    protected boolean canMove(Direction direction) {
        Position position = calculateNewPosition(direction);
        return isValidPosition(position);
    }

    protected boolean isCollidingWithWall() {
        Position nextPosition = calculateNewPosition(ghost.getDirection());
        return !isValidPosition(nextPosition);  // Return true if the next position is invalid (i.e., collides with a wall)
    }

    private boolean isSnappedToGrid() {
        int x = ghost.getX();
        int y = ghost.getY();

        // System.out.println("Ghost at " + ghost.getPosition() + " snapped to grid: " + snapped);
        return (x % Grid.CELL_SIZE == 0) && (y % Grid.CELL_SIZE == 0);
    }

    private void reverseDirection() {
        Direction currDirection = ghost.getDirection();
        Direction revDirection = switch (currDirection) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };

        // System.out.println("Reverse: " + currDirection + " -> " + revDirection);

        ghost.setDirection(revDirection);
    }

    private List<Direction> determineInitialMoves() {
        return switch (ghost.getColor()) {
            case BLUE -> List.of(Direction.LEFT, Direction.UP, Direction.UP);
            case RED -> List.of(Direction.LEFT, Direction.LEFT, Direction.DOWN, Direction.LEFT);
            case PINK -> List.of(Direction.UP, Direction.LEFT, Direction.LEFT);
            case ORANGE -> List.of(Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.DOWN);
        };
    }

    public abstract Direction determineNextDirection();

    @Override
    public void move(Direction direction) {
        Position newPosition = calculateNewPosition(direction);

        if (isValidPosition(newPosition)) {
            model.handleMagicCoords(newPosition).ifPresentOrElse(
                    teleportPosition -> ghost.setPosition(teleportPosition),
                    () -> ghost.setPosition(newPosition));
        } else {
            throw new IllegalEntityMovementException("Invalid movement for Ghost " + this.ghost.toString());
        }

    }

    private boolean isPositionOccupiedByGhost(Position position) {
        List<Ghost> ghosts = model.getGhosts();
        return ghosts.stream()
                .anyMatch(g -> !g.equals(this.ghost) && g.getPosition().equals(position));
    }

    private Position calculateNewPosition(Direction direction) {
        int newX = ghost.getX();
        int newY = ghost.getY();

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

    protected boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        boolean withinBounds = (x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows());
        boolean isNotWall = !grid.getWallPositions().contains(position);
        boolean isNotOccupiedByGhost = !isPositionOccupiedByGhost(position);

        return withinBounds && isNotWall && isNotOccupiedByGhost;
    }
}