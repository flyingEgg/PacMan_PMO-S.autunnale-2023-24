package Entities.Ghost;

import Entities.AbstractEntity;
import Game.Game;
import Game.Grid;
import Game.Strategies.GhostChaseStrategy;
import Game.Strategies.GhostFleeStrategy;
import Game.Strategies.GhostMovementStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Ghost extends AbstractEntity {
    private Color color;
    private GhostMovementStrategy movementStrategy;
    private Grid grid;
    private Game game;

    public Ghost(int x, int y, Color c) {
        super(x, y);
        this.color = c;
        this.movementStrategy = null; // O altra strategia
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, BufferedImage> images) {
        switch (this.color) {
            case BLUE -> g2d.drawImage(images.get("ghost_blue"), x * 20, y * 20, null);
            case ORANGE -> g2d.drawImage(images.get("ghost_orange"), x * 20, y * 20, null);
            case PINK -> g2d.drawImage(images.get("ghost_pink"), x * 20, y * 20, null);
            case RED -> g2d.drawImage(images.get("ghost_red"), x * 20, y * 20, null);
        }
    }

    public void move() {
        movementStrategy.move(movementStrategy.determineNextDirection());
    }

    public GhostMovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(GhostMovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public void runAway() {
        System.out.println("Fantasma alla posizione " + getPosition() + " sta scappando!");
        setMovementStrategy(new GhostFleeStrategy(this, grid, game, null)); // Cambia strategia per fuggire
    }

    public Color getColor() {
        return color;
    }
}
