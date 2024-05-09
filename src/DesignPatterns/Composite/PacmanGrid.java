package DesignPatterns.Composite;

import API.MapComponent;

public class PacmanGrid extends Grid {
    private static final int ROWS = 22; // Numero di righe della griglia
    private static final int COLUMNS = 28; // Numero di colonne della griglia

    public PacmanGrid() {
        super(new MapComponent[ROWS][COLUMNS]);
        initializeMap();
    }

    private void initializeMap() {
        // Inizializza la griglia con spazi vuoti
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                // Imposta uno spazio vuoto in ogni cella della griglia
                addComponent(null, new Position(i, j));
            }
        }

        // Aggiungi blocchi alla griglia
        addWallsToGrid();
    }

    private void addWallsToGrid() {
        // Posizioni dei muri
        int[][] wallPositions = {
                // Muri esterni
                { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 }, { 0, 6 }, { 0, 7 }, { 0, 8 }, { 0, 9 },
                { 0, 10 }, { 0, 11 }, { 0, 12 }, { 0, 13 }, { 0, 14 }, { 0, 15 }, { 0, 16 }, { 0, 17 }, { 0, 18 },
                { 0, 19 }, { 0, 20 }, { 0, 21 }, { 0, 22 }, { 0, 23 }, { 0, 24 }, { 0, 25 }, { 0, 26 }, { 0, 27 },
                { 1, 0 }, { 1, 27 },
                { 2, 0 }, { 2, 27 },
                { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 }, { 3, 4 }, { 3, 5 }, { 3, 6 }, { 3, 7 }, { 3, 8 }, { 3, 9 },
                { 3, 10 }, { 3, 11 }, { 3, 12 }, { 3, 13 }, { 3, 14 }, { 3, 15 }, { 3, 16 }, { 3, 17 }, { 3, 18 },
                { 3, 19 }, { 3, 20 }, { 3, 21 }, { 3, 22 }, { 3, 23 }, { 3, 24 }, { 3, 25 }, { 3, 26 }, { 3, 27 },
                { 4, 0 }, { 4, 27 },
                { 5, 0 }, { 5, 1 }, { 5, 2 }, { 5, 3 }, { 5, 4 }, { 5, 5 }, { 5, 6 }, { 5, 7 }, { 5, 8 }, { 5, 9 },
                { 5, 10 }, { 5, 11 }, { 5, 16 }, { 5, 17 }, { 5, 18 }, { 5, 19 }, { 5, 20 }, { 5, 21 }, { 5, 22 },
                { 5, 23 }, { 5, 24 }, { 5, 25 }, { 5, 26 }, { 5, 27 },
                { 6, 0 }, { 6, 11 }, { 6, 16 }, { 6, 27 },
                { 7, 0 }, { 7, 11 }, { 7, 16 }, { 7, 27 },
                { 8, 0 }, { 8, 11 }, { 8, 16 }, { 8, 27 },
                { 9, 0 }, { 9, 11 }, { 9, 16 }, { 9, 27 },
                { 10, 0 }, { 10, 1 }, { 10, 2 }, { 10, 3 }, { 10, 4 }, { 10, 5 }, { 10, 6 }, { 10, 7 }, { 10, 8 },
                { 10, 9 }, { 10, 10 }, { 10, 11 }, { 10, 16 }, { 10, 17 }, { 10, 18 }, { 10, 19 }, { 10, 20 },
                { 10, 21 }, { 10, 22 }, { 10, 23 }, { 10, 24 }, { 10, 25 }, { 10, 26 }, { 10, 27 },
                { 11, 0 }, { 11, 27 },
                { 12, 0 }, { 12, 27 },
                { 13, 0 }, { 13, 27 },
                { 14, 0 }, { 14, 1 }, { 14, 2 }, { 14, 3 }, { 14, 4 }, { 14, 5 }, { 14, 6 }, { 14, 7 }, { 14, 8 },
                { 14, 9 }, { 14, 10 }, { 14, 11 }, { 14, 16 }, { 14, 17 }, { 14, 18 }, { 14, 19 }, { 14, 20 },
                { 14, 21 }, { 14, 22 }, { 14, 23 }, { 14, 24 }, { 14, 25 }, { 14, 26 }, { 14, 27 },
                { 15, 0 }, { 15, 11 }, { 15, 16 }, { 15, 27 },
                { 16, 0 }, { 16, 11 }, { 16, 16 }, { 16, 27 },
                { 17, 0 }, { 17, 11 }, { 17, 16 }, { 17, 27 },
                { 18, 0 }, { 18, 11 }, { 18, 16 }, { 18, 27 },
                { 19, 0 }, { 19, 1 }, { 19, 2 }, { 19, 3 }, { 19, 4 }, { 19, 5 }, { 19, 6 }, { 19, 7 }, { 19, 8 },
                { 19, 9 }, { 19, 10 }, { 19, 11 }, { 19, 16 }, { 19, 17 }, { 19, 18 }, { 19, 19 }, { 19, 20 },
                { 19, 21 }, { 19, 22 }, { 19, 23 }, { 19, 24 }, { 19, 25 }, { 19, 26 }, { 19, 27 },
                { 20, 0 }, { 20, 27 },
                { 21, 0 }, { 21, 27 },
                { 22, 0 }, { 22, 1 }, { 22, 2 }, { 22, 3 }, { 22, 4 }, { 22, 5 }, { 22, 6 }, { 22, 7 }, { 22, 8 },
                { 22, 9 }, { 22, 10 }, { 22, 11 }, { 22, 12 }, { 22, 13 }, { 22, 14 }, { 22, 15 }, { 22, 16 },
                { 22, 17 }, { 22, 18 }, { 22, 19 }, { 22, 20 }, { 22, 21 }, { 22, 22 }, { 22, 23 }, { 22, 24 },
                { 22, 25 }, { 22, 26 }, { 22, 27 },
        };

        // Aggiungi i muri alla griglia
        for (int[] position : wallPositions) {
            addComponent(new Wall(), new Position(position[0], position[1]));
        }

        // Posizione di partenza dei fantasmi
        // addComponent(new GhostStartPoint(), new Position(11, 14));
    }

    public void printGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                MapComponent component = getComponent(new Position(i, j));
                if (component instanceof Wall) {
                    System.out.print("#"); // Muro
                } else {
                    System.out.print("+"); // Spazio vuoto
                }
            }
            System.out.println(); // Vai a capo alla fine di ogni riga
        }
    }
}