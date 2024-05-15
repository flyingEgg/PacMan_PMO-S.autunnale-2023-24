package DesignPatterns.State;

import API.GameState;
import API.GameSubject;
import API.GameSubjectListener;
import Main.Match;

public class GameOver implements GameState, GameSubject {
    private final Match partita;

    public GameOver(Match p) {
        this.partita = p;
    }

    @Override
    public void enterState() {
        this.partita.setGameOver(true);
        System.out.println("Hai perso");
        this.partita.displayMessage();
    }

    @Override
    public void exitState() {
        this.partita.setGameOver(false);
    }

    @Override
    public void update() {
        // Non è necessario alcun aggiornamento nello stato di "game over"

    }

    public void restart() {
        this.partita.setRestartMatch(true); // richiamo metodo per permettere il restart di un nuovo match
        System.out.println("Restart di un nuovo match");
        this.partita.displayMessage();
    }

    public void showScore() {
        // Aggiungi qui la logica per mostrare il punteggio
        // metodo per tornare il punteggio della partita
        System.out.println("Il punteggio della partita e di: " + this.partita.getScore());
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
