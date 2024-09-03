package Game.Composite;

import API.MapComponent;
import Game.Game;
import Game.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class Dot implements MapComponent {
    protected final Position position;
    protected boolean eaten;
    protected final int points;

    public Dot(Position position, int points) {
        this.position = position;
        this.eaten = false;
        this.points = points;
    }

    @Override
    public abstract void draw(Graphics2D g2d, Map<String, BufferedImage> images);

    public void collect(Game game) {
        if (isEaten()) {
            return; // Non fare nulla se il dot è già stato mangiato
        }

        game.incrementScore(points);
        System.out.println("Collecting dot at position: " + position + " with points: " + points);
        this.eaten = true;
        onCollect(game); // Metodo specifico per i Dot che possono avere logiche aggiuntive
    }

    protected abstract void onCollect(Game game);

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isEaten() {
        return eaten;
    }
}