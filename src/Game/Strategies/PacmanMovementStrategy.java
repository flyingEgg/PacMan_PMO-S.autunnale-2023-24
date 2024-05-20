package Game.Strategies;

import API.MovementStrategy;
import Entities.Pacman;
import Game.Position;
import Game.Grid;

public class PacmanMovementStrategy implements MovementStrategy<Pacman> {
    private final Pacman pacman;
    private final Grid grid;

    public PacmanMovementStrategy(Pacman p, Grid g) {
        this.pacman = p;
        this.grid = g;
    }

    @Override
    public void move(Direction direction) {
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
            grid.removeComponent(pacman.getPosition()); // rimuove pacman dalla posizione attuale
            pacman.setPosition(newPosition); // registra una nuova posizione per pacman

            pacman.draw(); // disegna pacman nella nuova posizione
        } else {
            System.out.println("Movimento non valido");
        }
    }

    // Verifica che la posizione all'interno della griglia sia valida
    private boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x >= 0 && x < grid.getColumns() && y >= 0 && y < grid.getRows(); // serve controllo per i muri
    }
}
