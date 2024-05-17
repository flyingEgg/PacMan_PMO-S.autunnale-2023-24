package Game.Observer;

import API.GameState;
import API.GameSubjectListener;
import Game.State.GameOver;
import Event.Event;
import Event.EventType;
import Game.Game;

public class GameStateObserver implements GameSubjectListener {
    private GameState currentGameState;

    @Override
    public void onGameEvent(Game m, Event event) {
        EventType type = event.getType();
        switch (type) {
            case NO_LIVES_LEFT:
                handleNoLives(m);
                break;
            default:
                throw new RuntimeException("Unknown event type received: " + type);
        }
    }

    private void handleNoLives(Game m) {
        this.currentGameState = new GameOver(m);
        m.setGameOver(true);
    }
}
