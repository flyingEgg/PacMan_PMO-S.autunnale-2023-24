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
        System.out.println("Rendering fruit (" + type + ") at position: " + position);
        // Logica per disegnare il frutto sulla mappa di gioco
    }

    public void collect() {
        // Logica per raccogliere il frutto
        System.out.println("Collecting fruit (" + type + ") at position: " + position);
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