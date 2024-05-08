package DesignPatterns.Composite;

import API.MapComponent;

public class Fruit implements MapComponent {
    private Position position;
    private String type;
    private boolean eaten;

    public Fruit(Position position, String type) {
        this.position = position;
        this.type = type;
        this.eaten = false;
    }

    @Override
    public void render() {
        // Implementazione per disegnare il frutto sulla mappa di gioco
    }

    public void collect() {
        this.eaten = true;
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