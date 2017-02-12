package lab.controller.structures;

import lab.controller.cellOnBoard.CellOnBoard;
import lab.model.coords.Coords2D;

import java.util.HashMap;

public interface Structure {
	HashMap<Coords2D, CellOnBoard> getStructure(int row, int col);
}

