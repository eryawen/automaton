package lab.model.neighbourhoods;

import lab.model.automatons.Automaton2Dim;
import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords2D;

import java.util.HashSet;
import java.util.Set;

public class MooreNeighbourhood extends Neighbourhood2D {

	public MooreNeighbourhood(boolean withWrapping, int height, int width, int r) {
		super(withWrapping, height, width, r);
	}

	@Override
	public Set<CellCoordinates> getCellNeighbours(CellCoordinates cellCoordinates) {
		Coords2D coordsOfCurrentCell = (Coords2D) cellCoordinates;
		Set<CellCoordinates> neighbours = new HashSet<>();
		Coords2D coords;

		int startRow = coordsOfCurrentCell.getX() - r;
		int leftColumn = coordsOfCurrentCell.getY() - r;
		int rightColumn = coordsOfCurrentCell.getY() + r;
		int lastRow = coordsOfCurrentCell.getX() + r;

		for (int i = startRow; i <= lastRow; i++) {
			for (int j = leftColumn; j <= rightColumn; j++) {
				coords = new Coords2D(i, j);
				if (withWrapping) {
					neighbours.add(Automaton2Dim.translateCoords(i, j, height, width));
				} else if (belongToBoard(coords)) {
					neighbours.add(coords);
				}
			}
		}

		neighbours.remove(coordsOfCurrentCell);
		return neighbours;
	}
}
