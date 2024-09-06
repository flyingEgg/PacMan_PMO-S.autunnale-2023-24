package main.java.model.Entities;

import main.java.model.Grid;
import main.java.model.API.Direction;
import main.java.model.API.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Pacman extends AbstractEntity {
    private Direction direction;
    private boolean superMode;

    public Pacman(Position position) {
        super(position);
        this.direction = Direction.RIGHT;
        this.superMode = false;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        ImageIcon pacmanImage = images.get("right"); // Default direction

        switch (direction) {
            case RIGHT -> pacmanImage = images.get("right");
            case LEFT -> pacmanImage = images.get("left");
            case UP -> pacmanImage = images.get("up");
            case DOWN -> pacmanImage = images.get("down");
        }

        if (pacmanImage != null) {
            g2d.drawImage(pacmanImage.getImage(), position.getX() * Grid.CELL_SIZE, position.getY() * Grid.CELL_SIZE,
                    null);
        } else {
            System.out.println("Pacman image not found for direction: " + direction);
        }
    }

    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }

    public boolean isSuperMode() {
        return superMode;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction d) {
        this.direction = d;
    }

    public void resetPosition(Position newPosition) {
        setPosition(newPosition);
    }
}
