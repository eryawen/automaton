package lab.controller.cellOnBoard;

import lab.controller.enums.Colour;
import lab.model.states.CellState;
import lab.model.states.WireElectronState;

public class WireWorldCell extends CellOnBoard {
	public WireWorldCell(int row, int col, CellState cellState) {
		super(row, col, cellState);
	}

	public WireWorldCell(int row, int col) {
		super(row, col);
	}

	@Override
	public CellState getDefaultState() {
		return WireElectronState.VOID;
	}

	@Override
	public void updateCellState(CellState cellState) {
		this.cellState = cellState;
		switch ((WireElectronState) cellState) {
			case ELECTRON_HEAD:
				colour = Colour.BLUE;
				break;
			case ELECTRON_TAIL:
				colour = Colour.RED;
				break;
			case WIRE:
				colour = Colour.YELLOW;
				break;
			case VOID:
				colour = Colour.BLACK;
				break;
		}
	}
}
