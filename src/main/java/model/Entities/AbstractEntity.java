package main.java.model.Entities;

import java.awt.*;
import java.util.Map;

import main.java.model.API.MapComponent;
import main.java.model.API.Position;

import javax.swing.*;

public abstract class AbstractEntity implements MapComponent {
    protected int x;
    protected int y;

    public AbstractEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public abstract void draw(Graphics2D g2d, Map<String, ImageIcon> images);

    @Override
    public Position getPosition() {
        return new Position(x, y);
    }

    public void setPosition(Position position) {
        this.x = position.getX();
        this.y = position.getY();
    }
}
