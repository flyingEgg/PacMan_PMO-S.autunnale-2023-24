package DesignPatterns.State;

import API.GameState;

public class GameOver implements GameState {
    @Override
    public void enterState() {
        // Logica per entrare nello stato "Game Over"
    }

    @Override
    public void exitState() {
        // Logica per uscire dallo stato "Game Over"
    }

    @Override
    public void update() {
        // Logica di aggiornamento per lo stato "Game Over"
    }

    public void restart() {
        // Logica per ricominciare il gioco
    }

    public void showScore() {
        // Logica per mostrare il punteggio
    }
}
