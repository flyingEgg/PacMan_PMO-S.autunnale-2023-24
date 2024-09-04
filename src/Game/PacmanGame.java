package Game;

import API.GameState;
import API.MapComponent;
import Game.GUI.MainMenu;

public class PacmanGame {
    private GameState state;
    private AbsGrid absGrid;
    private MapComponent component;

    public PacmanGame() {
        // Attributi della classe ???
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}