package lab.controller.cellOnBoard;

import lab.controller.enums.Colour;
import lab.model.states.AntState;
import lab.model.states.BinaryState;
import lab.model.states.CellState;
import lab.model.states.LangtonCellState;
import lombok.Getter;

import java.util.HashSet;

public class LangtonAntCell extends CellOnBoard {
	BinaryState binaryState;
	@Getter
	private AntState antDirection;
	
	public LangtonAntCell(int row, int col, CellState cellState) {
		super(row, col, cellState);
	}
	
	public LangtonAntCell(int row, int col) {
		super(row, col);
	}
	
	@Override
	public CellState getDefaultState() {
		return new LangtonCellState(BinaryState.DEAD, new HashSet<>());
	}
	
	@Override
	public void updateCellState(CellState cellState) {
		this.cellState = cellState;
		HashSet<LangtonCellState.Ant> ants = ((LangtonCellState) cellState).getAnts();
		if (!ants.isEmpty()) {
			antDirection = ants.iterator().next().getAntState();
		} else {
			antDirection = AntState.NONE;
		}
		this.binaryState = ((LangtonCellState) cellState).getBinaryState();
		colour = (binaryState == BinaryState.AlIVE) ? Colour.BLACK : Colour.WHITE;
	}
	
	public double[] getAntPxPy(int cellSize) {
		int[] coords;
		switch (antDirection) {
			case NORTH:
				coords = new int[]{
					   px, py + cellSize,
					   px + cellSize, py + cellSize,
					   px + (cellSize / 2), py
				};
				break;
			case SOUTH:
				coords = new int[]{px, py,
					   px + cellSize, py,
					   px + (cellSize / 2), py + cellSize
				};
				break;
			case WEST:
				coords = new int[]{px + cellSize, py,
					   px + cellSize, py + cellSize,
					   px, py + (cellSize / 2)
				};
				break;
			case EAST:
				coords = new int[]{px, py,
					   px, py + cellSize,
					   px + cellSize, py + (cellSize / 2)
				};
				break;
			default:
				coords = new int[]{
					   0, 0, 0, 0, 0, 0, 0};
		}
		double[] triangleCoords = new double[6];
		for (int i = 0; i < 6; i++) {
			triangleCoords[i] = coords[i];
		}
		return triangleCoords;
	}
}

