package DesignPatterns.Composite;

import API.MapComponent;

public class PacmanGrid extends Grid {
    private static final int COLUMNS = 28; // Numero di colonne della griglia
    private static final int ROWS = 22; // Numero di righe della griglia

    // Posizioni dei muri
    private static final int[][] WALL_POSITIONS = {
            { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 }, { 8, 0 }, { 9, 0 },
            { 10, 0 }, { 11, 0 }, { 12, 0 }, { 13, 0 }, { 14, 0 }, { 15, 0 }, { 16, 0 }, { 17, 0 }, { 18, 0 },
            { 19, 0 }, { 20, 0 },
            { 0, 1 }, { 6, 1 }, { 8, 1 }, { 10, 1 }, { 12, 1 }, { 16, 1 }, { 20, 1 },
            { 0, 2 }, { 2, 2 }, { 4, 2 }, { 6, 2 }, { 8, 2 }, { 10, 2 }, { 12, 2 }, { 14, 2 }, { 18, 2 }, { 20, 2 },
            { 0, 3 }, { 2, 3 }, { 4, 3 }, { 6, 3 }, { 7, 3 }, { 8, 3 }, { 10, 3 }, { 11, 3 }, { 12, 3 }, { 14, 3 },
            { 15, 3 }, { 16, 3 }, { 18, 3 }, { 20, 3 },
            { 0, 4 }, { 18, 4 }, { 20, 4 },
            { 0, 5 }, { 2, 5 }, { 4, 5 }, { 5, 5 }, { 6, 5 }, { 7, 5 }, { 8, 5 }, { 10, 5 }, { 11, 5 }, { 12, 5 },
            { 14, 5 }, { 16, 5 }, { 17, 5 }, { 18, 5 }, { 20, 5 },
            { 0, 6 }, { 2, 6 }, { 6, 6 }, { 14, 6 }, { 18, 6 }, { 20, 6 },
            { 0, 7 }, { 2, 7 }, { 4, 7 }, { 6, 7 }, { 8, 7 }, { 9, 7 }, { 10, 7 }, { 12, 7 }, { 14, 7 }, { 16, 7 },
            { 18, 7 }, { 20, 7 },
            { 0, 8 }, { 4, 8 }, { 8, 8 }, { 10, 8 }, { 12, 8 }, { 16, 8 }, { 20, 8 },
            { 0, 9 }, { 1, 9 }, { 2, 9 }, { 4, 9 }, { 5, 9 }, { 6, 9 }, { 8, 9 }, { 10, 9 }, { 12, 9 }, { 13, 9 },
            { 14, 9 }, { 16, 9 }, { 17, 9 }, { 18, 9 }, { 20, 9 },
            { 0, 10 }, { 4, 10 }, { 8, 10 }, { 10, 10 }, { 12, 10 }, { 16, 10 }, { 20, 10 },
            { 0, 11 },
            { 0, 12 }, { 27, 12 },
            { 0, 13 }, { 27, 13 },
            { 0, 14 },
            { 0, 15 }, { 11, 15 }, { 16, 15 }, { 27, 15 },
            { 0, 16 }, { 11, 16 }, { 16, 16 }, { 27, 16 },
            { 0, 17 }, { 11, 17 }, { 16, 17 }, { 27, 17 },
            { 0, 18 }, { 11, 18 }, { 16, 18 }, { 27, 18 },
            { 0, 19 },
            { 0, 20 }, { 27, 20 },
            { 0, 21 }, { 27, 21 }
    };

    public PacmanGrid() {
        super(COLUMNS, ROWS);
        initializeMap();
    }

    private void initializeMap() {
        // Inizializza la griglia con spazi vuoti
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                // Imposta uno spazio vuoto in ogni cella della griglia
                addComponent(null, new Position(j, i));
            }
        }

        // Aggiungi muri alla griglia
        addWallsToGrid();
    }

    private void addWallsToGrid() {
        // Aggiungi i muri alla griglia
        for (int[] position : WALL_POSITIONS) {
            addComponent(new Wall(), new Position(position[0], position[1]));
        }

        // Posizione di partenza dei fantasmi
        // addComponent(new GhostStartPoint(), new Position(14, 11)); // Invertito
        // l'ordine dei parametri
    }

    public void printGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                MapComponent component = getComponent(new Position(j, i));
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
