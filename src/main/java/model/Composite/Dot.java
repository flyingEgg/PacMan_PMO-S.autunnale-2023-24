package main.java.model.Composite;

import main.java.model.Model;
import main.java.model.API.MapComponent;
import main.java.model.API.Position;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public abstract class Dot implements MapComponent {
    protected final Position position;
    protected boolean eaten;
    protected final int points;

    public Dot(Position position, int points) {
        this.position = position;
        this.eaten = false;
        this.points = points;
    }

    @Override
    public abstract void draw(Graphics2D g2d, Map<String, ImageIcon> images);

    public void collect(Model model) {
        this.eaten = true;
        onCollect(model); // Call the method for additional logic
    }

    protected abstract void onCollect(Model model);

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isEaten() {
        return eaten;
    }
}