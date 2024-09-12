package main.java.model.State;

import main.java.model.Model;
import main.java.model.API.GameState;

/**
 * La classe GamePause gestisce lo stato di pausa del gioco.
 */
public class GamePause implements GameState {
    private final Model partita;

    /**
     * Costruttore per inizializzare lo stato di pausa.
     * 
     * @param p Il modello di gioco associato allo stato.
     */
    public GamePause(Model p) {
        this.partita = p;
    }

    /**
     * Entra nello stato di pausa, mettendo il gioco in pausa e mostrando un
     * messaggio.
     */
    @Override
    public void enterState() {
        this.partita.pauseUnpauseGame(true); // Metti in pausa la partita
        this.partita.displayMessage();
    }

    /**
     * Esce dallo stato di pausa, riprendendo il gioco.
     */
    @Override
    public void exitState() {
        this.partita.pauseUnpauseGame(false); // Riprendi la partita
    }

    /**
     * Aggiorna lo stato di pausa. Questo metodo può essere lasciato vuoto se non è
     * necessaria alcuna logica di aggiornamento.
     */
    @Override
    public void update() {
        // Aggiungi qui la logica di aggiornamento per lo stato di pausa, se necessaria
    }

    /**
     * Esce dallo stato di pausa e, facoltativamente, può gestire il reset del
     * gioco.
     */
    public void quit() {
        exitState();
        // Se è previsto il reset del gioco quando si esce dallo stato di pausa,
        // decommentare la riga sottostante
        // this.partita.resetGame();
    }
}