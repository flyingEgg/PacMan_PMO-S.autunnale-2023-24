package DesignPatterns.State;

import API.GameState;
import Main.Match;

public class GamePause implements GameState {
    private Match partita;

    public GamePause(Match p){
        this.partita = p;
    }

    @Override
    public void enterState() {
        this.partita.pauseUnpauseGame(true);
        this.partita.displayMessage();
    }

    @Override
    public void exitState() {

    }

    @Override
    public void update() {

    }

    public void resume() {

    }

    public void exit() {
        // Logica per uscire dal gioco
    }
}
