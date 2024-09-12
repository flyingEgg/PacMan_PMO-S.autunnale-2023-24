package main.java.controller.Strategies;

import main.java.model.Entities.GhostColor;
import main.java.model.Exceptions.IllegalEntityMovementException;
import main.java.model.Grid.Grid;
import main.java.view.GUI.GamePanel;
import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.API.MovementStrategy;
import main.java.model.API.Position;
import main.java.model.Entities.Ghost;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class GhostMovementStrategy implements MovementStrategy<Ghost> {
    private final Grid grid;
    private final GamePanel gamePanel;
    private final Random rand;
    private final List<Direction> initialMoves;
    private final Map<GhostColor, Position> initialPositionsMap;
    private boolean newStrat;
    private int initialMoveIndex = 0;
    protected final Ghost ghost;
    protected final Model model;
    private int movStratId;

    public GhostMovementStrategy(Ghost ghost, Grid grid, Model model, GamePanel gamePanel, boolean ns) {
        this.ghost = ghost;
        this.grid = grid;
        this.model = model;
        this.gamePanel = gamePanel;
        this.rand = new Random();
        this.initialMoves = determineInitialMoves();
        this.initialPositionsMap = determinePositionsMap();
        this.newStrat = ns;
    }

    public void movementService() {
        if(movStratId == 3){
            this.model.initChaseTimer();
            this.model.initScatterTimer();
        }

        try {
            // Gestisci il movimento iniziale
            if (!this.ghost.getPosition().equals(this.initialPositionsMap.get(this.ghost.getColor())) &&
                    this.initialMoveIndex < this.initialMoves.size() &&
                    this.newStrat) {
                Direction initialDirection = initialMoves.get(initialMoveIndex);
                if (canMove(initialDirection)) {
                    move(initialDirection);
                    this.initialMoveIndex++;
                } else {
                    reverseDirection();
                }
            } else {
                // Quando il fantasma è sulla griglia, verifica il movimento
                if (isSnappedToGrid()) {
                    changeDirection(); // Cambia direzione se è "allineato" alla griglia
                }

                Direction direction = determineNextDirection();
                if (direction != null && canMove(direction)) {
                    ghost.setDirection(direction);
                    move(direction);
                } else {
                    reverseDirection();
                }
            }
        } catch (IllegalEntityMovementException e) {
            reverseDirection();
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

    private boolean isSnappedToGrid() {
        int x = ghost.getX();
        int y = ghost.getY();

        // System.out.println("Ghost at " + ghost.getPosition() + " snapped to grid: " +
        // snapped);
        return (x % Grid.CELL_SIZE == 0) && (y % Grid.CELL_SIZE == 0);
    }

    private void reverseDirection() {
        Direction currDirection = ghost.getDirection();

        if (currDirection != null) {
            Direction revDirection = switch (currDirection) {
                case UP -> Direction.DOWN;
                case DOWN -> Direction.UP;
                case LEFT -> Direction.RIGHT;
                case RIGHT -> Direction.LEFT;
            };

            ghost.setDirection(revDirection);
        } else {
            // Se la direzione è null, scegli una direzione basata sulla posizione del
            // fantasma
            Direction newDirection = determineNextDirection(); // Determina la prossima direzione valida
            if (newDirection != null) {
                ghost.setDirection(newDirection); // Imposta la nuova direzione
            }
        }
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

    private Map<GhostColor, Position> determinePositionsMap() {
        return Map.of(GhostColor.BLUE, new Position(11, 7),
                GhostColor.RED, new Position(8, 6),
                GhostColor.PINK, new Position(7, 9),
                GhostColor.ORANGE, new Position(8, 7));
    }

    protected Direction findAlternativeDirection() {
        Direction[] directions = Direction.values();
        for (Direction dir : directions) {
            if (canMove(dir)) {
                return dir;
            }
        }
        return null; // Non dovrebbe accadere se la logica è corretta
    }

    public void setMovStratId(int ghostId){
        this.movStratId = ghostId;
    }
}
