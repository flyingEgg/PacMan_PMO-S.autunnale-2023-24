package Game;

import API.MapComponent;

public class Grid {
    private final MapComponent[][] grid;

    public Grid(int columns, int rows) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Invalid grid dimensions");
        }
        this.grid = new MapComponent[rows][columns];
    }

    public void addComponent(MapComponent component) {
        Position position = component.getPosition();
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            grid[x][y] = component;
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
        }
    }

    // Questo metodo rimuove i componenti e le entities
    public void removeComponent(MapComponent component) {
        Position position = component.getPosition();
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            grid[x][y] = null;
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
        }
    }

    public MapComponent getComponent(MapComponent component) {
        Position position = component.getPosition();
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            return grid[x][y];
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
            return null;
        }
    }

    public MapComponent getComponentByPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            return grid[x][y];
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
            return null;
        }
    }

    public int getColumns() {
        return grid[0].length;
    }

    public int getRows() {
        return grid.length;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }
}