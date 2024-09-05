package main.java.model.State;

import main.java.model.Model;
import main.java.model.API.GameState;

public class GameOnGoing implements GameState {
    private final Model partita;

    public GameOnGoing(Model p) {
        this.partita = p;
    }

    @Override
    public void enterState() {
        if (!this.partita.isOnGoing() || this.partita.isPaused()) {
            this.partita.startStopGame(true); // Avvia la partita se non è in corso o è in pausa
            this.partita.pauseUnpauseGame(false); // Riprende la partita se è in pausa
        }
        this.partita.displayMessage();
        System.out.println("Punteggio: " + partita.getScore());
    }

    @Override
    public void exitState() {
        // Interrompi la partita all'uscita dallo stato di "game on going"
        this.partita.startStopGame(false);
    }

    @Override
    public void update() {
        if (!partita.isOnGoing() || partita.isGameOver()) {
            exitState();
        }
    }
}
