package main.java.model.State;

import main.java.model.Model;
import main.java.model.API.GameState;

public class GamePause implements GameState {
    private final Model partita;

    public GamePause(Model p) {
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
        exitState();
        //this.partita.resetGame();
    }
}
