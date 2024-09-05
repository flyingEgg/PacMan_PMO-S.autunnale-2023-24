package Entities;

import Game.Grid;
import Game.Strategies.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Pacman extends AbstractEntity {
    private boolean superMode;
    private Direction direction;

    public Pacman(int x, int y) {
        super(x, y);
        this.direction = Direction.RIGHT;
        this.superMode = false;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        ImageIcon pacmanImage = images.get("right");
        switch (direction) {
            case RIGHT -> pacmanImage = images.get("right");
            case LEFT -> pacmanImage = images.get("left");
            case UP -> pacmanImage = images.get("up");
            case DOWN -> pacmanImage = images.get("down");
        }

        if (pacmanImage != null) {
            g2d.drawImage(pacmanImage.getImage(), x * Grid.CELL_SIZE, y * Grid.CELL_SIZE, null);
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

    public void setDirection(Direction d) {
        this.direction = d;
    }
}
