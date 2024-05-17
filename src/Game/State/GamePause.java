package Game.State;

import API.GameState;
import API.GameSubject;
import API.GameSubjectListener;
import Game.Game;

public class GamePause implements GameState {
    private final Game partita;

    public GamePause(Game p) {
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
       this.partita.resetScore();
    }
}
