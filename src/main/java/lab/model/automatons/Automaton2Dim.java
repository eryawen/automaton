package lab.model.automatons;

import lab.model.cellStateFactories.CellStateFactory;
import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords2D;
import lab.model.neighbourhoods.CellNeighbourhood;
import lombok.Getter;

/**
 * Base class which represents two-dimensional cellular automaton
 */
@Getter
public abstract class Automaton2Dim extends Automaton {
	private int height;
	private int width;
	
	public Automaton2Dim(CellNeighbourhood neighborsStrategy, int height, int width,
					 CellStateFactory cellStateFactory) {
		super(neighborsStrategy, cellStateFactory);
		this.height = height;
		this.width = width;
	}
	
	public static Coords2D translateCoords(int x, int y, int height, int width) {
		if (x < 0 || x >= height)
			x = Math.floorMod(x, height);
		if (y < 0 || y >= width)
			y = Math.floorMod(y, width);
		
		return new Coords2D(x, y);
	}
	
	@Override
	protected CellCoordinates initialCoordinates() {
		return new Coords2D(-1, width - 1);
	}
	
	@Override
	protected CellCoordinates nextCoordinates(CellCoordinates cellCoordinates) {
		Coords2D prevCoords = (Coords2D) cellCoordinates;
		int y = prevCoords.getY() + 1;
		int x = prevCoords.getX();
		if (y == width) {
			y = 0;
			x++;
		}
		return new Coords2D(x, y);
	}
	
	@Override
	protected boolean hasNextCoordinates(CellCoordinates cellCoordinates) {
		Coords2D prevCoords = (Coords2D) cellCoordinates;
		Coords2D lastCoords = new Coords2D(height - 1, width - 1);
		return !prevCoords.equals(lastCoords);
	}
}
