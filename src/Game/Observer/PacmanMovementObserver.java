package Game.Observer;

import Game.Composite.Dot;
import Event.Event;
import Game.Strategies.Direction;

public class PacmanMovementObserver {
    public void onGameEvent(Event event) {
        // Logica per gestire gli eventi relativi al movimento di Pacman
    }

    public void onPacmanMoved(Direction direction) {
        // Logica per gestire il movimento di Pacman in una nuova direzione
    }

    public void onDotEaten(Dot dot) {
        // Logica per gestire quando Pacman mangia un puntino
    }

    public void onGhostCollision() {
        // Logica per gestire la collisione di Pacman con un fantasma
    }
}
