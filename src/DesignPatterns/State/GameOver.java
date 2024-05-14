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
        // Aggiungi qui la logica per riavviare la partita
    }

    public void showScore() {
        // Aggiungi qui la logica per mostrare il punteggio
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
