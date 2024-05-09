package Main;

import API.GameState;
import API.MapComponent;
import API.MovementStrategy;
import DesignPatterns.Composite.Grid;
import DesignPatterns.Composite.PacmanGrid;

public class PacmanGame {
    private GameState state;
    private Grid grid;
    private MovementStrategy pacman;
    private MovementStrategy ghostRed;
    private MovementStrategy ghostOrange;
    private MovementStrategy ghostPink;
    private MovementStrategy ghostBlue;
    private MapComponent component;

    public PacmanGame() {
        // Inizializza gli attributi della classe Game
    }

    // Metodi per la gestione del gioco

    public static void main(String[] args) {
        // Creare un'istanza del gioco
        PacmanGame game = new PacmanGame();

        // Avviare il gioco
        game.start();
    }

    private void start() {
        PacmanGrid grid = new PacmanGrid();
        grid.printGrid(); // test per vedere con simboli se viene stampata effettivamente la grid
    }
}