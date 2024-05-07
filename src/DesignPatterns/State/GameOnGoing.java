package DesignPatterns.State;

import API.GameState;
import Main.Match;

public class GameOnGoing implements GameState {
    private final Match partita;

    public GameOnGoing(Match p) {
        this.partita = p;
    }

    @Override
    public void enterState() {
        if (!this.partita.isOnGoing() || this.partita.isPaused()) {
            this.partita.startStopGame(true); // Avvia la partita se non è in corso o è in pausa
            this.partita.pauseUnpauseGame(false); // Riprende la partita se è in pausa
        }
        this.partita.displayMessage();
    }

    @Override
    public void exitState() {
        // Interrompi la partita all'uscita dallo stato di "game on going"
        this.partita.startStopGame(false);
    }

    @Override
    public void update() {
        // Aggiungi qui la logica di aggiornamento per lo stato di "game on going"
    }
}
