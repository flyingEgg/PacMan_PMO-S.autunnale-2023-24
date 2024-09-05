package main.java.model.State;

import main.java.model.Model;
import main.java.model.API.GameState;

public class GameOver implements GameState {
    private final Model partita;

    public GameOver(Model p) {
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
        this.partita.resetGame();
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
