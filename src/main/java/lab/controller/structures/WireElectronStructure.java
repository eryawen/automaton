package lab.controller.structures;

import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.WireWorldCell;
import lab.model.coords.Coords2D;
import lab.model.states.WireElectronState;
import lombok.Getter;

import java.util.HashMap;

public class WireElectronStructure implements Structure {
	
	private WireElectronValue wireElectronValue;
	private int numberOfColsInAutomaton;
	
	public WireElectronStructure(WireElectronValue wireElectronValue, int numberOfColsInAutomaton) {
		this.wireElectronValue = wireElectronValue;
		this.numberOfColsInAutomaton = numberOfColsInAutomaton;
	}
	
	public enum WireElectronValue {
		
		VOID(WireElectronState.VOID), WIRE(WireElectronState.WIRE),
		ELECTRON_HEAD(WireElectronState.ELECTRON_HEAD), ELECTRON_TAIL(WireElectronState.ELECTRON_TAIL),
		DIODE;
		@Getter
		WireElectronState wireElectronState;
		
		WireElectronValue() {
			
		}
		
		WireElectronValue(WireElectronState wireElectronState) {
			this.wireElectronState = wireElectronState;
		}
	}
	
	@Override
	public HashMap<Coords2D, CellOnBoard> getStructure(int row, int col) {
		HashMap<Coords2D, CellOnBoard> structure = new HashMap<>();
		
		if (wireElectronValue == wireElectronValue.DIODE) {
			WireElectronValue currentWireElectronValue;
			
			for (int j = 0; j < numberOfColsInAutomaton; j++) {
				currentWireElectronValue = wireElectronValue.WIRE;
				if (j == col || j == col + 7) {
					currentWireElectronValue = wireElectronValue.ELECTRON_TAIL;
				}
				if (j == col + 1 || j == col + 8) {
					currentWireElectronValue = wireElectronValue.ELECTRON_HEAD;
				}
				if (j == col + 3) {
					currentWireElectronValue = wireElectronValue.VOID;
				}
				structure.put(new Coords2D(row, j),
						    new WireWorldCell(row, j, currentWireElectronValue.getWireElectronState()));
			}
			structure.put(new Coords2D(row - 1, col + 2),
					    new WireWorldCell(row - 1, col + 2, wireElectronValue.WIRE.getWireElectronState()));
			structure.put(new Coords2D(row - 1, col + 3),
					    new WireWorldCell(row - 1, col + 3, wireElectronValue.WIRE.getWireElectronState()));
			structure.put(new Coords2D(row + 1, col + 2),
					    new WireWorldCell(row + 1, col + 2, wireElectronValue.WIRE.getWireElectronState()));
			structure.put(new Coords2D(row + 1, col + 3),
					    new WireWorldCell(row + 1, col + 3, wireElectronValue.WIRE.getWireElectronState()));
			return structure;
		}
		
		structure.put(new Coords2D(row, col), new WireWorldCell(row, col, wireElectronValue.getWireElectronState()));
		return structure;
	}
}
