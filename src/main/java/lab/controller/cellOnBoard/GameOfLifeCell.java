package lab.controller.cellOnBoard;

import lab.controller.enums.Colour;
import lab.model.states.BinaryState;
import lab.model.states.CellState;

public class GameOfLifeCell extends CellOnBoard {
	public GameOfLifeCell(int row, int col, CellState cellState) {
		super(row, col, cellState);
	}
	
	public GameOfLifeCell(int row, int col) {
		super(row, col);
	}
	
	@Override
	public CellState getDefaultState() {
		return BinaryState.DEAD;
	}
	
	@Override
	public void updateCellState(CellState cellState) {
		this.cellState = cellState;
		colour = (cellState == BinaryState.AlIVE) ? Colour.BLACK : Colour.WHITE;
	}
}
