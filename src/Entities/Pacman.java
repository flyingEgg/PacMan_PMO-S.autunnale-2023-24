package Entities;

import API.MapComponent;
import Game.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Pacman extends AbstractEntity {
    private boolean superMode;

    public Pacman(int x, int y) {
        super(x, y);
        this.superMode = false;
    }

    @Override
    protected void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, BufferedImage> images) {
        g2d.drawImage(images.get("pacman"), x * 20, y * 20, null);
    }

    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }

    public boolean isSuperMode() {
        return superMode;
    }
}
