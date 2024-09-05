package test.java.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import main.java.model.Model;
import main.java.model.State.GameOnGoing;
import main.java.model.State.GameOver;
import main.java.model.State.GamePause;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitStateTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testGameOnGoingState() {
        GameOnGoing onGoing = new GameOnGoing(model);

        // Mi assicuro che l1o stato iniziale della partita non sia in corso
        assertFalse(model.isOnGoing(), "La partita non dovrebbe ancora essere in corso");

        // Entro nello stato in corso
        onGoing.enterState();
        assertTrue(model.isOnGoing(), "Il gioco dovrebbe esser nello stato 'onGoing'");
        assertFalse(model.isPaused(), "Il gioco non dovrebbe esser nello stato 'paused' mentre si trova in 'onGoing'");

        onGoing.exitState();
        assertFalse(model.isPaused(), "Il gioco non dovrebbe esser nello stato 'paused'");
    }

    @Test
    public void testGameOverState() {
        GameOver gameOver = new GameOver(model);

        assertFalse(model.isGameOver(), "Il gioco non dovrebbe inizialmente esser in game over");

        gameOver.enterState();
        assertTrue(model.isGameOver(), "Il gioco dovrebbe esser terminato");
        assertFalse(model.isOnGoing(), "Il gioco non dovrebbe esser in stato 'onGoing' una volta terminato");
        assertFalse(model.isPaused(), "Il gioco non dovrebbe esser in stato 'paused' una volta terminato");

        gameOver.exitState();
        assertFalse(model.isGameOver(), "Il gioco non dovrebbe esser in game over");
    }

    @Test
    public void testGamePausedState() {
        GamePause gamePause = new GamePause(model);

        assertFalse(model.isPaused(), "Il gioco non dovrebbe inizialmente esser in pausa");

        gamePause.enterState();
        assertTrue(model.isPaused(), "Il gioco dovrebbe esser in pausa");
        assertFalse(model.isOnGoing(), "Il gioco non dovrebbe esser in stato 'onGoing' mentre è in pausa");

        gamePause.exitState();
        assertFalse(model.isPaused(), "Il gioco non dovrebbe esser in pausa");
        assertTrue(model.isOnGoing(), "Il gioco dovrebbe esser in corso dopo esser uscito dalla pausa");
    }
}
