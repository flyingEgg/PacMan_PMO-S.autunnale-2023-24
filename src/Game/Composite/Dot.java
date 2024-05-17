package Game.Composite;

import API.MapComponent;
import Game.Game;

public abstract class Dot implements MapComponent {
    protected Position position;
    protected boolean eaten;
    protected int points;

    public Dot(Position position, int points) {
        this.position = position;
        this.eaten = false;
        this.points = points;
    }

    @Override
    public abstract void render();

    public void collect(Game game) {
        // Logica generica per raccogliere un punto
        game.incrementScore(points);
        System.out.println("Collecting dot at position: " + position);
        this.eaten = true;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isEaten() {
        return eaten;
    }
}