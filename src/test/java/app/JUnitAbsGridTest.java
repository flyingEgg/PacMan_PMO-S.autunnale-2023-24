package test.java.app;

import main.java.API.MapComponent;
import main.java.model.Movement.Position;
import main.java.model.Composite.BigDot;
import main.java.model.Composite.SmallDot;
import main.java.model.Composite.Wall;
import main.java.model.Entities.Pacman;
import main.java.model.Grid.Grid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitAbsGridTest {
    private Grid grid;

    @BeforeEach
    public void setUp() {
        grid = new Grid();
    }

    @Test
    public void testGridInitialization() {
        assertNotNull(grid);
        assertEquals(21, grid.getColumns());
        assertEquals(19, grid.getRows());
    }

    @Test
    public void testPacmanPosition() {
        Position pacmanStartPosition = new Position(11, 9);
        Optional<MapComponent> component = grid.getComponentByPosition(pacmanStartPosition);

        assertTrue(component.isPresent());
        assertInstanceOf(Pacman.class, component.get(), "Expected Pacman at position " + pacmanStartPosition);
    }

    @Test
    public void testWallPositions() {
        int[][] wallPositions = {
                { 0, 0 }, { 1, 0 }, { 2, 0 }, // Aggiungete tutte le posizioni di esempio
        };

        Arrays.stream(wallPositions).forEach(pos -> {
            Optional<MapComponent> component = grid.getComponentByPosition(new Position(pos[0], pos[1]));

            assertTrue(component.isPresent());
            assertInstanceOf(Wall.class, component.get(), "Expected a Wall at position " + pos[0] + ", " + pos[1]);
        });
    }

    @Test
    public void testBigDotPosition() {
        int[][] bigDotPositions = {
                { 1, 1 }, { 1, 17 }, { 19, 1 }, { 19, 17 }
        };

        Arrays.stream(bigDotPositions).forEach(pos -> {
            Optional<MapComponent> component = grid.getComponentByPosition(new Position(pos[0], pos[1]));

            assertTrue(component.isPresent());
            assertInstanceOf(BigDot.class, component.get(), "Expected a BigDot at position " + pos[0] + ", " + pos[1]);
        });
    }

    @Test
    public void testSmallDotPosition() {
        Position smallDotPosition = new Position(3, 17);
        Optional<MapComponent> component = grid.getComponentByPosition(smallDotPosition);

        assertTrue(component.isPresent());
        assertInstanceOf(SmallDot.class, component.get(), "Expected a SmallDot at position" + smallDotPosition);

    }

    /*
     * @Test
     * public void testPrintGrid(){
     * pacmanGrid.printGrid();
     * }
     */

}
