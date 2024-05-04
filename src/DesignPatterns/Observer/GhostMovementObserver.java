package DesignPatterns.Observer;

import DesignPatterns.Events.Event;
import DesignPatterns.Strategy.Direction;

public class GhostMovementObserver {
    public void onGameEvent(Event event) {
        // Logica per gestire gli eventi relativi al movimento dei fantasmi
    }

    public void onGhostMoved(Direction direction) {
        // Logica per gestire il movimento di un fantasma in una nuova direzione
    }
}