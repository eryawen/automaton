package lab.model.neighbourhoods;

import lab.model.coords.Coords2D;
import lab.model.exceptions.IllegalBoard2DDimensionException;
import lab.model.exceptions.IllegalRadiusValueException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Neighbourhood2DTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createAutomaton_Radius0_ThrowsException() {
		thrown.expect(IllegalRadiusValueException.class);
		MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(true, 10, 10, 0);
	}

	@Test
	public void createAutomaton_RadiusGreaterThanBoard_ThrowsException() {
		thrown.expect(IllegalRadiusValueException.class);
		MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(true, 10, 5, 6);
	}

	@Test
	public void createAutomaton_BadBoardDimensions_ThrowsException() {
		thrown.expect(IllegalBoard2DDimensionException.class);
		MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(true, 10, 1, 6);
	}

	@Test
	public void belongToBoard_differentCases() {
		MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(true, 10, 5, 3);

		assertTrue(mooreNeighbourhood.belongToBoard(new Coords2D(2, 3)));
		assertTrue(mooreNeighbourhood.belongToBoard(new Coords2D(9, 4)));
		assertTrue(mooreNeighbourhood.belongToBoard(new Coords2D(0, 0)));
		assertFalse(mooreNeighbourhood.belongToBoard(new Coords2D(-1, 0)));
		assertFalse(mooreNeighbourhood.belongToBoard(new Coords2D(0, -1)));
		assertFalse(mooreNeighbourhood.belongToBoard(new Coords2D(10, 4)));
	}
}

