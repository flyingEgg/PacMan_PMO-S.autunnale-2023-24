package Game.State;

import API.GameState;
import API.GameSubject;
import API.GameSubjectListener;
import Game.Match;

public class GamePause implements GameState, GameSubject {
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
       exitState();
       this.partita.resetScore();
    }

    @Override
    public void attach(GameSubjectListener observer) {

    }

    @Override
    public void detach(GameSubjectListener observer) {

    }

    @Override
    public void notifyObservers() {

    }
}
