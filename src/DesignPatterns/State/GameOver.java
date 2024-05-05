package DesignPatterns.State;

import API.GameState;

public class GameOver implements GameState {
    @Override
    public void enterState() {
        // Logica per entrare nello stato "Game Over"
    }

    @Override
    public void exitState() {

    }

    @Override
    public void update() {

    }

    public void restart() {

    }

    public void showScore() {

    }
}
