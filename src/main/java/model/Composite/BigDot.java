package main.java.model.Composite;

import main.java.model.Model;
import main.java.model.API.Position;

import java.awt.*;
import java.util.Map;

import javax.swing.*;

public class BigDot extends Dot {
    private static final int POINTS = 0; // BigDot non contribuisce al punteggio direttamente
    private boolean eaten;

    public BigDot(Position position) {
        super(position, POINTS); // 0 points for a big dot
        this.eaten = false;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(position.getX() * 20, position.getY() * 20, 20, 20);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isEaten() {
        return eaten;
    }

    @Override
    protected void onCollect(Model model) {
        model.activateSuperMode(20);
        model.getGrid().removeComponent(this);
        this.eaten = true;
        System.out.println("Big dot collected at position: " + getPosition());
    }
}