package DesignPatterns.State;


import API.GameState;

public class GameOnGoing implements GameState {


    @Override
    public void enterState() {

    }

    @Override
    public void exitState() {
        // Logica per uscire dallo stato "In corso"
    }

    @Override
    public void update() {
        // Logica di aggiornamento per lo stato "In corso"
    }
}
