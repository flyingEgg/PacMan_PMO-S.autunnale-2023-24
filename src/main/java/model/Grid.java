package main.java.model;

import java.util.*;
/* 
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
*/

import main.java.model.API.Position;
import main.java.model.Composite.BigDot;
import main.java.model.Composite.SmallDot;
import main.java.model.Composite.Wall;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.Pacman;

public class Grid extends AbsGrid {
    public static final int CELL_SIZE = 20;
    private static final int COLUMNS = 21; // Numero di colonne della griglia
    private static final int ROWS = 19; // Numero di righe della griglia
    private Set<Position> excludedPositions;
    private Map<Position, SmallDot> smallDotMap;
    private Map<Position, BigDot> bigDotMap;

    // Posizi oni d ei m uri
    private static final int[][] WALL_POSITIONS = {
            { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 }, { 8, 0 },
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
            { 0, 9 }, { 1, 9 }, { 2, 9 }, { 4, 9 }, { 5, 9 }, { 6, 9 }, { 10, 9 }, { 12, 9 }, { 13, 9 },
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
            { 10, 18 }, { 11, 18 }, { 12, 18 }, { 13, 18 }, { 14, 18 }, { 15, 18 }, { 16, 18 }, { 17, 18 },
            { 18, 18 }, { 19, 18 }, { 20, 18 }
    };
    // tocca liberare i fantasmi da { 8, 9 }

    // Posizioni dei Big Dot
    private static final int[][] BIG_DOT_POSITIONS = {
            { 1, 1 }, { 19, 1 }, { 1, 17 }, { 19, 17 }
    };

    private static final int[][] MAGIC_COORDS = {
            { 9, 0 }, { 9, 18 }
    };

    // Posizione di partenza di Pac-Man
    private static final Position PACMAN_START_POSITION = new Position(11, 9);

    // Posizioni di spawn dei fantasmi
    private static final Position[] GHOST_SPAWN_POSITIONS = {
            new Position(9, 9), new Position(9, 8), new Position(9, 10), new Position(8, 9)
    };

    // Altre posi zion i es clus e
    private static final int[][] OTHER_EXCLUDED_POSITIONS = {
            { 9, 1 }, { 9, 2 }, { 9, 3 }, { 9, 15 }, { 9, 16 }, { 9, 17 },
            { 7, 1 }, { 7, 2 }, { 7, 16 }, { 7, 17 },
            { 11, 1 }, { 11, 2 }, { 11, 16 }, { 11, 17 }
    };

    public Grid() {
        super(COLUMNS, ROWS);
        this.smallDotMap = new HashMap<>();
        this.bigDotMap = new HashMap<>();
        initializeExcludedPositions();
        initializeMap();
    }

    public void setPacman(Pacman pacman) {
        addComponent(pacman);
    }

    public void setGhosts(List<Ghost> ghosts) {
        ghosts.forEach(this::addComponent);
    }

    public Set<Position> getWallPositions() {
        return getBidimensionalArray(WALL_POSITIONS);
    }

    public Set<Position> getMagicCoords() {
        return getBidimensionalArray(MAGIC_COORDS);
    }

    public List<Position> getGhostStartPositions() {
        return Arrays.asList(GHOST_SPAWN_POSITIONS);
    }

    public Position getPacmanStartPosition() {
        return PACMAN_START_POSITION;
    }

    private void initializeExcludedPositions() {
        excludedPositions = new HashSet<>();
        addPositions(WALL_POSITIONS);
        addPositions(BIG_DOT_POSITIONS);
        excludedPositions.add(PACMAN_START_POSITION);
        excludedPositions.addAll(Arrays.asList(GHOST_SPAWN_POSITIONS));
        addPositions(OTHER_EXCLUDED_POSITIONS);
        addPositions(MAGIC_COORDS);
    }

    private void initializeMap() {
        SmallDot smallDot;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position currentPosition = new Position(j, i);
                if (!excludedPositions.contains(currentPosition)) {
                    smallDot = new SmallDot(currentPosition);
                    addComponent(smallDot);
                    smallDotMap.put(currentPosition, smallDot);
                }
            }
        }

        addWallsToGrid();
        addBigDotsToGrid();
    }

    private void addBigDotsToGrid() {
        Arrays.stream(BIG_DOT_POSITIONS)
                .forEach(position -> {
                    BigDot bigDot = new BigDot(new Position(position[0], position[1]));
                    addComponent(bigDot);
                    bigDotMap.put(bigDot.getPosition(), bigDot); // Aggiungi BigDot alla mappa
                });
    }

    public SmallDot getSmallDotAtPosition(Position pos) {
        return smallDotMap.get(pos); // Ottieni SmallDot dalla mappa
    }

    public BigDot getBigDotAtPosition(Position pos) {
        return bigDotMap.get(pos); // Ottieni BigDot dalla mappa
    }

    public void removeDotFromMap(Position pos) {
        smallDotMap.remove(pos); // Rimuovi SmallDot
        bigDotMap.remove(pos); // Rimuovi BigDot
    }

    private void addWallsToGrid() {
        Arrays.stream(WALL_POSITIONS)
                .forEach(position -> addComponent(new Wall(new Position(position[0], position[1]))));
    }

    private Set<Position> getBidimensionalArray(int[][] positions) {
        Set<Position> set = new HashSet<>();
        for (int[] pos : positions) {
            set.add(new Position(pos[0], pos[1]));
        }
        return set;
    }

    private void addPositions(int[][] positions) {
        Arrays.stream(positions)
                .forEach(pos -> excludedPositions.add(new Position(pos[0], pos[1])));
    }
}