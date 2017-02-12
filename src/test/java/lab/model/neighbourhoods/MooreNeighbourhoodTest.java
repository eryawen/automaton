package lab.model.neighbourhoods;

import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords2D;
import lab.model.exceptions.IllegalRadiusValueException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class MooreNeighbourhoodTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void cellNeighbors_Board8x3_CellCoords1x1_rIs2_WrappingFalse() throws Exception {
		Coords2D cellCoords = new Coords2D(1, 1);
		MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(false, 8, 3, 2);

		Set<CellCoordinates> cellCoordinatesSet = new HashSet<>();
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 3; col++) {
				cellCoordinatesSet.add(new Coords2D(row, col));
			}

		cellCoordinatesSet.remove(cellCoords);

		assertTrue(cellCoordinatesSet.equals(mooreNeighbourhood.getCellNeighbours(cellCoords)));
	}

	@Test
	public void cellNeighbors_Board8x3_RadiusIs0_throwsException() throws Exception {
		thrown.expect(IllegalRadiusValueException.class);

		MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(false, 8, 3, 0);
	}

	@Test
	public void cellNeighbors_Board8x3_CellCoords1x1_rIs2_WrappingTrue() throws Exception {
		Coords2D cellCoords = new Coords2D(1, 1);
		MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(true, 8, 3, 2);

		Set<CellCoordinates> cellCoordinatesSet = new HashSet<>();
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 3; col++) {
				cellCoordinatesSet.add(new Coords2D(row, col));
			}
		for (int col = 0; col < 3; col++) {
			cellCoordinatesSet.add(new Coords2D(7, col));
		}

		cellCoordinatesSet.remove(cellCoords);

		assertTrue(cellCoordinatesSet.equals(mooreNeighbourhood.getCellNeighbours(cellCoords)));
	}
}
