package Game.Strategies;

public class GhostScatterStrategy extends GhostMovementStrategy {
    @Override
    public Direction calculateNextMove() {
        // Logica per calcolare il prossimo movimento quando il fantasma è in fase di
        // dispersione
        return Direction.DOWN; // Esempio di direzione
    }
}
