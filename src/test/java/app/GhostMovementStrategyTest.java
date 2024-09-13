package test.java.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.Model;
import main.java.model.Entities.Ghost;
import main.java.model.Strategies.GhostChaseStrategy;
import main.java.model.Strategies.GhostScatterStrategy;
import main.java.model.Strategies.GhostFleeStrategy;

public class GhostMovementStrategyTest {
    private Model model;
    private Ghost ghost;

    @BeforeEach
    public void setUp() {
        model = new Model(); // Inizializza il modello
        ghost = model.getGhost(0); // Recupera il primo fantasma dal modello
    }

    @Test
    public void testGhostMovementStrategy() {
        ghost.setMovementStrategy(new GhostChaseStrategy(ghost, model.getGrid(), model, null, true));
        model.moveGhosts(); // Muove i fantasmi in base alla strategia corrente
        assertTrue(ghost.getMovementStrategy() instanceof GhostChaseStrategy); // Verifica che il fantasma stia usando
                                                                               // la strategia Chase

        ghost.setMovementStrategy(new GhostScatterStrategy(ghost, model.getGrid(), model, null, true));
        model.moveGhosts(); // Muove i fantasmi in base alla nuova strategia
        assertTrue(ghost.getMovementStrategy() instanceof GhostScatterStrategy); // Verifica che il fantasma stia usando
                                                                                 // la strategia Scatter
    }

    @Test
    public void testGhostReturnToInitialPosition() {
        ghost.setMovementStrategy(new GhostFleeStrategy(ghost, model.getGrid(), model, null, true));
        model.moveGhosts(); // Muove i fantasmi
        // Simula che Pacman mangi il fantasma
        model.eatGhost(ghost.getPosition());
        assertEquals(ghost.getInitialPosition(), ghost.getPosition()); // Verifica che il fantasma ritorni alla
                                                                       // posizione iniziale
    }
}
