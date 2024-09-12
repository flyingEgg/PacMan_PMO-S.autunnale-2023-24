package main.java.model.Grid;

import java.util.Optional;

import main.java.model.API.MapComponent;
import main.java.model.Movement.Position;
import main.java.model.Util.Pair;

/**
 * Classe astratta che rappresenta una griglia di componenti di gioco.
 * 
 * La griglia è utilizzata per gestire la disposizione dei componenti nella
 * mappa di gioco,
 * inclusa l'aggiunta, la rimozione e il recupero dei componenti in base alla
 * loro posizione.
 */
public abstract class AbsGrid {
    private final MapComponent[][] grid;

    /**
     * Costruttore per la griglia.
     * 
     * @param columns Il numero di colonne nella griglia.
     * @param rows    Il numero di righe nella griglia.
     * @throws IllegalArgumentException Se le dimensioni della griglia sono non
     *                                  valide.
     */
    public AbsGrid(int columns, int rows) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Invalid grid dimensions");
        }
        this.grid = new MapComponent[rows][columns];
    }

    /**
     * Aggiunge un componente alla griglia alla posizione specificata.
     * 
     * @param component Il componente da aggiungere.
     */
    public void addComponent(MapComponent component) {
        Position position = component.getPosition();
        Pair<Integer, Integer> coordinates = new Pair<>(position.getX(), position.getY());

        if (isValidPosition(coordinates)) {
            grid[coordinates.getB()][coordinates.getA()] = component;
        } else {
            System.out.println("Invalid position: (" + coordinates + ")");
        }
    }

    /**
     * Rimuove un componente dalla griglia dalla posizione specificata.
     * 
     * @param component Il componente da rimuovere.
     */
    public void removeComponent(MapComponent component) {
        Position position = component.getPosition();
        Pair<Integer, Integer> coordinates = new Pair<>(position.getX(), position.getY());

        if (isValidPosition(coordinates)) {
            grid[coordinates.getB()][coordinates.getA()] = null;
        } else {
            System.out.println("Invalid position: (" + coordinates + ")");
        }
    }

    /**
     * Restituisce il componente alla posizione specificata.
     * 
     * @param position La posizione del componente.
     * @return Un Optional contenente il componente se presente, altrimenti vuoto.
     */
    public Optional<MapComponent> getComponentByPosition(Position position) {
        Pair<Integer, Integer> coordinates = new Pair<>(position.getX(), position.getY());

        if (isValidPosition(coordinates)) {
            return Optional.ofNullable(grid[coordinates.getB()][coordinates.getA()]);
        } else {
            System.out.println("Invalid position: (" + coordinates + ")");
            return Optional.empty();
        }
    }

    /**
     * Restituisce il numero di colonne della griglia.
     * 
     * @return Il numero di colonne.
     */
    public int getColumns() {
        return grid[0].length;
    }

    /**
     * Restituisce il numero di righe della griglia.
     * 
     * @return Il numero di righe.
     */
    public int getRows() {
        return grid.length;
    }

    /**
     * Verifica se una posizione è valida all'interno della griglia.
     * 
     * @param coord La posizione da verificare.
     * @return True se la posizione è valida, altrimenti false.
     */
    private boolean isValidPosition(Pair<Integer, Integer> coord) {
        return coord.getA() >= 0 && coord.getA() < grid[0].length &&
                coord.getB() >= 0 && coord.getB() < grid.length;
    }
}