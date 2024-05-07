package DesignPatterns.State;

import API.GameState;
import Main.Match;

public class GamePause implements GameState {
    private final Match partita;

    public GamePause(Match p) {
        this.partita = p;
    }

    @Override
    public void enterState() {
        this.partita.pauseUnpauseGame(true); // Metti in pausa la partita
        this.partita.displayMessage();
    }

    @Override
    public void exitState() {
        this.partita.pauseUnpauseGame(false); // Riprendi la partita all'uscita dallo stato di pausa
    }

    @Override
    public void update() {
        // Aggiungi qui la logica di aggiornamento per lo stato di pausa
    }

    public void quit() {
        // Aggiungi qui la logica per uscire dalla partita
    }
}
