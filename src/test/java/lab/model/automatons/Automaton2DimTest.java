package lab.model.automatons;

import lab.model.coords.Coords2D;
import lab.model.neighbourhoods.MooreNeighbourhood;
import org.junit.Test;

import static org.junit.Assert.*;

public class Automaton2DimTest {
	@Test
	public void translateCoords_xGreaterThanBoardDim() throws Exception {
		assertEquals(Automaton2Dim.translateCoords(100, 1, 100, 100), new Coords2D(0, 1));
	}

	@Test
	public void translateCoords_xLowerThanBoardDim() throws Exception {
		assertEquals(Automaton2Dim.translateCoords(-4, 1, 100, 100), new Coords2D(96, 1));
	}

	@Test
	public void translateCoords_yLowerThanBoardDim() throws Exception {
		assertEquals(Automaton2Dim.translateCoords(1, -4, 100, 100), new Coords2D(1, 96));
	}

	@Test
	public void translateCoords_yGreaterThanBoardDim() throws Exception {
		assertEquals(Automaton2Dim.translateCoords(1, 100, 100, 100), new Coords2D(1, 0));
	}

	@Test
	public void translateCoords_OnBoard() throws Exception {
		assertEquals(Automaton2Dim.translateCoords(1, 10, 50, 15), new Coords2D(1, 10));
	}

	@Test
	public void nextCoordinates_DifferentCases() throws Exception {
		Automaton2Dim automaton2Dim = new GameOfLife(new MooreNeighbourhood(true, 100, 50, 1), null, 100, 50, null);
		assertEquals(new Coords2D(99, 49), automaton2Dim.nextCoordinates(new Coords2D(99, 48)));
		assertEquals(new Coords2D(12, 6), automaton2Dim.nextCoordinates(new Coords2D(12, 5)));
		assertEquals(new Coords2D(13, 0), automaton2Dim.nextCoordinates(new Coords2D(12, 49)));
	}

	@Test
	public void hasNextCoordinates_ReturnTrue() throws Exception {
		Automaton2Dim automaton2Dim = new GameOfLife(new MooreNeighbourhood(true, 100, 50, 1), null, 100, 50, null);
		assertTrue(automaton2Dim.hasNextCoordinates(new Coords2D(99, 48)));
		assertTrue(automaton2Dim.hasNextCoordinates(new Coords2D(12, 5)));
	}

	@Test
	public void hasNextCoordinates_ReturnFalse() throws Exception {
		Automaton2Dim automaton2Dim = new GameOfLife(new MooreNeighbourhood(true, 100, 50, 1), null, 100, 50, null);
		assertFalse(automaton2Dim.hasNextCoordinates(new Coords2D(99, 49)));
	}
}