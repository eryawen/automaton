package lab.model.neighbourhoods;

import lab.model.coords.CellCoordinates;
import lombok.AllArgsConstructor;

import java.util.Set;

/**
 * Abstract class which represents strategy of determining neighbours
 */

@AllArgsConstructor
public abstract class CellNeighbourhood {
	boolean withWrapping;
	
	/**
	 * @param cellCoordinates represents coordinates of cell which neighbours are to be calculated
	 * @return neighbours of {@code cellCoordinates}
	 */
	public abstract Set<CellCoordinates> getCellNeighbours(CellCoordinates cellCoordinates);
}
