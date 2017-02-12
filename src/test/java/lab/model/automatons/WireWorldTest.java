package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.cellStateFactories.UniformStateFactory;
import lab.model.coords.Coords2D;
import lab.model.neighbourhoods.VonNeumanNeighbourhood;
import lab.model.states.WireElectronState;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class WireWorldTest {
	Set<Cell> neighbours;
	CellStateFactory cellStateFactory = new UniformStateFactory(WireElectronState.ELECTRON_HEAD);

	@Before
	public void init() {
		neighbours = new HashSet<>();
	}

	public void addCell(int a, int b, WireElectronState wireElectronState) {
		neighbours.add(new Cell(new Coords2D(a, b), wireElectronState));
	}

	@Test
	public void haveOneOrTwoHeadNeighbours_ReturnTrue() throws Exception {
		WireWorld wireWorld = new WireWorld(new VonNeumanNeighbourhood(true, 100, 100, 1), 100, 100,
									 cellStateFactory);
		addCell(1, 2, WireElectronState.ELECTRON_TAIL);
		addCell(1, 4, WireElectronState.ELECTRON_TAIL);
		addCell(2, 3, WireElectronState.ELECTRON_HEAD);
		addCell(2, 3, WireElectronState.ELECTRON_HEAD);
		assertTrue(wireWorld.hasOneOrTwoHeadNeighbours(neighbours));
	}

	@Test
	public void haveOneOrTwoHeadNeighbours_ReturnFalse() throws Exception {
		WireWorld wireWorld = new WireWorld(new VonNeumanNeighbourhood(true, 100, 100, 1), 100, 100,
									 cellStateFactory);
		addCell(1, 2, WireElectronState.ELECTRON_TAIL);
		addCell(1, 4, WireElectronState.ELECTRON_TAIL);
		addCell(2, 3, WireElectronState.WIRE);
		addCell(2, 3, WireElectronState.WIRE);
		assertFalse(wireWorld.hasOneOrTwoHeadNeighbours(neighbours));
	}

	@Test
	public void nextCellState_WIRE_WIRE() {
		WireWorld wireWorld = new WireWorld(null, 0, 0, null);
		addCell(1, 2, WireElectronState.ELECTRON_HEAD);
		addCell(1, 4, WireElectronState.ELECTRON_HEAD);
		addCell(2, 3, WireElectronState.ELECTRON_HEAD);
		addCell(2, 3, WireElectronState.WIRE);
		assertEquals(wireWorld.nextCellState(new Cell(null, WireElectronState.WIRE), neighbours),
				   WireElectronState.WIRE);
	}

	@Test
	public void nextCellState_WIRE_HEAD() {
		WireWorld wireWorld = new WireWorld(null, 0, 0, null);
		addCell(1, 2, WireElectronState.ELECTRON_HEAD);
		addCell(1, 3, WireElectronState.ELECTRON_HEAD);
		addCell(4, 22, WireElectronState.ELECTRON_TAIL);
		addCell(5, 2, WireElectronState.WIRE);
		assertEquals(wireWorld.nextCellState(new Cell(null, WireElectronState.WIRE), neighbours),
				   WireElectronState.ELECTRON_HEAD);
	}
}