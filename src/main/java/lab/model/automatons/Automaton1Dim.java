package lab.model.automatons;

import lab.model.cellStateFactories.CellStateFactory;
import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords1D;
import lab.model.neighbourhoods.CellNeighbourhood;

/**
 * Base class which represents one-dimensional cellular automaton
 */
public abstract class Automaton1Dim extends Automaton {
	private int size;
	
	public Automaton1Dim(CellNeighbourhood neighborsStrategy, int size, CellStateFactory cellStateFactory) {
		super(neighborsStrategy, cellStateFactory);
		this.size = size;
	}
	
	public static Coords1D getWrappedCoords(int x, int automatonSize) {
		if (x < 0 || x >= automatonSize)
			x = Math.floorMod(x, automatonSize);
		return new Coords1D(x);
	}
	
	@Override
	protected boolean hasNextCoordinates(CellCoordinates cellCoordinates) {
		return (((Coords1D) cellCoordinates).getX() + 1 < getSize());
	}
	
	@Override
	protected CellCoordinates initialCoordinates() {
		return new Coords1D(-1);
	}
	
	@Override
	protected CellCoordinates nextCoordinates(CellCoordinates cellCoordinates) {
		Coords1D prevCoords = (Coords1D) cellCoordinates;
		return new Coords1D(prevCoords.getX() + 1);
	}
	
	public int getSize() {
		return size;
	}
}
