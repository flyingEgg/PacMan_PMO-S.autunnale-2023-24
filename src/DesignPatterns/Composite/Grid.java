package DesignPatterns.Composite;

import API.MapComponent;

public class Grid {
    private MapComponent[][] grid;

    public Grid(MapComponent[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            throw new IllegalArgumentException("Invalid map dimensions");
        }
        this.grid = map;
    }

    public void addComponent(MapComponent component, Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            grid[x][y] = component;
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
        }
    }

    public void removeComponent(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            grid[x][y] = null;
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
        }
    }

    public MapComponent getComponent(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            return grid[x][y];
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
            return null;
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
}