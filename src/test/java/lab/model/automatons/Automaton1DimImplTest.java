package lab.model.automatons;

import lab.model.Cell;
import lab.model.coords.Coords1D;
import lab.model.neighbourhoods.Neighbourhood1D;
import lab.model.rules.Rule1D;
import lab.model.states.BinaryState;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class Automaton1DimImplTest {

	Automaton1DimImpl automaton;
	Set<Cell> cells;

	public void initAutomaton_Rule51() {
		automaton = new Automaton1DimImpl(new Neighbourhood1D(false, 10), 10, new Rule1D("51"), null);
		cells = new HashSet();
	}

	public void initAutomaton_Rule102() {
		automaton = new Automaton1DimImpl(new Neighbourhood1D(false, 100), 100, new Rule1D("102"), null);
		cells = new HashSet();
	}

	@Test
	public void nextCellState_102_000_0() throws Exception {
		initAutomaton_Rule102();
		cells.add(new Cell(new Coords1D(2), BinaryState.DEAD));
		cells.add(new Cell(new Coords1D(4), BinaryState.DEAD));
		assertEquals(automaton.nextCellState(new Cell(new Coords1D(3), BinaryState.DEAD), cells), BinaryState.DEAD);
	}

	@Test
	public void nextCellState_102_111_0() throws Exception {
		initAutomaton_Rule102();
		cells.add(new Cell(new Coords1D(2), BinaryState.AlIVE));
		cells.add(new Cell(new Coords1D(4), BinaryState.AlIVE));
		assertEquals(automaton.nextCellState(new Cell(new Coords1D(3), BinaryState.AlIVE), cells), BinaryState.DEAD);
	}

	@Test
	public void nextCellState_102_101_1() throws Exception {
		initAutomaton_Rule102();
		cells.add(new Cell(new Coords1D(2), BinaryState.AlIVE));
		cells.add(new Cell(new Coords1D(4), BinaryState.AlIVE));
		assertEquals(automaton.nextCellState(new Cell(new Coords1D(3), BinaryState.DEAD), cells), BinaryState.AlIVE);
	}

	@Test
	public void nextCellState_51_010_0() throws Exception {
		initAutomaton_Rule51();
		cells.add(new Cell(new Coords1D(2), BinaryState.DEAD));
		cells.add(new Cell(new Coords1D(4), BinaryState.DEAD));
		assertEquals(automaton.nextCellState(new Cell(new Coords1D(3), BinaryState.AlIVE), cells), BinaryState.DEAD);
	}

	@Test
	public void nextCellState_51_011_0() throws Exception {
		initAutomaton_Rule51();

		cells.add(new Cell(new Coords1D(2), BinaryState.DEAD));
		cells.add(new Cell(new Coords1D(4), BinaryState.AlIVE));
		assertEquals(automaton.nextCellState(new Cell(new Coords1D(3), BinaryState.AlIVE), cells), BinaryState.DEAD);
	}

	@Test
	public void nextCellState_51_000_1() throws Exception {
		initAutomaton_Rule51();
		cells.add(new Cell(new Coords1D(2), BinaryState.DEAD));
		cells.add(new Cell(new Coords1D(4), BinaryState.DEAD));
		assertEquals(automaton.nextCellState(new Cell(new Coords1D(3), BinaryState.DEAD), cells), BinaryState.AlIVE);
	}
}