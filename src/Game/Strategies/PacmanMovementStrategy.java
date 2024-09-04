package Game.Strategies;

import java.util.Map;
import java.util.Optional;

import API.MovementStrategy;
import Entities.Pacman;
import Exceptions.IllegalEntityMovementException;
import Game.Game;
import Game.Position;
import Game.AbsGrid;

public class PacmanMovementStrategy implements MovementStrategy<Pacman> {
    private final Pacman pacman;
    private final AbsGrid absGrid;
    private final Game game;
    private Direction currentDirection;

    public PacmanMovementStrategy(Pacman p, AbsGrid g, Game gam) {
        this.pacman = p;
        this.absGrid = g;
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
            redrawPacman(newPosition);

            if (handleMagicCoords(newPosition).isPresent()) {
                newPosition = handleMagicCoords(newPosition).get();
                redrawPacman(newPosition);
            }

            // Gestione collisione con i fantasmi
            if (isPacmanHitByGhost(newPosition)) {
                handlePacmanGhostCollision(newPosition);
            }

        } else {
            throw new IllegalEntityMovementException("Invalid movement for Pacman");
        }
    }

    private void redrawPacman(Position pacPos) {
        this.absGrid.removeComponent(pacman); // Rimuove Pacman dalla posizione attuale
        this.pacman.setPosition(pacPos); // Registra una nuova posizione per Pacman
        this.absGrid.addComponent(pacman);
    }

    // Verifica che la posizione all'interno della griglia sia valida
    private boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        // Verifica se la posizione è all'interno dei limiti della griglia
        if (x < 0 || x >= absGrid.getColumns() || y < 0 || y >= absGrid.getRows()) {
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

    private boolean isOnMagicCoord(Position pacPos) {
        return this.game.getGrid().getMagicCoords().contains(pacPos);
    }

    private Optional<Position> handleMagicCoords(Position pacPos) {
        Map<Position, Position> magicCoordsMap = Map.of(
                new Position(9, 0), new Position(9, 17),
                new Position(9, 18), new Position(9, 1));

        return Optional.ofNullable(magicCoordsMap.get(pacPos));
    }

}
