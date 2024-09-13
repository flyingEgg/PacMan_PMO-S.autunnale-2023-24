package test.java.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.Model;
import main.java.model.Movement.Direction;
import main.java.model.Movement.Position;
import main.java.model.Composite.BigDot;
import main.java.model.Composite.SmallDot;
import main.java.model.Entities.Pacman;

public class DotInteractionTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new Model(); // Inizializza il modello
    }

    @Test
    public void testCollectSmallDot() {
        model.movePacman(Direction.DOWN); // Muove Pacman verso il dot
        assertEquals(10, model.getScore()); // Verifica il punteggio; assume 10 punti per il SmallDot
    }

    @Test
    public void testActivateSupermode() {
        model.getPacman().setPosition(new Position(18, 17));
        model.movePacman(Direction.RIGHT); // Muove Pacman verso il big dot
        assertTrue(model.isSuperModeActive()); // Verifica se il Supermode è attivo
    }
}
