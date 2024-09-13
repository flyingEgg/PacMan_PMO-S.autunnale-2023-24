package test.java.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.Model;

public class ModelTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testLoseAllLivesEndsGame() {
        // Initially, Pacman has 3 lives
        assertEquals(3, model.getLives(), "Pacman dovrebbe iniziare con tutte e 3 le vite");

        // Simulate losing one life
        model.loseLife();
        assertEquals(2, model.getLives(), "Pacman dovrebbe avere 2 vite");

        // Simulate losing second life
        model.loseLife();
        assertEquals(1, model.getLives(), "Pacman dovrebbe avere una vita");

        // Simulate losing third life (last life)
        model.loseLife();
        assertEquals(0, model.getLives(), "Pacman dovrebbe avere 0 vite");

        // At this point, the game should be over
        assertTrue(model.isGameOver(), "Si dovrebbe essere in Game Over se Pacman ha 0 vite");
    }
}
