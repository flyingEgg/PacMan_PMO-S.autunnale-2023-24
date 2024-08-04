package Game;

import API.MapComponent;
import Entities.Ghost.Color;
import Entities.Ghost.Ghost;
import Entities.Pacman;
import Game.Composite.BigDot;
import Game.Composite.EmptySpace;
import Game.Composite.SmallDot;
import Game.Composite.Wall;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PacmanGrid extends Grid {
    private static final int COLUMNS = 21; // Numero di colonne della griglia
    private static final int ROWS = 19; // Numero di righe della griglia
    private Set<Position> excludedPositions;

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

    // Posizioni dei Big Dot
    private static final int[][] BIG_DOT_POSITIONS = {
            { 1, 1  }, { 19, 1 }, { 1, 17 }, { 19, 17 }
    };

    private static final int[][] MAGIC_COORDS = {
            { 0, 9 }, { 18, 9 }
    };

    // Posizione di partenza di Pac-Man
    private static final Position PACMAN_START_POSITION = new Position(11, 9);

    // Posizioni di spawn dei fantasmi
    private static final Position[] GHOST_SPAWN_POSITIONS = {
            new Position(9, 9), new Position(9, 8), new Position(9, 10)
    };

    // Altre posizioni escluse
    private static final int[][] OTHER_EXCLUDED_POSITIONS = {
            { 9, 1 }, { 9, 2 }, { 9, 3 }, { 9, 15 }, { 9, 16 }, { 9, 17 }
    };

    public PacmanGrid() {
        super(COLUMNS, ROWS);
        initializeExcludedPositions();
        initializeMap();
    }

    public Set<Position> getWallPositions(){
        return getArray(WALL_POSITIONS);
    }

    public Set<Position> getMagicCoords(){
        return getArray(MAGIC_COORDS);
    }

    private void initializeExcludedPositions() {
        excludedPositions = new HashSet<>();

        // Aggiungi posizioni dei muri
        addPositions(WALL_POSITIONS);


        // Aggiungi posizioni dei Big Dot
        addPositions(BIG_DOT_POSITIONS);


        // Aggiungi posizione di Pac-Man
        excludedPositions.add(PACMAN_START_POSITION);

        // Aggiungi posizioni di spawn dei fantasmi
        excludedPositions.addAll(Arrays.asList(GHOST_SPAWN_POSITIONS));

        // Aggiungi altre posizioni escluse
        addPositions(OTHER_EXCLUDED_POSITIONS);

    }

    private void initializeMap() {
        // Inizializza la griglia con spazi vuoti e Small Dot
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position currentPosition = new Position(j, i);
                addComponent(new EmptySpace(currentPosition));
                if (!excludedPositions.contains(currentPosition)) {
                    addComponent(new SmallDot(currentPosition));
                }
            }
        }

        addWallsToGrid();
        addPacmanToGrid();
        addGhostsToGrid();
        addBigDotsToGrid();
    }

    private void addWallsToGrid() {
        Arrays.stream(WALL_POSITIONS)
                .map(pos -> new Wall(new Position(pos[0], pos[1])))
                .forEach(this::addComponent);
    }

    private void addPacmanToGrid() {
        addComponent(new Pacman(PACMAN_START_POSITION.getX(), PACMAN_START_POSITION.getY()));
    }

    private void addGhostsToGrid() {
        IntStream.range(0, GHOST_SPAWN_POSITIONS.length).mapToObj(i -> {
            Position pos = GHOST_SPAWN_POSITIONS[i];
            Color color = Color.values()[i % Color.values().length];
            return new Ghost(pos.getX(), pos.getY(), color);
        }).forEach(this::addComponent);
    }

    private void addBigDotsToGrid() {
        Arrays.stream(BIG_DOT_POSITIONS)
                .map(pos -> new BigDot(new Position(pos[0], pos[1])))
                .forEach(this::addComponent);
    }

    public void printGrid() {
        Optional<MapComponent> component;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                component = getComponentByPosition(new Position(j, i));
                if(component.isPresent()){
                    component.get().draw();
                }else{
                    System.out.println(" ");
                }
            }
            System.out.println(); // A capo alla fine di ogni riga
        }
    }

    private void addPositions(int[][] positionsArray){
        Arrays.stream(positionsArray).forEach(pos -> excludedPositions.add(new Position(pos[0], pos[1])));
    }

    private Set<Position> getArray(int[][] ARR){
        return Arrays.stream(ARR).
                map(p -> new Position(p[0], p[1])).
                collect(Collectors.toSet());
    }
}