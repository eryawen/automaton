package lab.model.neighbourhoods;

import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords2D;
import lab.model.exceptions.IllegalRadiusValueException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class VonNeumanNeighborhoodTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void cellNeighbors_Board8x3_CellCoords1x1_rIs2_WrappingFalse() throws Exception {
		Coords2D cellCoords = new Coords2D(1, 1);
		VonNeumanNeighbourhood vonNeumanNeighborhood = new VonNeumanNeighbourhood(false, 8, 3, 2);
		
		Set<CellCoordinates> cellCoordinatesSet = new HashSet<>();
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				cellCoordinatesSet.add(new Coords2D(row, col));
			}
		cellCoordinatesSet.add(new Coords2D(3, 1));
		cellCoordinatesSet.remove(cellCoords);
		
		assertTrue(cellCoordinatesSet.equals(vonNeumanNeighborhood.getCellNeighbours(cellCoords)));
	}
	
	@Test
	public void cellNeighbors_Board3x8_rIs0_ThrowsException() throws Exception {
		thrown.expect(IllegalRadiusValueException.class);
		
		VonNeumanNeighbourhood vonNeumanNeighbourhood = new VonNeumanNeighbourhood(false, 3, 8, 0);
	}
	
	@Test
	public void cellNeighbors_Board8x3_CellCoords1x1_rIs2_WrappingTrue() throws Exception {
		Coords2D cellCoords = new Coords2D(1, 1);
		VonNeumanNeighbourhood vonNeumanNeighborhood = new VonNeumanNeighbourhood(true, 8, 3, 2);
		
		Set<CellCoordinates> cellCoordinatesSet = new HashSet<>();
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				cellCoordinatesSet.add(new Coords2D(row, col));
			}
		cellCoordinatesSet.add(new Coords2D(7, 1));
		cellCoordinatesSet.add(new Coords2D(3, 1));
		
		cellCoordinatesSet.remove(cellCoords);
		
		assertTrue(cellCoordinatesSet.equals(vonNeumanNeighborhood.getCellNeighbours(cellCoords)));
	}
}