package main.java.model.Entities;

import java.awt.*;
import java.util.Map;

import javax.swing.ImageIcon;

import main.java.model.API.Direction;
import main.java.model.API.MapComponent;
import main.java.model.API.Position;

public abstract class AbstractEntity implements MapComponent {
    protected Position position;

    public AbstractEntity(Position position) {
        this.position = position;
    }

    public int getX() {
        return position.getX();
    }

    public void setX(int x) {
        position = new Position(x, position.getY());
    }

    public int getY() {
        return position.getY();
    }

    public void setY(int y) {
        position = new Position(position.getX(), y);
    }

    @Override
    public abstract void draw(Graphics2D g2d, Map<String, ImageIcon> images);

    @Override
    public Position getPosition() {
        return position;
    }

    public abstract Direction getDirection();
    public abstract void setDirection(Direction d);


    public void setPosition(Position position) {
        this.position = position;
    }
}
