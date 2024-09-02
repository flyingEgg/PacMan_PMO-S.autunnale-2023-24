package Entities;

import Game.PacmanGrid;

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
    public void draw(Graphics2D g2d, Map<String, BufferedImage> images) {
        BufferedImage pacmanImage = images.get("pacman");
        if (pacmanImage != null) {
            g2d.drawImage(pacmanImage, x * PacmanGrid.CELL_SIZE, y * PacmanGrid.CELL_SIZE, null);
        } else {
            System.out.println("Pacman image not found!");
        }
    }

    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }

    public boolean isSuperMode() {
        return superMode;
    }
}
