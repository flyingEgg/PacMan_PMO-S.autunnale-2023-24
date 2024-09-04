package Entities.Ghost;

import Entities.AbstractEntity;
import Game.Game;
import Game.Grid;
import Game.GUI.GamePanel;
import Game.Strategies.GhostFleeStrategy;
import Game.Strategies.GhostMovementStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Ghost extends AbstractEntity {
    private Color color;
    private GhostMovementStrategy movementStrategy;
    private Grid grid;
    private Game game;
    private GamePanel gamePanel;

    public Ghost(int x, int y, Color c) {
        super(x, y);
        this.color = c;
        this.movementStrategy = null; // O altra strategia
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        switch (this.color) {
            case BLUE -> g2d.drawImage(images.get("ghost_blue").getImage(), x * 20, y * 20, null);
            case ORANGE -> g2d.drawImage(images.get("ghost_orange").getImage(), x * 20, y * 20, null);
            case PINK -> g2d.drawImage(images.get("ghost_pink").getImage(), x * 20, y * 20, null);
            case RED -> g2d.drawImage(images.get("ghost_red").getImage(), x * 20, y * 20, null);
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
        if (strategy != null) {
            System.out.println("Strategia di movimento per il fantasma impostata correttamente.");
        }
    }

    public void runAway() {
        System.out.println("Fantasma alla posizione " + getPosition() + " sta scappando!");
        setMovementStrategy(new GhostFleeStrategy(this, grid, game, gamePanel)); // Cambia strategia per fuggire
    }

    public Color getColor() {
        return color;
    }
}
