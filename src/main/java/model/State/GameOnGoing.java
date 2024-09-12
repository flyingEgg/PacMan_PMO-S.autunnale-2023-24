package main.java.model.State;

import main.java.model.Model;
import main.java.model.API.GameState;

/**
 * La classe GameOnGoing gestisce lo stato di gioco in corso.
 */
public class GameOnGoing implements GameState {
    private final Model partita;

    /**
     * Costruttore per inizializzare lo stato di gioco in corso.
     * 
     * @param p Il modello di gioco associato allo stato.
     */
    public GameOnGoing(Model p) {
        this.partita = p;
    }

    /**
     * Entra nello stato di gioco in corso, avviando il gioco se necessario
     * e visualizzando il punteggio corrente.
     */
    @Override
    public void enterState() {
        if (!this.partita.isOnGoing() || this.partita.isPaused()) {
            this.partita.startStopGame(true); // Avvia il gioco se non è in corso
            this.partita.pauseUnpauseGame(false); // Riprende il gioco se è in pausa
        }
        this.partita.displayMessage();
        System.out.println("Punteggio: " + partita.getScore());
    }

    /**
     * Esce dallo stato di gioco in corso, fermando il gioco.
     */
    @Override
    public void exitState() {
        this.partita.startStopGame(false); // Ferma il gioco all'uscita dallo stato
    }

    /**
     * Aggiorna lo stato di gioco, uscendo dallo stato se il gioco non è più in
     * corso
     * o se è terminato.
     */
    @Override
    public void update() {
        if (!partita.isOnGoing() || partita.isGameOver()) {
            exitState();
        }
    }
}