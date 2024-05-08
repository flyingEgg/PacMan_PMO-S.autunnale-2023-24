package DesignPatterns.Composite;

import API.MapComponent;

public abstract class Dot implements MapComponent {
    protected Position position;
    protected boolean eaten;

    public Dot(Position position) {
        this.position = position;
        this.eaten = false;
    }

    @Override
    public abstract void render();

    public void collect() {
        // Logica generica per raccogliere un punto
        System.out.println("Collecting dot at position: " + position);
        eaten = true;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isEaten() {
        return eaten;
    }
}