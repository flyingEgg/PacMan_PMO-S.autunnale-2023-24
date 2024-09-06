package main.java.model.Entities;

import main.java.controller.Strategies.GhostChaseStrategy;
import main.java.controller.Strategies.GhostFleeStrategy;
import main.java.controller.Strategies.GhostMovementStrategy;
import main.java.model.API.Direction;
import main.java.model.API.Position;
import main.java.model.Grid;
import main.java.model.Model;
import main.java.view.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Ghost extends AbstractEntity {
    private GhostColor color;
    private GhostMovementStrategy movementStrategy;
    private boolean scared;
    private Grid grid;
    private Model model;
    private GamePanel gamePanel;
    private Direction direction;

    public Ghost(Position position, GhostColor color, Grid g, Model m, GamePanel gp) {
        super(position);
        this.color = color;
        this.scared = false;
        this.direction = null;
        this.grid = g;
        this.model = m;
        this.gamePanel = gp;
        this.movementStrategy = null;
        this.direction = Direction.UP;
        setInitialStrategy();
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        //ImageIcon ghostImage = scared ? images.get("ghost_scared") : getGhostImage(images);
        ImageIcon ghostImage = switch (this.color) {
            case BLUE -> images.get("ghost_blue");
            case ORANGE -> images.get("ghost_orange");
            case PINK -> images.get("ghost_pink");
            case RED -> images.get("ghost_red");
        };

        if (ghostImage != null) {
            g2d.drawImage(ghostImage.getImage(), position.getX() * Grid.CELL_SIZE, position.getY() * Grid.CELL_SIZE,
                    null);
        } else {
            System.out.println("Ghost image not found for color: " + color);
        }
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction d) {
        // forse da rimuovere
    }

    private ImageIcon getGhostImage(Map<String, ImageIcon> images) {
        return switch (this.color) {
            case BLUE -> images.get("ghost_blue");
            case ORANGE -> images.get("ghost_orange");
            case PINK -> images.get("ghost_pink");
            case RED -> images.get("ghost_red");
        };
    }

    public void move() {
        if (movementStrategy != null) {
            // Usa la direzione corrente per determinare il movimento
            Direction nextDirection = movementStrategy.determineNextDirection();
            setDirection(nextDirection); // Aggiorna la direzione
            movementStrategy.move(nextDirection);
        } else {
            System.out.println("Movement strategy is not set for ghost.");
        }
    }

    public void setScaredMode(boolean scared) {
        this.scared = scared;
        if (scared) {
            System.out.println("Ghost at position " + getPosition() + " is now scared and running away!");
            setMovementStrategy(new GhostFleeStrategy(this, grid, model, gamePanel));
        } else {
            System.out.println("Ghost at position " + getPosition() + " is back to chase mode.");
            setMovementStrategy(new GhostChaseStrategy(this, grid, model, gamePanel));
        }
    }

    public boolean isScared() {
        return scared;
    }

    public void setMovementStrategy(GhostMovementStrategy strategy) {
        this.movementStrategy = strategy;
        System.out.println("Movement strategy set successfully for ghost.");
    }

    public GhostMovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void runAway() {
        System.out.println("Ghost at position " + getPosition() + " is running away!");
        setMovementStrategy(new GhostFleeStrategy(this, grid, model, gamePanel)); // Change strategy to flee
    }

    public GhostColor getColor() {
        return color;
    }

    private void setInitialStrategy(){
        switch (color) {
            case RED -> movementStrategy = new GhostChaseStrategy(this, grid, model, gamePanel);
            case BLUE -> movementStrategy = new GhostChaseStrategy(this, grid, model, gamePanel);
            case PINK -> movementStrategy = new GhostChaseStrategy(this, grid, model, gamePanel);
            case ORANGE -> movementStrategy = new GhostChaseStrategy(this, grid, model, gamePanel);
        }
    }
}
