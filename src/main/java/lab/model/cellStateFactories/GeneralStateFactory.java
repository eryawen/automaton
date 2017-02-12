package lab.model.cellStateFactories;

import lab.model.coords.CellCoordinates;
import lab.model.states.CellState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class GeneralStateFactory implements CellStateFactory {
	@Getter
	Map<CellCoordinates, CellState> states = new HashMap<>();
	
	public void add(CellCoordinates cellCoordinates, CellState cellState) {
		states.put(cellCoordinates, cellState);
	}
	
	@Override
	public CellState initialState(CellCoordinates cellCoordinates) {
		return states.get(cellCoordinates);
	}
}
