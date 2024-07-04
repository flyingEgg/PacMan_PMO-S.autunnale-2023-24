package Game.Composite;

import Game.Game;
import Game.Position;

public class SmallDot extends Dot {
    private boolean eaten;

    public SmallDot(Position position) {
        super(position, 10); // 10 punti per uno small dot
        this.eaten = false;
    }

    @Override
    public void draw() {
        // Logica specifica per disegnare un puntino piccolo sulla mappa di gioco
        System.out.println("SMALL_DOT: " + getPosition());
    }

    @Override
    public void collect(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }
        // Logica generica per raccogliere un punto
        System.out.println("Collecting small dot at position: " + getPosition());
        game.incrementScore(points); // Incrementa il punteggio solo per uno small dot
        System.out.println(
                "Score updated to: " + game.getScore() + " after collecting small dot at position: " + getPosition());
        this.eaten = true;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isEaten() {
        return eaten;
    }

    @Override
    public String toString() {
        return "SmallDot at " + position + " [eaten=" + eaten + "]";
    }
}