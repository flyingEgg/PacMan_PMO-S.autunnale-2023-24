package DesignPatterns.State;

import API.GameState;

public class Pause implements GameState {
    @Override
    public void enterState() {
        // Logica per entrare nello stato "In pausa"
    }

    @Override
    public void exitState() {
        // Logica per uscire dallo stato "In pausa"
    }

    @Override
    public void update() {
        // Logica di aggiornamento per lo stato "In pausa"
    }

    public void resume() {
        // Logica per riprendere il gioco dalla pausa
    }

    public void exit() {
        // Logica per uscire dal gioco
    }
}
