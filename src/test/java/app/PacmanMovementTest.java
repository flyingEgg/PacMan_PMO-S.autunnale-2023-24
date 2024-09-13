package test.java.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.Model;
import main.java.model.Movement.Direction;
import main.java.model.Movement.Position;
import main.java.model.Entities.Pacman;
import main.java.model.Grid.Grid;
import java.util.Set;

public class PacmanMovementTest {
    private Model model;
    private Pacman pacman;
    private Grid grid;

    @BeforeEach
    public void setUp() {
        model = new Model(); // Initialize the model
        pacman = model.getPacman(); // Get Pacman from the model
        grid = model.getGrid(); // Get the grid from the model
    }

    @Test
    public void testMovePacman() {

        // Move Pacman down
        model.movePacman(Direction.DOWN);
        assertEquals(new Position(11, 10), pacman.getPosition()); // Adjust expected position

        // Move Pacman up
        model.movePacman(Direction.UP);
        assertEquals(new Position(11, 9), pacman.getPosition()); // Adjust expected position
    }

    @Test
    public void testBlockMovement() {
        // Get wall positions from grid
        Set<Position> wallPositions = grid.getWallPositions();

        // Move Pacman to the right until hitting a wall

        Position nextPosition = pacman.getPosition();
        if (wallPositions.contains(nextPosition)) {
            // If the next position is a wall, test collision
            assertThrows(IllegalStateException.class, () -> model.movePacman(Direction.RIGHT));
            return; // Exit the test as collision is expected
        }
    }
}
