package main.java.API;

/**
 * Interfaccia per ascoltare le modifiche delle statistiche del gioco.
 * I metodi di questa interfaccia devono essere implementati per ricevere
 * notifiche riguardo ai cambiamenti nel punteggio, nelle vite e nello stato
 * della Supermode.
 */
public interface GameStatisticsListener {

    /**
     * Viene chiamato quando il punteggio cambia.
     * 
     * @param newScore Il nuovo punteggio del gioco.
     */
    void onScoreChanged(int newScore);

    /**
     * Viene chiamato quando il numero di vite cambia.
     * 
     * @param newLives Il nuovo numero di vite del giocatore.
     */
    void onLivesChanged(int newLives);

    /**
     * Viene chiamato quando lo stato della Supermode cambia.
     * 
     * @param movesRemaining Il numero di mosse rimanenti nella Supermode.
     */
    void onSuperModeStatusChanged(int movesRemaining);
}