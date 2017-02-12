package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.cellStateFactories.UniformStateFactory;
import lab.model.coords.Coords2D;
import lab.model.neighbourhoods.MooreNeighbourhood;
import lab.model.rules.Rule2D;
import lab.model.states.BinaryState;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GameOfLifeTest {
	GameOfLife gameOfLife;
	Set<Cell> neighbours;
	CellStateFactory cellStateFactory = new UniformStateFactory(BinaryState.DEAD);

	public void init_NeighboursAndAutomaton() {
		neighbours = new HashSet<>();
		gameOfLife = new GameOfLife(new MooreNeighbourhood(true, 100, 100, 1), new Rule2D("2,3;4"), 100, 100,
							   cellStateFactory);
	}

	public void initCells_FiveNeighbours(BinaryState first, BinaryState second, BinaryState third, BinaryState forth,
								  BinaryState fifth) {
		neighbours.add(new Cell(new Coords2D(1, 1), first));
		neighbours.add(new Cell(new Coords2D(1, 3), second));
		neighbours.add(new Cell(new Coords2D(3, 5), third));
		neighbours.add(new Cell(new Coords2D(4, 5), forth));
		neighbours.add(new Cell(new Coords2D(53, 5), fifth));
	}

	@Test
	public void countAliveNeighbours_shouldReturn0() throws Exception {
		init_NeighboursAndAutomaton();
		initCells_FiveNeighbours(BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD,
							BinaryState.DEAD);
		assertEquals(gameOfLife.countAliveNeighbours(neighbours), 0);
	}

	@Test
	public void countAliveNeighbours_shouldReturn4() {
		init_NeighboursAndAutomaton();
		initCells_FiveNeighbours(BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.AlIVE,
							BinaryState.DEAD);
		assertEquals(gameOfLife.countAliveNeighbours(neighbours), 4);
	}

	@Test
	public void nextCellState_Dead_Dead() throws Exception {
		init_NeighboursAndAutomaton();
		BinaryState currentState = BinaryState.DEAD;
		Cell cell = new Cell(null, currentState);
		initCells_FiveNeighbours(BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.DEAD,
							BinaryState.DEAD);
		assertEquals(gameOfLife.nextCellState(cell, neighbours), BinaryState.DEAD);
	}

	@Test
	public void nextCellState_Alive_Alive() {
		init_NeighboursAndAutomaton();
		BinaryState currentState = BinaryState.AlIVE;
		Cell cell = new Cell(null, currentState);
		initCells_FiveNeighbours(BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.DEAD,
							BinaryState.DEAD);
		assertEquals(gameOfLife.nextCellState(cell, neighbours), BinaryState.AlIVE);
	}

	@Test
	public void nextCellState_Alive_Dead() {
		init_NeighboursAndAutomaton();
		BinaryState currentState = BinaryState.AlIVE;
		Cell cell = new Cell(null, currentState);
		initCells_FiveNeighbours(BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.AlIVE,
							BinaryState.DEAD);

		assertEquals(gameOfLife.nextCellState(cell, neighbours), BinaryState.DEAD);
	}

	@Test
	public void nextCellState_Dead_Alive() {
		init_NeighboursAndAutomaton();
		BinaryState currentState = BinaryState.DEAD;
		Cell cell = new Cell(null, currentState);
		initCells_FiveNeighbours(BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.AlIVE, BinaryState.AlIVE,
							BinaryState.DEAD);

		assertEquals(gameOfLife.nextCellState(cell, neighbours), BinaryState.AlIVE);
	}
}
