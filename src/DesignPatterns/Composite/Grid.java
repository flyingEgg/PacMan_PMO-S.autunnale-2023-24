package DesignPatterns.Composite;

import API.MapComponent;

public class Grid {
    private MapComponent[][] grid;

    public Grid(int rows, int cols) {
        this.grid = new MapComponent[rows][cols];
    }

    public void addComponent(MapComponent component, Position position) {
        // Implementazione per aggiungere un componente alla griglia nella posizione
        // specificata
        int row = position.getRow();
        int col = position.getCol();
        grid[row][col] = component;
    }

    public void removeComponent(Position position) {
        // Implementazione per rimuovere un componente dalla griglia nella posizione
        // specificata
        int row = position.getRow();
        int col = position.getCol();
        grid[row][col] = null;
    }

    public MapComponent getComponent(Position position) {
        // Implementazione per ottenere un componente dalla griglia nella posizione
        // specificata
        int row = position.getRow();
        int col = position.getCol();
        return grid[row][col];
    }
}