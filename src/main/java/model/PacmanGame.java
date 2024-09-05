package main.java.model;

import main.java.model.API.GameState;
import main.java.model.API.MapComponent;
import main.java.view.MainMenu;

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