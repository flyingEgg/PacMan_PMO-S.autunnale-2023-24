package main.java.model.API;

import main.java.model.Game;
import main.java.model.Event.Event;

public interface GameSubjectListener {
    void onGameEvent(Game m, Event event); // onGameEvent
}
