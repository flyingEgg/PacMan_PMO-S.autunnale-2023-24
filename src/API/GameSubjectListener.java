package API;

import Event.Event;
import Game.Match;

public interface GameSubjectListener {
    void onGameEvent(Match m, Event event); // onGameEvent
}
