package DesignPatterns.State;


import API.GameState;
import Main.Match;

public class GameOnGoing implements GameState {
    private final Match partita;

    public GameOnGoing(Match p){
        this.partita = p;
    }

    @Override
    public void enterState() {
        if (!this.partita.isOnGoing() || this.partita.isPaused()){
            this.partita.startStopGame(true);
            this.partita.pauseUnpauseGame(false);
        }
        System.out.println("Partita avviata");

        this.partita.displayMessage();
    }

    @Override
    public void exitState() {
        this.partita.startStopGame(false);
        this.partita.displayMessage();
        System.out.println("Partita terminata");
    }

    @Override
    public void update() {
        // Logica di aggiornamento per lo stato "In corso"
    }
}
