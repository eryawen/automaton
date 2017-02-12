package lab.controller.cellOnBoard;

import javafx.scene.paint.Color;
import lab.controller.controllers.BaseGameController;
import lab.controller.enums.Colour;
import lab.model.states.CellState;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode

public abstract class CellOnBoard {
	@Getter
	final int px;
	@Getter
	final int py;
	@Getter
	CellState cellState;
	Colour colour;
	
	public CellOnBoard(int row, int col, CellState cellState) {
		this.px = col * BaseGameController.getCellSize();
		this.py = row * BaseGameController.getCellSize();
		updateCellState(cellState);
	}
	
	public CellOnBoard(int row, int col) {
		this.px = col * BaseGameController.getCellSize();
		this.py = row * BaseGameController.getCellSize();
		updateCellState(getDefaultState());
	}
	
	public Color getColour() {
		return colour.getFXColor();
	}
	
	public abstract CellState getDefaultState();
	
	public abstract void updateCellState(CellState cellState);
}