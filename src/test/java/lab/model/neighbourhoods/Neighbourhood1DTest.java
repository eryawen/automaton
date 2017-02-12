package lab.model.neighbourhoods;

import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords1D;
import lab.model.exceptions.CellBeyondBoardException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class Neighbourhood1DTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void getCellNeighbours_coordsBeyondBoard_throwsException() throws Exception {
		thrown.expect(CellBeyondBoardException.class);
		Neighbourhood1D neighbourhood1D = new Neighbourhood1D(true, 10);

		neighbourhood1D.getCellNeighbours(new Coords1D(-1));
	}

	@Test
	public void getCellNeighbours_4_neighboursAre3and5() throws Exception {
		Neighbourhood1D neighbourhood1D = new Neighbourhood1D(true, 10);
		Set<CellCoordinates> neighbours = new HashSet<>();
		neighbours.add(new Coords1D(3));
		neighbours.add(new Coords1D(5));

		assertEquals(neighbourhood1D.getCellNeighbours(new Coords1D(4)), neighbours);
	}

	@Test
	public void getCellNeighbours_withoutWrapping_celWithOneNeighbour_AddSecondToSet() throws Exception {
		Neighbourhood1D neighbourhood1D = new Neighbourhood1D(false, 10);
		Set<CellCoordinates> neighbours = new HashSet<>();
		neighbours.add(new Coords1D(-1));
		neighbours.add(new Coords1D(1));

		assertEquals(neighbourhood1D.getCellNeighbours(new Coords1D(0)), neighbours);
	}

	@Test
	public void getCellNeighbours_withWrapping_cellOnTheEdge() throws Exception {
		Neighbourhood1D neighbourhood1D = new Neighbourhood1D(true, 10);
		Set<CellCoordinates> neighbours = new HashSet<>();
		neighbours.add(new Coords1D(9));
		neighbours.add(new Coords1D(1));

		assertEquals(neighbourhood1D.getCellNeighbours(new Coords1D(0)), neighbours);
	}
}