package lab.model.cellStateFactories;

import lab.model.coords.CellCoordinates;
import lab.model.states.CellState;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniformStateFactory implements CellStateFactory {
	CellState state;

	@Override
	public CellState initialState(CellCoordinates cellCoordinates) {
		return state;
	}
}
