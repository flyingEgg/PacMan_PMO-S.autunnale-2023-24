package DesignPatterns.Composite;

import API.MapComponent;
import Main.Match;

public class Fruit implements MapComponent {
    private Position position;
    private FruitType type;
    private boolean eaten;

    public Fruit(Position position, FruitType type) {
        this.position = position;
        this.type = type;
        this.eaten = false;
    }

    @Override
    public void render() {
        System.out.println("Rendering fruit (" + type + ") at position: " + position);
        // Logica per disegnare il frutto sulla mappa di gioco
    }

    public void collect(Match match) {
        // Logica per raccogliere il frutto
        System.out.println("Collecting fruit (" + type + ") at position: " + position);
        match.increaseScore(type.getPoints()); // Incrementa il punteggio in base ai punti della frutta
        System.out.println("Score updated: " + match.getScore()); // Stampa il punteggio aggiornato
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