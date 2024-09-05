package main.java.model.Composite;

import main.java.model.API.MapComponent;
import main.java.model.API.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class EmptySpace implements MapComponent {
    private Position position;

    public EmptySpace(Position p) {
        this.position = p;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(position.getX() * 20, position.getY() * 20, 20, 20);
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
