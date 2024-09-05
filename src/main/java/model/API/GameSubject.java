package main.java.model.API;

public interface GameSubject {
    void attach(GameSubjectListener observer);

    void detach(GameSubjectListener observer);

    void notifyObservers();
}
