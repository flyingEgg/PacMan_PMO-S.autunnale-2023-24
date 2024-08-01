package Game.Strategies;

import API.MovementStrategy;
import Entities.Pacman;
import Exceptions.IllegalEntityMovementException;
import Game.Game;
import Game.Position;
import Game.Grid;

public class PacmanMovementStrategy implements MovementStrategy<Pacman> {
    private final Pacman pacman;
    private final Grid grid;
    private final Game game;

    public PacmanMovementStrategy(Pacman p, Grid g, Game gam) {
        this.pacman = p;
        this.grid = g;
        this.game = gam;
    }

    @Override
    public void move(Direction direction){
        int newX = pacman.getX(),
                newY = pacman.getY();
        switch (direction) {
            case UP -> newY -= 1;
            case DOWN -> newY += 1;
            case LEFT -> newX -= 1;
            case RIGHT -> newX += 1;
        }

        Position newPosition = new Position(newX, newY);

        if (isValidPosition(newPosition)) {
            grid.removeComponent(pacman); // rimuove pacman dalla posizione attuale
            pacman.setPosition(newPosition); // registra una nuova posizione per pacman
            grid.addComponent(pacman);
            pacman.draw(); // disegna pacman nella nuova posizione
        } else {
            throw new IllegalEntityMovementException("Invalid movement for Pacman");
        }
    }

    // Verifica che la posizione all'interno della griglia sia valida
    private boolean isValidPosition(Position position) {
        int x = position.getX(), y = position.getY();
        return x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows(); // serve controllo per i muri
    }

    private boolean isPacmanHitByGhost(Position pacPos){
        return this.game.getGhosts().
                stream().
                anyMatch(ghost -> pacPos.equals(ghost.getPosition()));
    }

    private boolean isPacmanBumpingWall(Position pacPos){
        return this.game.getGrid().getWallPositions().contains(pacPos);
    }

}
