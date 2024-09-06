package main.java.model.Entities;

import main.java.model.API.Direction;
import main.java.model.Model;
import main.java.model.API.Position;
import main.java.model.Grid;
import main.java.controller.Strategies.GhostFleeStrategy;
import main.java.controller.Strategies.GhostMovementStrategy;
import main.java.view.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Ghost extends AbstractEntity {
    private GhostColor color;
    private GhostMovementStrategy movementStrategy;
    private Grid grid;
    private Model model;
    private GamePanel gamePanel;

    public Ghost(Position position, GhostColor color) {
        super(position);
        this.color = color;
        this.movementStrategy = null;
    }

    @Override
    public void draw(Graphics2D g2d, Map<String, ImageIcon> images) {
        ImageIcon ghostImage = null;

        switch (this.color) {
            case BLUE -> ghostImage = images.get("ghost_blue");
            case ORANGE -> ghostImage = images.get("ghost_orange");
            case PINK -> ghostImage = images.get("ghost_pink");
            case RED -> ghostImage = images.get("ghost_red");
        }

        if (ghostImage != null) {
            g2d.drawImage(ghostImage.getImage(), position.getX() * Grid.CELL_SIZE, position.getY() * Grid.CELL_SIZE,
                    null);
        } else {
            System.out.println("Ghost image not found for color: " + color);
        }
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setDirection(Direction d) {

    }

    public void move() {
        if (movementStrategy != null) {
            movementStrategy.move(movementStrategy.determineNextDirection());
        } else {
            System.out.println("Movement strategy is not set for ghost.");
        }
    }

    public GhostMovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(GhostMovementStrategy strategy) {
        this.movementStrategy = strategy;
        if (strategy != null) {
            System.out.println("Movement strategy set successfully for ghost.");
        }
    }

    public void runAway() {
        System.out.println("Ghost at position " + getPosition() + " is running away!");
        setMovementStrategy(new GhostFleeStrategy(this, grid, model, gamePanel)); // Change strategy to flee
    }

    public GhostColor getColor() {
        return color;
    }
}
