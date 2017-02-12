package lab.controller.cellOnBoard;

import lab.controller.enums.Colour;
import lab.model.states.CellState;
import lab.model.states.QuadState;

public class QuadLifeCell extends CellOnBoard {
	public QuadLifeCell(int row, int col, CellState cellState) {
		super(row, col, cellState);
	}
	
	public QuadLifeCell(int row, int col) {
		super(row, col);
	}
	
	@Override
	public CellState getDefaultState() {
		return QuadState.DEAD;
	}
	
	@Override
	public void updateCellState(CellState cellState) {
		this.cellState = cellState;
		switch ((QuadState) cellState) {
			case BLUE:
				colour = Colour.BLUE;
				break;
			case GREEN:
				colour = Colour.GREEN;
				break;
			case RED:
				colour = Colour.RED;
				break;
			case YELLOW:
				colour = Colour.YELLOW;
				break;
			case DEAD:
				colour = Colour.WHITE;
		}
	}
}
