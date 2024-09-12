package main.java.model.Movement;

/**
 * Enum che rappresenta le direzioni di movimento in gioco.
 */
public enum Direction {
    UP, // Movimento verso l'alto
    DOWN, // Movimento verso il basso
    LEFT, // Movimento verso sinistra
    RIGHT; // Movimento verso destra

    /**
     * Restituisce la direzione opposta a quella corrente.
     * 
     * @return La direzione opposta.
     */
    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new IllegalArgumentException("Unknown direction: " + this);
        }
    }
}