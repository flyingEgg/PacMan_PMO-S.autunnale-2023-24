package API;

import Event.Event;
import Game.Game;

public interface GameSubjectListener {
    void onGameEvent(Game m, Event event); // onGameEvent
}
