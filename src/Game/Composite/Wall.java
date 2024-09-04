package Game.Composite;

import API.MapComponent;
import Game.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Wall implements MapComponent {
    private final Position position;

    public Wall(Position position) {
        this.position = position;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(position.getX() * 20, position.getY() * 20, 20, 20);
    }

    @Override
    public Position getPosition() {
        return position;
    }
}