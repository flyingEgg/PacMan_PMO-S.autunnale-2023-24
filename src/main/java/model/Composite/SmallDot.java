package main.java.model.Composite;

import main.java.model.Model;
import main.java.model.API.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SmallDot extends Dot {
    private static final int POINTS = 10; // Punti per SmallDot

    public SmallDot(Position position) {
        super(position, POINTS); // 10 points for a small dot
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(position.getX() * 20 + 5, position.getY() * 20 + 5, 10, 10);
    }

    @Override
    public String toString() {
        return "SmallDot at " + position + " [eaten=" + eaten + "]";
    }

    @Override
    protected void onCollect(Model model) {
        model.incrementScore(points); // Aumenta il punteggio
        model.notifyScoreChanged(); // Notifica il cambiamento di punteggio
        System.out.println("SmallDot mangiato alla posizione: " + getPosition());
    }
}