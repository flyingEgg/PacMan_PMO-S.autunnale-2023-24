package DesignPatterns.Composite;

import API.MapComponent;

public class Grid {
    private MapComponent[][] grid;

    public Grid(int rows, int cols) {
        grid = new MapComponent[rows][cols];
    }

    public void addComponent(MapComponent component, Position position) {
        // Logica per aggiungere un componente alla griglia nella posizione specificata
        grid[position.getRow()][position.getCol()] = component;
    }

    public void removeComponent(Position position) {
        // Logica per rimuovere un componente dalla griglia nella posizione specificata
        grid[position.getRow()][position.getCol()] = null;
    }

    public MapComponent getComponent(Position position) {
        // Logica per ottenere un componente dalla griglia nella posizione specificata
        return grid[position.getRow()][position.getCol()];
    }
}