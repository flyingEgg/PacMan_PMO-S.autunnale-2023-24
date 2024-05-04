package DesignPatterns.Composite;

import API.MapComponent;

public class Fruit implements MapComponent {
    private Position position;
    private String type;
    private boolean eaten;

    @Override
    public void render() {
        // Logica per disegnare il frutto sulla mappa di gioco
    }

    public void collect() {
        // Logica per raccogliere il frutto
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isEaten() {
        return eaten;
    }
}
