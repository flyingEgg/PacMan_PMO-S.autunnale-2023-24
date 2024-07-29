package Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import Game.Game;
import Game.State.GameOnGoing;
import Game.State.GameOver;
import Game.State.GamePause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

public class JUnitStateTest {
    private Game game;

    @BeforeEach
    public void setUp(){
        game = new Game(null, null);
    }

    @Test
    public void testGameOnGoingState(){
        GameOnGoing onGoing = new GameOnGoing(game);

        // Mi assicuro che l1o stato iniziale della partita non sia in corso
        assertFalse(game.isOnGoing(), "La partita non dovrebbe ancora essere in corso");

        // Entro nello stato in corso
        onGoing.enterState();
        assertTrue(game.isOnGoing(), "Il gioco dovrebbe esser nello stato 'onGoing'");
        assertFalse(game.isPaused(), "Il gioco non dovrebbe esser nello stato 'paused' mentre si trova in 'onGoing'");

        onGoing.exitState();
        assertFalse(game.isPaused(), "Il gioco non dovrebbe esser nello stato 'paused'");
    }

    @Test
    public void testGameOverState(){
        GameOver gameOver = new GameOver(game);

        assertFalse(game.isGameOver(), "Il gioco non dovrebbe inizialmente esser in game over");

        gameOver.enterState();
        assertTrue(game.isGameOver(), "Il gioco dovrebbe esser terminato");
        assertFalse(game.isOnGoing(), "Il gioco non dovrebbe esser in stato 'onGoing' una volta terminato");
        assertFalse(game.isPaused(), "Il gioco non dovrebbe esser in stato 'paused' una volta terminato");

        gameOver.exitState();
        assertFalse(game.isGameOver(), "Il gioco non dovrebbe esser in game over");
    }

    @Test
    public void testGamePausedState(){
        GamePause gamePause = new GamePause(game);

        assertFalse(game.isPaused(), "Il gioco non dovrebbe inizialmente esser in pausa");

        gamePause.enterState();
        assertTrue(game.isPaused(), "Il gioco dovrebbe esser in pausa");
        assertFalse(game.isOnGoing(), "Il gioco non dovrebbe esser in stato 'onGoing' mentre è in pausa");

        gamePause.exitState();
        assertFalse(game.isPaused(), "Il gioco non dovrebbe esser in pausa");
        assertTrue(game.isOnGoing(), "Il gioco dovrebbe esser in corso dopo esser uscito dalla pausa");
    }
}
