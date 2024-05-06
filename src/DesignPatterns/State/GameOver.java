package DesignPatterns.State;

import API.GameState;
import Main.Match;

public class GameOver implements GameState {
    private Match partita;

    public GameOver(Match p){
        this.partita = p;
    }

    @Override
    public void enterState() {
        // Logica per entrare nello stato "Game Over"
    }

    @Override
    public void exitState() {

    }

    @Override
    public void update() {

    }

    public void restart() {

    }

    public void showScore() {

    }
}
