package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.cellStateFactories.UniformStateFactory;
import lab.model.coords.Coords2D;
import lab.model.neighbourhoods.MooreNeighbourhood;
import lab.model.states.BinaryState;
import lab.model.states.QuadState;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class QuadLifeTest {
	QuadLife quadLife;
	Set<Cell> neighbours;
	CellStateFactory cellStateFactory = new UniformStateFactory(BinaryState.DEAD);

	public void init_NeighboursAndAutomaton() {
		neighbours = new HashSet<>();
		quadLife = new QuadLife(new MooreNeighbourhood(true, 10, 10, 1), 10, 10, cellStateFactory);
	}

	public void addCell(int a, int b, QuadState quadState) {
		neighbours.add(new Cell(new Coords2D(a, b), quadState));
	}

	public void initCells_ThreeNeighbours(QuadState first, QuadState second, QuadState third) {
		neighbours = new HashSet<>();
		quadLife = new QuadLife(new MooreNeighbourhood(false, 10, 10, 1), 10, 10, cellStateFactory);
		neighbours.add(new Cell(new Coords2D(1, 1), first));
		neighbours.add(new Cell(new Coords2D(1, 3), second));
		neighbours.add(new Cell(new Coords2D(5, 5), third));
	}

	@Test
	public void nextCellState_Dead_Alive() {
		QuadState currentState = QuadState.DEAD;
		initCells_ThreeNeighbours(QuadState.BLUE, QuadState.GREEN, QuadState.YELLOW);
		Cell cell = new Cell(new Coords2D(1, 2), currentState);
		assertEquals(quadLife.nextCellState(cell, neighbours), QuadState.RED);
	}

	@Test
	public void nextCellState_Alive_Alive() {
		QuadState currentState = QuadState.YELLOW;
		Cell cell = new Cell(new Coords2D(1, 2), currentState);
		initCells_ThreeNeighbours(QuadState.BLUE, QuadState.GREEN, QuadState.YELLOW);
		assertEquals(quadLife.nextCellState(cell, neighbours), QuadState.YELLOW);
	}

	@Test
	public void nextCellState_Dead_Dead() {
		QuadState currentState = QuadState.DEAD;
		Cell cell = new Cell(new Coords2D(1, 2), currentState);
		init_NeighboursAndAutomaton();
		addCell(1, 5, QuadState.RED);
		addCell(2, 3, QuadState.BLUE);
		assertEquals(quadLife.nextCellState(cell, neighbours), QuadState.DEAD);
	}

	@Test
	public void nextCellState_Alive_Dead() {
		QuadState currentState = QuadState.RED;
		Cell cell = new Cell(new Coords2D(1, 2), currentState);
		init_NeighboursAndAutomaton();
		addCell(1, 2, QuadState.RED);
		addCell(2, 3, QuadState.BLUE);
		addCell(3, 2, QuadState.RED);
		addCell(7, 3, QuadState.BLUE);
		assertEquals(quadLife.nextCellState(cell, neighbours), QuadState.DEAD);
	}

	@Test
	public void findNextQuadState_AllColoursAreDifferent() throws Exception {
		initCells_ThreeNeighbours(QuadState.BLUE, QuadState.GREEN, QuadState.YELLOW);
		assertEquals(quadLife.findNextQuadState(neighbours), QuadState.RED);
	}

	@Test
	public void findNextQuadState_AllNeigboursAreBlue() {
		initCells_ThreeNeighbours(QuadState.BLUE, QuadState.BLUE, QuadState.BLUE);
		assertEquals(quadLife.findNextQuadState(neighbours), QuadState.BLUE);
	}

	@Test
	public void findNextQuadState_TwoNeighboursAreGreenAndOneIsYellow() {
		initCells_ThreeNeighbours(QuadState.GREEN, QuadState.GREEN, QuadState.YELLOW);
		assertEquals(quadLife.findNextQuadState(neighbours), QuadState.GREEN);
	}

	@Test
	public void countAliveNeighbours_shouldReturn0() throws Exception {
		init_NeighboursAndAutomaton();
		initCells_ThreeNeighbours(QuadState.DEAD, QuadState.DEAD, QuadState.DEAD);
		assertEquals(quadLife.countAliveNeighbours(neighbours), 0);
	}

	@Test
	public void countAliveNeighbours_shouldReturn2() {
		init_NeighboursAndAutomaton();
		initCells_ThreeNeighbours(QuadState.YELLOW, QuadState.RED, QuadState.DEAD);
		assertEquals(quadLife.countAliveNeighbours(neighbours), 2);
	}
}
