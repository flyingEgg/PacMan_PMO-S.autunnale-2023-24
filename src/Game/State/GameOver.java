package Game.State;

import API.GameState;
import API.GameSubject;
import API.GameSubjectListener;
import Game.Game;

public class GameOver implements GameState {
    private final Game partita;

    public GameOver(Game p) {
        this.partita = p;
    }

    @Override
    public void enterState() {
        this.partita.setGameOver(true);
        this.partita.displayMessage();
    }

    @Override
    public void exitState() {
        this.partita.setGameOver(false);
        this.partita.resetScore();
    }

    @Override
    public void update() {

    }

    public void showScore() {
        // Aggiungi qui la logica per mostrare il punteggio
        // metodo per tornare il punteggio della partita
        System.out.println("Il punteggio della partita e di: " + this.partita.getScore());
    }
}
