package test.java.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.Model;
import main.java.model.Movement.Direction;
import main.java.model.Movement.Position;
import main.java.model.Composite.BigDot;
import main.java.model.Entities.Ghost;
import main.java.model.Entities.Pacman;

public class SupermodeTest {
    private Model model;
    private Pacman pacman;
    private Ghost ghost;

    @BeforeEach
    public void setUp() {
        model = new Model(); // Inizializza il modello
        pacman = model.getPacman(); // Recupera Pacman dal modello
        ghost = model.getGhost(0); // Recupera il primo fantasma dal modello
    }

    @Test
    public void testGhostVulnerability() {
        // Posiziona Pacman sulla BigDot
        pacman.setPosition(new Position(18, 17));
        model.movePacman(Direction.RIGHT); // Muove Pacman verso il BigDot

        assertTrue(ghost.isScared()); // Verifica se il fantasma è spaventato
    }
}
