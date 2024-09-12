package main.java.model.API;

/**
 * Interfaccia che definisce i metodi per gestire lo stato di gioco.
 * Ogni stato di gioco deve implementare questi metodi per gestire
 * l'ingresso, l'uscita e l'aggiornamento dello stato.
 */
public interface GameState {

    /**
     * Fa entrare la partita nello stato corrente.
     * Questo metodo deve essere chiamato quando il gioco entra
     * in questo stato.
     */
    void enterState();

    /**
     * Fa uscire la partita dallo stato corrente.
     * Questo metodo deve essere chiamato quando il gioco esce
     * da questo stato.
     */
    void exitState();

    /**
     * Effettua aggiornamenti sullo stato corrente.
     * Questo metodo deve essere chiamato periodicamente per aggiornare
     * lo stato corrente del gioco.
     */
    void update();
}