package Game;

import API.MapComponent;

import java.util.Optional;

import Util.Pair;

public abstract class AbsGrid {
    private final MapComponent[][] grid;

    public AbsGrid(int columns, int rows) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Invalid grid dimensions");
        }
        this.grid = new MapComponent[rows][columns];
    }

    public void addComponent(MapComponent component) {
        Position position = component.getPosition();
        Pair<Integer, Integer> coordinates = new Pair<>(position.getX(), position.getY());

        if (isValidPosition(coordinates)) {
            grid[coordinates.getB()][coordinates.getA()] = component;
        } else {
            System.out.println("Invalid position: (" + coordinates + ")");
        }
    }

    // Questo metodo rimuove i componenti e le entities
    public void removeComponent(MapComponent component) {
        Position position = component.getPosition();
        Pair<Integer, Integer> coordinates = new Pair<>(position.getX(), position.getY());

        int x = position.getX();
        int y = position.getY();

        if (isValidPosition(coordinates)) {
            grid[coordinates.getB()][coordinates.getA()] = null;
        } else {
            System.out.println("Invalid position: (" + coordinates + ")");
        }
    }

    public Optional<MapComponent> getComponentByPosition(Position position) {
        Pair<Integer, Integer> coordinates = new Pair<>(position.getX(), position.getY());

        if (isValidPosition(coordinates)) {
            return Optional.ofNullable(grid[coordinates.getB()][coordinates.getA()]);
        } else {
            System.out.println("Invalid position: (" + coordinates + ")");
            return Optional.empty();
        }
    }

    public int getColumns() {
        return grid[0].length;
    }

    public int getRows() {
        return grid.length;
    }

    private boolean isValidPosition(Pair<Integer, Integer> coord) {
        return coord.getA() >= 0 && coord.getA() < grid[0].length && coord.getB() >= 0 && coord.getB() < grid.length;
    }
}