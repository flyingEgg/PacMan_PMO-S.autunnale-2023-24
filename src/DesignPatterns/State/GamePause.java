package DesignPatterns.State;

import API.GameState;

public class GamePause implements GameState {
    @Override
    public void enterState() {
        // Logica per entrare nello stato "In pausa"
    }

    @Override
    public void exitState() {

    }

    @Override
    public void update() {

    }

    public void resume() {

    }

    public void exit() {
        // Logica per uscire dal gioco
    }
}
