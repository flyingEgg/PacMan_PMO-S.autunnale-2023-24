package Game.Strategies;

public class GhostChaseStrategy extends GhostMovementStrategy {
    @Override
    public Direction calculateNextMove() {
        // Logica per calcolare il prossimo movimento quando il fantasma è in fase di
        // inseguimento
        return Direction.UP; // Esempio di direzione
    }
}
