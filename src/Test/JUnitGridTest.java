package Test;

import API.MapComponent;
import Entities.Pacman;
import Game.Composite.BigDot;
import Game.Composite.SmallDot;
import Game.Composite.Wall;
import Game.PacmanGrid;
import Game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitGridTest {
    private PacmanGrid pacmanGrid;


    @BeforeEach
    public void setUp(){
        pacmanGrid = new PacmanGrid();
    }

    @Test
    public void testGridInitialization(){
        assertNotNull(pacmanGrid);
        assertEquals(21, pacmanGrid.getColumns());
        assertEquals(19, pacmanGrid.getRows());
    }

    @Test
    public void testPacmanPosition(){
        Position pacmanStartPosition = new Position(11, 9);
        Optional<MapComponent> component = pacmanGrid.getComponentByPosition(pacmanStartPosition);

        assertTrue(component.isPresent());
        assertTrue(component.get() instanceof Pacman, "Expected Pacman at position "+pacmanStartPosition);
    }

    @Test
    public void testWallPositions(){
        int[][] wallPositions = {
                {0, 0}, {1, 0}, {2, 0}, // Aggiungete tutte le posizioni di esempio
        };

        Arrays.stream(wallPositions).forEach(pos -> {
            Optional<MapComponent> component = pacmanGrid.getComponentByPosition(new Position(pos[0], pos[1]));

            assertTrue(component.isPresent());
            assertTrue(component.get() instanceof Wall, "Expected a Wall at position " + pos[0]+", "+pos[1]);
        });
    }

    @Test
    public void testBigDotPosition(){
        int[][] bigDotPositions = {
                {1, 1}, {1, 17}, {19, 1}, {19, 17}
        };

        Arrays.stream(bigDotPositions).forEach(pos -> {
            Optional<MapComponent> component = pacmanGrid.getComponentByPosition(new Position(pos[0], pos[1]));

            assertTrue(component.isPresent());
            assertTrue(component.get() instanceof BigDot, "Expected a BigDot at position " + pos[0]+", "+pos[1]);
        });
    }

    @Test
    public void testSmallDotPosition(){
        Position smallDotPosition = new Position(3, 17);
        Optional<MapComponent> component = pacmanGrid.getComponentByPosition(smallDotPosition);

        assertTrue(component.isPresent());
        assertTrue(component.get() instanceof SmallDot, "Expected a BigDot at position" + smallDotPosition);


    }

    @Test
    public void testPrintGrid(){
        pacmanGrid.printGrid();
    }

}
