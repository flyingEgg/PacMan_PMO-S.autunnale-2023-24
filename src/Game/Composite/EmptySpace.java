package Game.Composite;

import API.MapComponent;
import Game.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class EmptySpace implements MapComponent {
    private Position position;

    public EmptySpace(Position p) {
        this.position = p;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, BufferedImage> images) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(position.getX() * 20, position.getY() * 20, 20, 20);
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
