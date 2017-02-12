package lab.model.neighbourhoods;

import lab.model.automatons.Automaton1Dim;
import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords1D;
import lab.model.exceptions.CellBeyondBoardException;
import lab.model.exceptions.IllegalBoard1DDimensionException;

import java.util.HashSet;
import java.util.Set;

public class Neighbourhood1D extends CellNeighbourhood {
	private int boardLength;

	public Neighbourhood1D(boolean withWrapping, int boardLength) {
		super(withWrapping);

		if (boardLength < 1) {
			throw new IllegalBoard1DDimensionException();
		}
		this.boardLength = boardLength;
	}

	@Override
	public Set<CellCoordinates> getCellNeighbours(CellCoordinates cellCoordinates) {
		int coordsOfCurrentCell = ((Coords1D) cellCoordinates).getX();

		if (coordsOfCurrentCell < 0 || coordsOfCurrentCell >= boardLength) {
			throw new CellBeyondBoardException();
		}

		Set<CellCoordinates> neighbours = new HashSet<>();

		if (withWrapping) {
			neighbours.add(Automaton1Dim.getWrappedCoords(coordsOfCurrentCell - 1, boardLength));
			neighbours.add(Automaton1Dim.getWrappedCoords(coordsOfCurrentCell + 1, boardLength));
		} else {
			neighbours.add(new Coords1D(coordsOfCurrentCell - 1));
			neighbours.add(new Coords1D(coordsOfCurrentCell + 1));
		}
		return neighbours;
	}
}
