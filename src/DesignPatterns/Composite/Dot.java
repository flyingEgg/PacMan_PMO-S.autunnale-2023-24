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
        this.eaten = true;
        // Logica generica per raccogliere un punto
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isEaten() {
        return eaten;
    }
}