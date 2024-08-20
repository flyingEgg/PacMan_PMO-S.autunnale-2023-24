package Entities;

import Game.Game;
import Game.Strategies.PacmanMovementStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Pacman extends AbstractEntity {
    private boolean superMode;
    private PacmanMovementStrategy myMovementStrat;

    public Pacman(int x, int y) {
        super(x, y);
        this.superMode = false;
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

    public PacmanMovementStrategy getMyMovementStrat(){
        return myMovementStrat;
    }
}
