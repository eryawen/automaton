package lab.controller.structures;

import lab.controller.cellOnBoard.CellOnBoard;
import lab.model.coords.Coords2D;

import java.util.HashMap;

public class OneDimensionalStructure implements Structure {
	
	private final GameOfLifeStructure choosenGameOfLifeStructure;
	
	public OneDimensionalStructure(GameOfLifeStructure choosenGameOfLifeStructure) {
		this.choosenGameOfLifeStructure = choosenGameOfLifeStructure;
	}
	
	@Override
	public HashMap<Coords2D, CellOnBoard> getStructure(int row, int col) {
		return choosenGameOfLifeStructure.getStructure(row, col);
	}
}
