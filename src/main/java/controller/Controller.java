package main.java.controller;

import main.java.model.Model;
import main.java.view.View;
import main.java.model.API.Direction;
import main.java.model.Exceptions.IllegalEntityMovementException;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        setupListeners();
    }

    private void setupListeners() {
        // Setup action listeners or event handlers
    }

    public void movePacman(Direction direction) {
        try {
            model.movePacman(direction);
            view.updateInfoPanel();
            if (model.isGameOver()) {
                // Handle game over
            }
        } catch (IllegalEntityMovementException e) {
            // Handle movement exception
        }
    }

    public void resetGame() {
        model.resetGame();
        view.showGameWindow(model);
    }

    public void startGame() {
        view.showGameWindow(model);
    }

    public void showMainMenu() {
        view.showMainMenu();
    }

    public Model getModel() {
        return model;
    }

    public void setView(View view) {
        this.view = view;
    }
}
