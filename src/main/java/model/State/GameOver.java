package main.java.model.State;

import main.java.model.Model;
import main.java.model.API.GameState;

/**
 * La classe GameOver gestisce lo stato di fine gioco.
 */
public class GameOver implements GameState {
    private final Model partita;

    /**
     * Costruttore per inizializzare lo stato di fine gioco.
     * 
     * @param p Il modello di gioco associato allo stato.
     */
    public GameOver(Model p) {
        this.partita = p;
    }

    /**
     * Entra nello stato di fine gioco, impostando il gioco come terminato
     * e visualizzando il punteggio finale.
     */
    @Override
    public void enterState() {
        this.partita.setGameOver(true);
        this.partita.displayMessage();
        showScore(); // Mostra il punteggio al termine del gioco
    }

    /**
     * Esce dallo stato di fine gioco, resettando lo stato del gioco se necessario.
     */
    @Override
    public void exitState() {
        this.partita.setGameOver(false);
        // Se è previsto il reset del gioco all'uscita, decommentare la riga sottostante
        // this.partita.resetGame();
    }

    /**
     * Aggiorna lo stato di fine gioco. In questo stato, l'aggiornamento potrebbe
     * non essere necessario.
     */
    @Override
    public void update() {
        // In questo stato, l'aggiornamento potrebbe non essere necessario
    }

    /**
     * Mostra il punteggio finale della partita.
     */
    public void showScore() {
        System.out.println("Il punteggio della partita è di: " + this.partita.getScore());
    }
}