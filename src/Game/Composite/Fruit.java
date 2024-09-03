package Game.Composite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import API.MapComponent;
import Game.Game;
import Game.Position;

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
    public void draw(Graphics2D g2d, Map<String, BufferedImage> images) {
        System.out.println("FRUIT: " + getPosition());
        // Logica per disegnare il frutto sulla mappa di gioco
    }

    public void collect(Game game) {
        // Logica per raccogliere il frutto
        System.out.println("Collecting fruit (" + type + ") at position: " + position);
        game.incrementScore(type.getPoints()); // Incrementa il punteggio in base ai punti della frutta
        System.out.println("Score updated: " + game.getScore()); // Stampa il punteggio aggiornato
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