package lab.model.states;

import lab.model.coords.Coords2D;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AntTest {

	@Test
	public void getNextAntDirection_Black_North_East() throws Exception {
		LangtonCellState langtonCellState = new LangtonCellState(BinaryState.AlIVE, null);
		LangtonCellState.Ant ant = new LangtonCellState.Ant(AntState.NORTH);
		assertEquals(ant.getNextAntDirection(langtonCellState.getColour()), AntState.EAST);
	}

	@Test
	public void getNextAntDirection_White_West_South() throws Exception {
		LangtonCellState langtonCellState = new LangtonCellState(BinaryState.DEAD, null);
		LangtonCellState.Ant ant = new LangtonCellState.Ant(AntState.WEST);
		assertEquals(ant.getNextAntDirection(langtonCellState.getColour()), AntState.SOUTH);
	}

	@Test
	public void getNextAntCoords_2x3_White_East_1x3() throws Exception {

		LangtonCellState.Ant ant = new LangtonCellState.Ant(AntState.EAST);
		LangtonCellState langtonCellState = new LangtonCellState(BinaryState.DEAD, null);
		assertEquals(ant.getNextAntCoords(new Coords2D(2, 3), langtonCellState.getColour()), new Coords2D(1, 3));
	}

	@Test
	public void getNextAntCoords_5x0_Black_South_5xNegativeCoords() throws Exception {
		LangtonCellState.Ant ant = new LangtonCellState.Ant(AntState.SOUTH);
		LangtonCellState langtonCellState = new LangtonCellState(BinaryState.AlIVE, null);
		assertEquals(ant.getNextAntCoords(new Coords2D(5, 0), langtonCellState.getColour()), new Coords2D(5, -1));
	}
}
