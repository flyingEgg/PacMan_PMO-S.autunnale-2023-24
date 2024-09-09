package main.java.controller;

import main.java.model.Model;
import main.java.model.API.Direction;
import main.java.model.Entities.Ghost;
import main.java.view.View;

public class Controller {
    private final Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void startGame() {
        model.startStopGame(true);
        view.showGameWindow(); // Ensures the game window is shown when starting the game
        view.updateInfoPanel(); // Updates the info panel with initial game state
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }

    public void pauseGame() {
        model.pauseUnpauseGame(true);
        view.updateInfoPanel(); // Update the info panel to reflect the paused state
    }

    public void resumeGame() {
        model.pauseUnpauseGame(false);
        view.updateInfoPanel(); // Update the info panel to reflect the resumed state
    }

    public void stopGame() {
        model.setGameOver(true);
        view.updateInfoPanel(); // Update the info panel to show the game over status
    }

    public void movePacman(Direction direction) {
        if (model.isOnGoing() && !model.isPaused()) {
            model.movePacman(direction);
            checkForCollisions();
            view.updateInfoPanel(); // Update the info panel after Pacman moves
            view.getGamePanel().repaint(); // Repaint the game panel to reflect changes
        }
    }

    private void checkForCollisions() {
        // Check for collisions between Pacman and ghosts
        for (Ghost ghost : model.getGhosts()) {
            if (model.getPacman().getPosition().equals(ghost.getPosition())) {
                if (model.isSuperModeActive()) {
                    model.eatGhost(ghost.getPosition());
                } else {
                    model.loseLife();
                }
                view.updateInfoPanel(); // Update info panel if a ghost was eaten or a life was lost
                return; // End the check after handling a collision
            }
        }
    }

    public void activateSuperMode(int moves) {
        model.activateSuperMode(moves);
        view.updateInfoPanel(); // Update the info panel to reflect super mode status
    }

    public void resetGame() {
        //model.resetGame();
        view.showGameWindow(); // Show the game window after resetting the game
        view.updateInfoPanel(); // Update the info panel with the reset game state
    }

    public void setView(View view) {
        this.view = view;
    }
}
