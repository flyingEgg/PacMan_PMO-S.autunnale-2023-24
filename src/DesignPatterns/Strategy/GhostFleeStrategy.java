package DesignPatterns.Strategy;

public class GhostFleeStrategy extends GhostMovementStrategy {
    @Override
    public Direction calculateNextMove() {
        // Logica per calcolare il prossimo movimento quando il fantasma è in fuga
        return Direction.RIGHT; // Esempio di direzione
    }
}
