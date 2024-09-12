package main.java.model.API;

import main.java.model.Model;
import main.java.model.Event.Event;

public interface GameSubjectListener {
    void onGameEvent(Model m, Event event); // onGameEvent
}
