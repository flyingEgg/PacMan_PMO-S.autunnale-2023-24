package Game;

import API.MapComponent;

public class Grid {
    private final MapComponent[][] grid;

    public Grid(int columns, int rows) { // Cambiato l'ordine dei parametri
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Invalid grid dimensions");
        }
        this.grid = new MapComponent[rows][columns]; // Modificato l'ordine delle dimensioni
    }

    public void addComponent(MapComponent component, Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            grid[y][x] = component; // Modificato l'accesso alla griglia
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
        }
    }

    public void removeComponent(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            grid[y][x] = null; // Modificato l'accesso alla griglia
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
        }
    }

    public MapComponent getComponent(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (isValidPosition(x, y)) {
            return grid[y][x]; // Modificato l'accesso alla griglia
        } else {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
            return null;
        }
    }

    public int getColumns() {
        return grid[0].length;
    }

    public int getRows(){
        return grid.length;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length; // Modificato il controllo di validità
    }
}