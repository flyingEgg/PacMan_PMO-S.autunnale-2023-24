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
    protected int points;

    public Dot(Position position, int points) {
        this.position = position;
        this.eaten = false;
        this.points = points;
    }

    @Override
    public abstract void draw(Graphics2D g2d, Map<String, BufferedImage> images);

    public void collect(Game game) {
        game.incrementScore(points);
        System.out.println("Collecting dot at position: " + position);
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