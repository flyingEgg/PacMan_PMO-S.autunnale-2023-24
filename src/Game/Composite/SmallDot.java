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
    public void render() {
        // Logica specifica per disegnare un puntino piccolo sulla mappa di gioco
        System.out.println("Rendering small dot at position: " + getPosition());
    }

    @Override
    public void collect(Game game) {
        // Logica generica per raccogliere un punto
        System.out.println("Collecting small dot at position: " + getPosition());
        game.incrementScore(points); // Incrementa il punteggio solo per uno small dot
        System.out.println("Score updated: " + game.getScore()); // Stampa il punteggio aggiornato
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
}