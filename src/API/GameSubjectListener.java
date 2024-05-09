package API;

import Events.Event;
import Main.Match;

public interface GameSubjectListener {
    void onGameEvent(Match m, Event event); // onGameEvent
}
