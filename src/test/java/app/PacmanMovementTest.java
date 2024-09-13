package test.java.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.Model;
import main.java.model.Movement.Direction;
import main.java.model.Movement.Position;
import main.java.model.Entities.Pacman;

public class PacmanMovementTest {
    private Model model;
    private Pacman pacman;

    @BeforeEach
    public void setUp() {
        model = new Model(); // Inizializza il modello
        pacman = model.getPacman(); // Recupera Pacman dal modello
    }

    @Test
    public void testMovePacman() {
        model.movePacman(Direction.RIGHT); // Muove Pacman verso destra
        assertEquals(new Position(1, 0), pacman.getPosition()); // Verifica la nuova posizione di Pacman

        model.movePacman(Direction.DOWN); // Muove Pacman verso il basso
        assertEquals(new Position(1, 1), pacman.getPosition()); // Verifica la nuova posizione di Pacman

        model.movePacman(Direction.LEFT); // Muove Pacman verso sinistra
        assertEquals(new Position(0, 1), pacman.getPosition()); // Verifica la nuova posizione di Pacman

        model.movePacman(Direction.UP); // Muove Pacman verso l'alto
        assertEquals(new Position(0, 0), pacman.getPosition()); // Verifica la nuova posizione di Pacman
    }

    @Test
    public void testBlockMovement() {
        model.movePacman(Direction.RIGHT); // Muove Pacman verso destra
        // Supponiamo che ci sia un muro a destra della posizione iniziale
        // Usa riflessione o modifica il modello per simulare la collisione
        // Questo è un segnaposto; implementa in base alla tua logica di collisione
        // effettiva
        assertThrows(IllegalStateException.class, () -> model.movePacman(Direction.RIGHT)); // Verifica la collisione
                                                                                            // con il muro
    }
}
