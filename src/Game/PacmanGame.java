package Game;

import API.GameState;
import API.MapComponent;
import Game.Composite.PacmanGrid;

public class PacmanGame {
    private GameState state;
    private Grid grid;
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