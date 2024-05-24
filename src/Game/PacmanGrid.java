package Game;

import API.MapComponent;
import Game.Composite.BigDot;
import Game.Composite.EmptySpace;
import Game.Composite.SmallDot;
import Game.Composite.Wall;

public class PacmanGrid extends Grid {
    private static final int COLUMNS = 21; // Numero di colonne della griglia
    private static final int ROWS = 19; // Numero di righe della griglia

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
            { 0, 11 }, { 2, 11 }, { 4, 11 }, { 6, 11 }, { 8, 11 }, { 9, 11 }, { 10, 11 }, { 12, 11 }, { 14, 11 },
            { 16, 11 }, { 18, 11 }, { 20, 11 },
            { 0, 12 }, { 2, 12 }, { 6, 12 }, { 14, 12 }, { 18, 12 }, { 20, 12 },
            { 0, 13 }, { 2, 13 }, { 4, 13 }, { 5, 13 }, { 6, 13 }, { 7, 13 }, { 8, 13 }, { 10, 13 }, { 11, 13 },
            { 12, 13 }, { 14, 13 }, { 16, 13 }, { 17, 13 }, { 18, 13 }, { 20, 13 },
            { 0, 14 }, { 18, 14 }, { 20, 14 },
            { 0, 15 }, { 2, 15 }, { 4, 15 }, { 6, 15 }, { 7, 15 }, { 8, 15 }, { 10, 15 }, { 11, 15 }, { 12, 15 },
            { 14, 15 }, { 15, 15 }, { 16, 15 }, { 18, 15 }, { 20, 15 },
            { 0, 16 }, { 2, 16 }, { 4, 16 }, { 6, 16 }, { 8, 16 }, { 10, 16 }, { 12, 16 }, { 14, 16 }, { 18, 16 },
            { 20, 16 },
            { 0, 17 }, { 6, 17 }, { 8, 17 }, { 10, 17 }, { 12, 17 }, { 16, 17 }, { 20, 17 },
            { 0, 18 }, { 1, 18 }, { 2, 18 }, { 3, 18 }, { 4, 18 }, { 5, 18 }, { 6, 18 }, { 7, 18 }, { 8, 18 },
            { 9, 18 }, { 10, 18 }, { 11, 18 }, { 12, 18 }, { 13, 18 }, { 14, 18 }, { 15, 18 }, { 16, 18 }, { 17, 18 },
            { 18, 18 }, { 19, 18 }, { 20, 18 }
    }; // eventualmente togliere { 0, 9 } e { 18, 9 } ed effettuare il teletrasporto da
       // unlato all'altro
       // tocca liberare i fantasmi da { 8, 9 }

    public PacmanGrid() {
        super(COLUMNS, ROWS);
        initializeMap();
    }

    private void initializeMap() {
        // Inizializza la griglia con spazi vuoti
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position currentPosition = new Position(j, i);
                // Imposta uno spazio vuoto in ogni cella della griglia
                /*addComponent(new EmptySpace());*/
                // Escludi le posizioni specificate
                if (!isExcludedPosition(currentPosition)) {
                    addComponent(new SmallDot(currentPosition));
                }
            }
        }

        // Aggiungi i big dot alla griglia nelle posizioni specificate
        addComponent(new BigDot(new Position(1, 1)));
        addComponent(new BigDot(new Position(19, 1)));
        addComponent(new BigDot(new Position(1, 17)));
        addComponent(new BigDot(new Position(19, 17)));
        // credo sia da fixare perchè addcomponent richiede una position ma i dot ne
        // hanno anche una propria nel costruttore

        // Aggiungi muri alla griglia
        addWallsToGrid();
    }

    private boolean isExcludedPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        // Check se la posizione è esclusa per essere vuota o per altre ragioni
        // specifiche
        if (x == 11 && y == 9) { // Escludi la posizione di partenza di Pac-Man
            return true;
        }

        // Escludi altre posizioni specifiche
        int[][] excludedPositions = {
                { 9, 0 }, { 9, 1 }, { 9, 2 }, { 9, 3 }, { 9, 15 }, { 9, 16 }, { 9, 17 }, { 9, 18 }, // Posizioni muro
                                                                                                    // fantasmi
                { 9, 8 }, { 9, 9 }, { 9, 10 } // Posizioni di spawn dei fantasmi
        };

        for (int[] excluded : excludedPositions) {
            if (x == excluded[0] && y == excluded[1]) {
                return true;
            }
        }

        return false;
    }

    private void addWallsToGrid() {
        // Aggiungi i muri alla griglia
        for (int[] position : WALL_POSITIONS) {
            addComponent(new Wall(new Position(position[0], position[1])));
        }

        // Posizione di partenza dei fantasmi
        // addComponent(new GhostStartPoint(), new Position(9, 9));
    }

    public void printGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                MapComponent component = getComponentByPosition(new Position(j, i));
                if (component instanceof Wall) {
                    System.out.print("#"); // Muro
                } else if (component instanceof BigDot) {
                    System.out.print("O"); // Pallino grande
                } else if (component instanceof SmallDot) {
                    System.out.print("."); // Pallino piccolo
                } else {
                    System.out.print(" "); // Spazio vuoto
                }
            }
            System.out.println(); // Vai a capo alla fine di ogni riga
        }
    }
}
