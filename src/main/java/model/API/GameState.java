package main.java.model.API;

public interface GameState {
    /**
     * Fa entrare la partita in un determinato stato
     */
    void enterState();

    /**
     * Fa uscire la partita da un determinato stato
     */
    void exitState();

    /**
     * Effettua aggiornamenti sullo stato
     */
    void update();
}
