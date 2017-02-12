package lab.model;

import lab.model.coords.CellCoordinates;
import lab.model.states.CellState;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Class which represents single cell in automaton
 */

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public final class Cell {
	public final CellCoordinates coords;
	@Setter
	private CellState state;
}
