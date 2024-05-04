package DesignPatterns.Composite;

import API.MapComponent;

public abstract class Dot implements MapComponent {
    protected Position position;
    protected boolean eaten;

    @Override
    public abstract void render();

    public void collect() {
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
