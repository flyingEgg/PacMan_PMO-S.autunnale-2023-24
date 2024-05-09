package DesignPatterns.Observer;

import API.GameState;
import API.GameSubjectListener;
import DesignPatterns.State.GameOver;
import Events.Event;
import Events.EventType;
import Main.Match;

public class GameStateObserver implements GameSubjectListener {
    private GameState currentGameState;

    @Override
    public void onGameEvent(Match m, Event event) {
        int typeID= event.getType().ordinal();
        switch (typeID){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                handleNoLives(m);
                break;
            default:
                throw new RuntimeException("Unknown event type received: "+event.getType());
        }
        /*if(type.equals(EventType.NO_LIVES_LEFT)){
            handleNoLives(m);
        }*/
    }

    private void handleNoLives(Match m){
        this.currentGameState = new GameOver(m);
        m.setGameOver(true);
    }
}
