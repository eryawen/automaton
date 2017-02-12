package lab.model.cellStateFactories;

import lab.model.coords.CellCoordinates;
import lab.model.states.CellState;

/**
 * Interface which represents factory used to get initial state of {@code Automaton}
 */
public interface CellStateFactory {
	/**
	 * @param cellCoordinates coordinates of cell whose initial state is to be returned
	 * @return initial state of cell with {@code cellCoordinates}
	 */
	CellState initialState(CellCoordinates cellCoordinates);
}
