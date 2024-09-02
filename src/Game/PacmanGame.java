package Game;

import API.GameState;
import API.MapComponent;
import Game.GUI.MainMenu;

public class PacmanGame {
    private GameState state;
    private Grid grid;
    private MapComponent component;

    public PacmanGame() {
        // Attributi della classe ???
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}