package main.java.controller.Strategies;

import java.util.Map;
import java.util.Optional;

import main.java.model.Game;
import main.java.model.Grid;
import main.java.model.API.Direction;
import main.java.model.API.MovementStrategy;
import main.java.model.API.Position;
import main.java.model.Composite.SmallDot;
import main.java.model.Entities.Pacman;
import main.java.model.Exceptions.IllegalEntityMovementException;

public class PacmanMovementStrategy implements MovementStrategy<Pacman> {
    private final Pacman pacman;
    private final Grid grid;
    private final Game game;
    private Direction currentDirection;

    public PacmanMovementStrategy(Pacman p, Grid g, Game gam) {
        this.pacman = p;
        this.grid = g;
        this.game = gam;
        this.currentDirection = null; // inizialmente fermo
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
            game.getGhosts().stream().forEach(ghost -> ghost.getMovementStrategy().move(direction));

            if (handleMagicCoords(newPosition).isPresent()) {
                newPosition = handleMagicCoords(newPosition).get();
                redrawPacman(newPosition);
            }

            if (isPacmanHitByGhost(newPosition)) {
                handlePacmanGhostCollision(newPosition);
            } else {
                redrawPacman(newPosition);
            }

            handlePacmanDotEat(newPosition);
        } else {
            throw new IllegalEntityMovementException("Invalid movement for Pacman");
        }
    }

    private void redrawPacman(Position pacPos) {
        this.grid.removeComponent(pacman); // Rimuove Pacman dalla posizione attuale
        this.pacman.setPosition(pacPos); // Registra una nuova posizione per Pacman
        this.grid.addComponent(pacman);
    }

    // Verifica che la posizione all'interno della griglia sia valida
    private boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        // Verifica se la posizione è all'interno dei limiti della griglia
        if (x < 0 || x >= grid.getColumns() || y < 0 || y >= grid.getRows()) {
            return false;
        }

        // Verifica se la posizione non è occupata da un muro
        if (isPacmanBumpingWall(position)) {
            return false;
        }

        return true;
    }

    private boolean isPacmanHitByGhost(Position pacPos) {
        return this.game.getGhosts().stream().anyMatch(ghost -> pacPos.equals(ghost.getPosition()));
    }

    private boolean isPacmanBumpingWall(Position pacPos) {
        return this.game.getGrid().getWallPositions().contains(pacPos);
    }

    private void handlePacmanDotEat(Position pacPos) {
        SmallDot dot = grid.getSmallDotAtPosition(pacPos);

        try {
            if (dot != null && !dot.isEaten()) {
                dot.collect(game);
                game.getGamePanel().repaint();
            }
        } catch (NullPointerException npE) {
            System.out.println(npE.getMessage() + " " + npE.getCause());
        }

    }

    private void handlePacmanGhostCollision(Position pacPos) {
        if (pacman.isSuperMode()) {
            // Pacman è in modalità super, il fantasma viene mangiato
            this.game.eatGhost(pacPos); // Logica per mangiare il fantasma
        } else {
            // Pacman viene colpito dal fantasma, perde una vita
            redrawPacman(this.game.getGrid().getPacmanStartPosition());
            this.game.loseLife();
        }
    }

    private Optional<Position> handleMagicCoords(Position pacPos) {
        Map<Position, Position> magicCoordsMap = Map.of(
                new Position(9, 0), new Position(9, 17),
                new Position(9, 18), new Position(9, 1));

        return Optional.ofNullable(magicCoordsMap.get(pacPos));
    }

}
