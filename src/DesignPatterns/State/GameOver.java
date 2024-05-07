package DesignPatterns.State;

import API.GameState;
import Main.Match;

public class GameOver implements GameState {
    private Match partita;

    public GameOver(Match p) {
        this.partita = p;
    }

    @Override
    public void enterState() {
        this.partita.gameOver(); // Imposta lo stato di gioco su "game over"
        System.out.println("Hai perso");
        this.partita.displayMessage();
    }

    @Override
    public void exitState() {
        // Non è necessario alcun azione specifica all'uscita dallo stato di "game over"
    }

    @Override
    public void update() {
        // Non è necessario alcun aggiornamento nello stato di "game over"
    }

    public void restart() {
        // Aggiungi qui la logica per riavviare la partita
    }

    public void showScore() {
        // Aggiungi qui la logica per mostrare il punteggio
    }
}
