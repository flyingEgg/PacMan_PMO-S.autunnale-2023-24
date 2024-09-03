package Game.Composite;

import Game.Game;
import Game.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import Entities.Pacman;
import Entities.Ghost.Ghost;

public class BigDot extends Dot {
    private static final int POINTS = 0; // BigDot non contribuisce al punteggio direttamente
    private boolean eaten;

    public BigDot(Position position) {
        super(position, POINTS); // 0 points for a big dot
        this.eaten = false;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, BufferedImage> images) {
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(position.getX() * 20, position.getY() * 20, 20, 20);
    }

    /*
     * @Override
     * public void collect(Game game) {
     * Pacman pacman = game.getPacman();
     * pacman.setSuperMode(true);
     * 
     * for (Ghost ghost : game.getGhosts()) {
     * ghost.runAway();
     * }
     * 
     * System.out.println("Collecting big dot at position: " + getPosition());
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
    protected void onCollect(Game game) {
        Pacman pacman = game.getPacman();
        pacman.setSuperMode(true);

        for (Ghost ghost : game.getGhosts()) {
            ghost.runAway();
        }

        game.getGrid().removeComponent(this); // Rimuovi il BigDot dalla griglia
        System.out.println("Big dot collected at position: " + getPosition());
    }
}