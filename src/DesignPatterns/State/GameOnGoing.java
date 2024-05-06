package DesignPatterns.State;


import API.GameState;

public class GameOnGoing implements GameState {

    @Override
    public void enterState() {
        System.out.println("Partita avviata");

    }

    @Override
    public void exitState() {

    }

    @Override
    public void update() {
        // Logica di aggiornamento per lo stato "In corso"
    }
}
