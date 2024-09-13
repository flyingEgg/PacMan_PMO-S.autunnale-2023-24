package main.java.model.Strategies;

import main.java.model.Entities.GhostColor;
import main.java.model.Exceptions.IllegalEntityMovementException;
import main.java.model.Grid.Grid;
import main.java.view.GUI.GamePanelView;
import main.java.model.Model;
import main.java.model.Movement.Direction;
import main.java.API.MovementStrategy;
import main.java.model.Movement.Position;
import main.java.model.Entities.Ghost;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Rappresenta una strategia di movimento per i fantasmi nel gioco.
 * Fornisce metodi per determinare la direzione del movimento e gestire
 * le eccezioni di movimento.
 */
public abstract class GhostMovementStrategy implements MovementStrategy<Ghost> {

    protected final Ghost ghost;
    protected final Model model;
    private final Grid grid;
    private final GamePanelView gamePanel;
    private final Random rand;
    private final List<Direction> initialMoves;
    private final Map<GhostColor, Position> initialPositionsMap;
    private boolean newStrat;
    private int initialMoveIndex = 0;
    private int movStratId;

    /**
     * Costruttore della strategia di movimento del fantasma.
     * 
     * @param ghost     Il fantasma associato a questa strategia.
     * @param grid      La griglia di gioco.
     * @param model     Il modello di gioco.
     * @param gamePanel Il pannello di gioco per la visualizzazione.
     * @param newStrat  Indica se è una nuova strategia.
     */
    public GhostMovementStrategy(Ghost ghost, Grid grid, Model model, GamePanelView gamePanel, boolean newStrat) {
        this.ghost = ghost;
        this.grid = grid;
        this.model = model;
        this.gamePanel = gamePanel;
        this.rand = new Random();
        this.initialMoves = determineInitialMoves();
        this.initialPositionsMap = determinePositionsMap();
        this.newStrat = newStrat;
    }

    /**
     * Determina la prossima direzione di movimento.
     * 
     * @return La direzione successiva da prendere.
     */
    public abstract Direction determineNextDirection();

    /**
     * Gestisce il movimento del fantasma secondo la strategia corrente e aggiorna
     * il pannello di gioco.
     */
    public void movementService() {
        // Inizializza i timer per le strategie se necessario
        if (movStratId == 3) {
            model.initChaseTimer();
            model.initScatterTimer();
        }

        try {
            // Gestisci il movimento iniziale
            if (shouldPerformInitialMove()) {
                // Controlla se il fantasma è nella posizione iniziale
                Position initialPosition = initialPositionsMap.get(ghost.getColor());
                if (!ghost.getPosition().equals(initialPosition)) {
                    performInitialMove(); // Esegui il movimento iniziale
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
            reverseDirection(); // Gestisce eccezioni di movimento
        }

        if (gamePanel != null) {
            gamePanel.repaint(); // Aggiorna il pannello di gioco
        }
    }

    /**
     * Imposta un nuovo stato della strategia di movimento.
     * 
     * @param stratId L'identificatore dello stato della strategia.
     */
    public void setMovStratId(int stratId) {
        this.movStratId = stratId;
    }

    /**
     * Muove il fantasma nella direzione specificata.
     * 
     * @param direction La direzione in cui muoversi.
     */
    @Override
    public void move(Direction direction) {
        Position newPosition = calculateNewPosition(direction);

        if (isValidPosition(newPosition)) {
            model.handleMagicCoords(newPosition).ifPresentOrElse(
                    ghost::setPosition, // Imposta la posizione se non è un teletrasporto
                    () -> ghost.setPosition(newPosition)); // Gestisce il teletrasporto
        } else {
            throw new IllegalEntityMovementException("Invalid movement for Ghost " + this.ghost.toString());
        }
    }

    /**
     * Cambia la direzione del fantasma casualmente con una certa probabilità.
     */
    protected void changeDirection() {
        Direction currentDirection = ghost.getDirection();

        // Cambia direzione casualmente con probabilità
        if (rand.nextInt(3) == 0) {
            Direction newDirection = getRandomDirection(currentDirection);
            if (newDirection != null) {
                ghost.setDirection(newDirection);
            }
        }
    }

    /**
     * Ottiene una direzione casuale diversa dalla direzione corrente.
     * 
     * @param currentDirection La direzione corrente.
     * @return La nuova direzione casuale.
     */
    private Direction getRandomDirection(Direction currentDirection) {
        if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
            return rand.nextInt(2) == 0 && canMove(Direction.LEFT) ? Direction.LEFT
                    : canMove(Direction.RIGHT) ? Direction.RIGHT : null;
        } else if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
            return rand.nextInt(2) == 0 && canMove(Direction.UP) ? Direction.UP
                    : canMove(Direction.DOWN) ? Direction.DOWN : null;
        }
        return null;
    }

    /**
     * Verifica se il fantasma può muoversi in una determinata direzione.
     * 
     * @param direction La direzione da verificare.
     * @return True se il movimento è valido, altrimenti false.
     */
    protected boolean canMove(Direction direction) {
        Position position = calculateNewPosition(direction);
        return isValidPosition(position);
    }

    /**
     * Trova una direzione alternativa in cui il fantasma può muoversi.
     * 
     * @return Una direzione alternativa valida.
     */
    protected Direction findAlternativeDirection() {
        return Arrays.stream(Direction.values())
                .filter(this::canMove)
                .findFirst()
                .orElse(null); // Non dovrebbe accadere se la logica è corretta
    }

    /**
     * Verifica se il fantasma è allineato con la griglia.
     * 
     * @return True se il fantasma è allineato con la griglia, altrimenti false.
     */
    private boolean isSnappedToGrid() {
        int x = ghost.getX();
        int y = ghost.getY();
        return (x % Grid.CELL_SIZE == 0) && (y % Grid.CELL_SIZE == 0);
    }

    /**
     * Inverte la direzione del fantasma se il movimento non è valido.
     */
    private void reverseDirection() {
        Direction currentDirection = ghost.getDirection();

        if (currentDirection != null) {
            ghost.setDirection(getReverseDirection(currentDirection));
        } else {
            Direction newDirection = determineNextDirection(); // Determina una nuova direzione valida
            if (newDirection != null) {
                ghost.setDirection(newDirection);
            }
        }
    }

    /**
     * Ottiene la direzione opposta rispetto alla direzione corrente.
     * 
     * @param direction La direzione corrente.
     * @return La direzione opposta.
     */
    private Direction getReverseDirection(Direction direction) {
        return direction.getOpposite();
    }

    /**
     * Calcola la nuova posizione in base alla direzione fornita.
     * 
     * @param direction La direzione in cui muoversi.
     * @return La nuova posizione calcolata.
     */
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

    /**
     * Verifica se una posizione è valida.
     * 
     * @param position La posizione da verificare.
     * @return True se la posizione è valida, altrimenti false.
     */
    protected boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        boolean withinBounds = (x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows());
        boolean isNotWall = !grid.getWallPositions().contains(position);
        boolean isNotOccupiedByGhost = !isPositionOccupiedByGhost(position);

        return withinBounds && isNotWall && isNotOccupiedByGhost;
    }

    /**
     * Verifica se una posizione è occupata da un altro fantasma.
     * 
     * @param position La posizione da verificare.
     * @return True se la posizione è occupata da un altro fantasma, altrimenti
     *         false.
     */
    private boolean isPositionOccupiedByGhost(Position position) {
        return model.getGhosts().stream()
                .anyMatch(g -> !g.equals(this.ghost) && g.getPosition().equals(position));
    }

    /**
     * Determina i movimenti iniziali per il fantasma in base al colore.
     * 
     * @return Una lista dei movimenti iniziali.
     */
    private List<Direction> determineInitialMoves() {
        return switch (ghost.getColor()) {
            case BLUE -> List.of(Direction.LEFT, Direction.UP, Direction.UP);
            case RED -> List.of(Direction.LEFT, Direction.LEFT, Direction.DOWN, Direction.LEFT);
            case PINK -> List.of(Direction.UP, Direction.LEFT, Direction.LEFT);
            case ORANGE -> List.of(Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.DOWN);
        };
    }

    /**
     * Determina la mappa delle posizioni iniziali per i fantasmi in base al colore.
     * 
     * @return Una mappa delle posizioni iniziali.
     */
    private Map<GhostColor, Position> determinePositionsMap() {
        return Map.of(
                GhostColor.BLUE, new Position(0, 0),
                GhostColor.RED, new Position(1, 0),
                GhostColor.PINK, new Position(0, 1),
                GhostColor.ORANGE, new Position(1, 1));
    }

    /**
     * Verifica se il fantasma deve eseguire un movimento iniziale.
     * 
     * @return True se deve eseguire un movimento iniziale, altrimenti false.
     */
    private boolean shouldPerformInitialMove() {
        return newStrat && initialMoveIndex < initialMoves.size();
    }

    /**
     * Esegue il movimento iniziale del fantasma.
     */
    private void performInitialMove() {
        Direction direction = initialMoves.get(initialMoveIndex);
        if (canMove(direction)) {
            ghost.setDirection(direction);
            move(direction);
            initialMoveIndex = (initialMoveIndex + 1) % initialMoves.size();
        } else {
            Direction alternativeDirection = findAlternativeDirection();
            if (alternativeDirection != null) {
                ghost.setDirection(alternativeDirection);
                move(alternativeDirection);
            }
        }
    }
}