package Test;

import static org.junit.jupiter.api.Assertions.*;
import Game.Game;
import Game.State.GameOnGoing;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class JUnitStateTest {
    private Game game;

    @BeforeEach
    public void setUp(){
        game = new Game(null, null);
    }

    @Test
    void testGameOnGoingState(){
        GameOnGoing onGoing = new GameOnGoing(game);

        assertFalse(game.isOnGoing(), "La partita non dovrebbe ancora essere in corso");

        // riprendi qui
    }

}
