import API.GameState;
import API.MapComponent;
import API.MovementStrategy;
import DesignPatterns.Composite.Grid;

public class Game {
    private GameState state;
    private Grid grid;
    private MovementStrategy pacman;
    private MovementStrategy ghostRed;
    private MovementStrategy ghostOrange;
    private MovementStrategy ghostPink;
    private MovementStrategy ghostBlue;
    private MapComponent component;

    public Game() {
        // Inizializza gli attributi della classe Game
    }

    // Metodi per la gestione del gioco

    public static void main(String[] args) {
        // Creare un'istanza del gioco
        Game game = new Game();

        // Avviare il gioco
        game.start();
    }

    private void start() {
        // Logica per avviare il gioco
    }
}