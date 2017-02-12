package lab.controller.structures;

import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.LangtonAntCell;
import lab.model.coords.Coords2D;
import lab.model.states.AntState;
import lab.model.states.BinaryState;
import lab.model.states.LangtonCellState;

import java.util.HashMap;
import java.util.HashSet;

public enum LangtonAntStructure implements Structure {
	NORTH(AntState.NORTH), SOUTH(AntState.SOUTH), EAST(AntState.EAST), WEST(AntState.WEST);
	
	private AntState antState;
	
	LangtonAntStructure(AntState antState) {
		
		this.antState = antState;
	}
	
	@Override
	public HashMap<Coords2D, CellOnBoard> getStructure(int row, int col) {
		HashSet<LangtonCellState.Ant> ants = new HashSet<>();
		ants.add(new LangtonCellState.Ant(antState));
		HashMap<Coords2D, CellOnBoard> structure = new HashMap();
		structure.put(new Coords2D(row, col),
				    new LangtonAntCell(row, col, new LangtonCellState(BinaryState.DEAD, ants)));
		
		return structure;
	}
}
