package Entities.Ghost;

import Entities.AbstractEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Ghost extends AbstractEntity {
    private Color color;

    public Ghost(int x, int y, Color c) {
        super(x, y);
        this.color = c;
    }

    @Override
    protected void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, BufferedImage> images) {
        g2d.drawImage(images.get("ghost"), x * 20, y * 20, null);
    }

    public void runAway() {
        System.out.println("Fantasma alla posizione " + getPosition() + " sta scappando!");
    }

    public Color getColor() {
        return color;
    }
}
