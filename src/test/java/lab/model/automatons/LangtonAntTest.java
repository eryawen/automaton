package lab.model.automatons;

import lab.model.Cell;
import lab.model.coords.Coords2D;
import lab.model.states.AntState;
import lab.model.states.BinaryState;
import lab.model.states.LangtonCellState;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class LangtonAntTest {
	LangtonAnt langtonAnt;
	Cell currentCell;
	LangtonCellState nextState;
	HashSet<Cell> neighbours;

	@Before
	public void init_NeighboursAndAutomaton() {
		neighbours = new HashSet<>();
		langtonAnt = new LangtonAnt(null, 100, 100, null);
	}

	@Test
	public void nextCellState_1x1WithoutAnt_AntFrom1x0() throws Exception {
		currentCell = new Cell(new Coords2D(1, 1), new LangtonCellState(BinaryState.AlIVE, new HashSet<>()));

		LangtonCellState.Ant antInNeighbourCell = new LangtonCellState.Ant(AntState.NORTH, 1);
		HashSet<LangtonCellState.Ant> antsInNeighbourCell = new HashSet<>();
		antsInNeighbourCell.add(antInNeighbourCell);
		neighbours.add(new Cell(new Coords2D(1, 0), new LangtonCellState(BinaryState.AlIVE, antsInNeighbourCell)));
		neighbours.add(new Cell(new Coords2D(1, 2), new LangtonCellState(null, new HashSet<>())));

		LangtonCellState.Ant ant = new LangtonCellState.Ant(AntState.EAST, 1);
		HashSet<LangtonCellState.Ant> antsInNextState = new HashSet<>();
		antsInNextState.add(ant);

		nextState = new LangtonCellState(BinaryState.AlIVE, antsInNextState);
		assertEquals(nextState, langtonAnt.nextCellState(currentCell, neighbours));
	}

	@Test
	public void nextCellState_1x1WithAnt_AntFrom1x0and2x1() throws Exception {
		HashSet<LangtonCellState.Ant> antsInCurrentCell = new HashSet<>();
		antsInCurrentCell.add(new LangtonCellState.Ant(null, 0));
		currentCell = new Cell(new Coords2D(1, 1), new LangtonCellState(BinaryState.DEAD, antsInCurrentCell));

		LangtonCellState.Ant antInNeighbourCell = new LangtonCellState.Ant(AntState.NORTH, 1);
		LangtonCellState.Ant antInNeighbourCell2 = new LangtonCellState.Ant(AntState.EAST, 2);
		HashSet<LangtonCellState.Ant> antsInNeighbourCell = new HashSet<>();
		HashSet<LangtonCellState.Ant> antsInNeighbourCell2 = new HashSet<>();
		antsInNeighbourCell.add(antInNeighbourCell);
		antsInNeighbourCell2.add(antInNeighbourCell2);
		neighbours.add(new Cell(new Coords2D(1, 0), new LangtonCellState(BinaryState.AlIVE, antsInNeighbourCell)));
		neighbours.add(new Cell(new Coords2D(2, 1), new LangtonCellState(BinaryState.DEAD, antsInNeighbourCell2)));

		LangtonCellState.Ant ant = new LangtonCellState.Ant(AntState.EAST, 1);
		LangtonCellState.Ant ant2 = new LangtonCellState.Ant(AntState.NORTH, 2);

		HashSet<LangtonCellState.Ant> antsInNextState = new HashSet<>();
		antsInNextState.add(ant);
		antsInNextState.add(ant2);

		nextState = new LangtonCellState(BinaryState.AlIVE, antsInNextState);

		assertEquals(nextState, langtonAnt.nextCellState(currentCell, neighbours));
	}

	@Test
	public void nextCellState_1x1WithoutAnt_AntOn0x0() throws Exception {
		currentCell = new Cell(new Coords2D(1, 1), new LangtonCellState(BinaryState.AlIVE, new HashSet<>()));

		LangtonCellState.Ant antInNeighbourCell = new LangtonCellState.Ant(AntState.EAST);
		HashSet<LangtonCellState.Ant> antsInNeighbourCell = new HashSet<>();
		antsInNeighbourCell.add(antInNeighbourCell);
		neighbours.add(new Cell(new Coords2D(1, 0), new LangtonCellState(BinaryState.DEAD, antsInNeighbourCell)));
		neighbours.add(new Cell(new Coords2D(0, 0), new LangtonCellState(null, new HashSet<>())));

		nextState = new LangtonCellState(BinaryState.AlIVE, new HashSet<>());
		assertEquals(nextState, langtonAnt.nextCellState(currentCell, neighbours));
	}

	@Test
	public void findNextBinaryState_DifferentCases() {
		nextState = new LangtonCellState(BinaryState.AlIVE, new HashSet<>());

		assertEquals(BinaryState.AlIVE,
				   langtonAnt.findNextBinaryState(new LangtonCellState(BinaryState.AlIVE, new HashSet<>())));
		assertEquals(BinaryState.DEAD,
				   langtonAnt.findNextBinaryState(new LangtonCellState(BinaryState.DEAD, new HashSet<>())));
		LangtonCellState.Ant antInNeighbourCell = new LangtonCellState.Ant(AntState.EAST);
		HashSet<LangtonCellState.Ant> antsInNeighbourCell = new HashSet<>();
		antsInNeighbourCell.add(antInNeighbourCell);
		assertEquals(BinaryState.AlIVE,
				   langtonAnt.findNextBinaryState(new LangtonCellState(BinaryState.DEAD, antsInNeighbourCell)));
	}
}

