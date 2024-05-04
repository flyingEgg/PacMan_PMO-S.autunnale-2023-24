package DesignPatterns.State;

import API.GameState;

public class OnGoing implements GameState {
    @Override
    public void enterState() {
        // Logica per entrare nello stato "In corso"
    }

    @Override
    public void exitState() {
        // Logica per uscire dallo stato "In corso"
    }

    @Override
    public void update() {
        // Logica di aggiornamento per lo stato "In corso"
    }
}
