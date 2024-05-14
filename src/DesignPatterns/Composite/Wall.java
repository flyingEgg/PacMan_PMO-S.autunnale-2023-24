package DesignPatterns.Composite;

import API.MapComponent;

public class Wall implements MapComponent {
    private Position position;

    public Wall(Position position) {
        this.position = position;
    }

    @Override
    public void render() {
        // Logica di rendering per il muro (es. stampa a console o disegno su GUI)
        System.out.print("#");
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}