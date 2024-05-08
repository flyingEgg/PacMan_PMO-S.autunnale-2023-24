package DesignPatterns.Composite;

import API.MapComponent;
import Main.Match;

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

    public void collect(Match match) {
        // Logica generica per raccogliere un punto
        match.incrementScore(points);
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