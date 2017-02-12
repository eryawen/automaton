package lab.model.neighbourhoods;

import lab.model.coords.Coords2D;
import lab.model.exceptions.IllegalBoard2DDimensionException;
import lab.model.exceptions.IllegalRadiusValueException;

/**
 * Base class for two-dimensional neighborhood
 */
public abstract class Neighbourhood2D extends CellNeighbourhood {
	protected int width;
	protected int height;
	protected int r;

	public Neighbourhood2D(boolean withWrapping, int height, int width, int r) {
		super(withWrapping);
		if (width < 3 || height < 3) {
			throw new IllegalBoard2DDimensionException();
		}
		this.width = width;
		this.height = height;
		this.r = r;

		if (r < 1) {
			throw new IllegalRadiusValueException("Radius should be greater than 0");
		}
		if (r > 6) {
			throw new IllegalRadiusValueException("Radius should be lower than 7");
		}
		if (r >= Math.min(width, height)) {
			throw new IllegalRadiusValueException("Radius is too big for this board dimensions");
		}
	}

	protected boolean belongToBoard(Coords2D cell) {
		return (cell.getX() >= 0) && (cell.getY() >= 0) && (cell.getX() < height) && (cell.getY() < width);
	}
}
