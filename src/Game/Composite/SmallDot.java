package Game.Composite;

import Game.Game;
import Game.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class SmallDot extends Dot {
    private static final int POINTS = 10; // Punti per SmallDot
    private boolean eaten;

    public SmallDot(Position position) {
        super(position, POINTS); // 10 points for a small dot
        this.eaten = false;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, BufferedImage> images) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(position.getX() * 20 + 5, position.getY() * 20 + 5, 10, 10);
    }

    /*
     * @Override
     * public void collect(Game game) {
     * if (game == null) {
     * throw new IllegalArgumentException("Game cannot be null");
     * }
     * 
     * System.out.println("Collecting small dot at position: " + getPosition());
     * game.incrementScore(points);
     * game.getGrid().removeDot(this); // Rimuove il punto dalla griglia
     * System.out.println(
     * "Score updated to: " + game.getScore() +
     * " after collecting small dot at position: " + getPosition());
     * this.eaten = true;
     * }
     */
    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isEaten() {
        return eaten;
    }

    @Override
    public String toString() {
        return "SmallDot at " + position + " [eaten=" + eaten + "]";
    }

    @Override
    protected void onCollect(Game game) {
        game.getGrid().removeComponent(this); // Rimuovi il punto dalla griglia
        System.out.println("Small dot collected at position: " + getPosition());
    }

}