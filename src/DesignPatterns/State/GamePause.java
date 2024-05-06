package DesignPatterns.State;

import API.GameState;
import Main.Match;

public class GamePause implements GameState {
    private final Match partita;

    public GamePause(Match p){
        this.partita = p;
    }

    @Override
    public void enterState() {
        this.partita.pauseUnpauseGame(true);
        this.partita.startStopGame(false);
        this.partita.displayMessage();
    }

    @Override
    public void exitState() {
        this.partita.pauseUnpauseGame(false);
    }

    @Override
    public void update() {

    }

    public void quit(){
        // logica che resetterà i dati di partita
    }

}
