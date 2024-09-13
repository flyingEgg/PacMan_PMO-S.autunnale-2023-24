package test.java.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.Model;
import main.java.model.Entities.Ghost;
import main.java.model.Strategies.GhostChaseStrategy;
import main.java.model.Strategies.GhostScatterStrategy;
import main.java.model.Strategies.GhostFleeStrategy;
import main.java.model.Movement.Position;

public class GhostMovementStrategyTest {
    private Model model;
    private Ghost ghost;

    @BeforeEach
    public void setUp() {
        model = new Model(); // Inizializza il modello
        ghost = model.getGhost(0); // Recupera il primo fantasma dal modello
        model.getGrid().getGhostStartPositions().get(0); // Salva la posizione iniziale del fantasma
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
}
