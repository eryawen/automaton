package lab.model.automatons;

import lab.model.coords.Coords1D;
import org.junit.Test;

import static org.junit.Assert.*;

public class Automaton1DimTest {
	@Test
	public void hasNextCoordinates_LastCoords_False() throws Exception {
		Automaton1DimImpl automaton1Dim = new Automaton1DimImpl(null, 100, null, null);
		assertFalse(automaton1Dim.hasNextCoordinates(new Coords1D(99)));
	}

	@Test
	public void hasNextCoordinates_True() throws Exception {
		Automaton1DimImpl automaton1Dim = new Automaton1DimImpl(null, 100, null, null);
		assertTrue(automaton1Dim.hasNextCoordinates(new Coords1D(10)));
		assertTrue(automaton1Dim.hasNextCoordinates(new Coords1D(-1)));
		assertTrue(automaton1Dim.hasNextCoordinates(new Coords1D(0)));
	}

	@Test
	public void nextCoordinates() throws Exception {
		Automaton1DimImpl automaton1Dim = new Automaton1DimImpl(null, 100, null, null);
		assertEquals(new Coords1D(99), automaton1Dim.nextCoordinates(new Coords1D(98)));
		assertEquals(new Coords1D(0), automaton1Dim.nextCoordinates(new Coords1D(-1)));
		assertEquals(new Coords1D(1), automaton1Dim.nextCoordinates(new Coords1D(0)));
	}
}
