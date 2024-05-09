package DesignPatterns.Composite;

import API.MapComponent;

public class GameMap {
    private int[][] mapData;
    private int height;
    private int width;

    public GameMap(int[][] mapData) {
        this.mapData = mapData;
    }

    public int getValueAt(int x, int y) {
        return mapData[y][x];
    }

    public void setValueAt(int x, int y, int value) {
        mapData[y][x] = value;
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
