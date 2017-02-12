package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.neighbourhoods.CellNeighbourhood;
import lab.model.states.CellState;
import lab.model.states.WireElectronState;

import java.util.Set;

import static lab.model.states.WireElectronState.*;

public class WireWorld extends Automaton2Dim {
	public WireWorld(CellNeighbourhood neighborsStrategy, int height, int width, CellStateFactory cellStateFactory) {
		super(neighborsStrategy, height, width, cellStateFactory);
	}
	
	@Override
	protected Automaton newInstance(CellNeighbourhood cellNeighbourhood, CellStateFactory cellStateFactory) {
		return new WireWorld(cellNeighbourhood, getHeight(), getWidth(), cellStateFactory);
	}
	
	@Override
	protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {
		
		WireElectronState wireElectronState = (WireElectronState) currentCell.getState();
		
		switch (wireElectronState) {
			case ELECTRON_HEAD:
				return ELECTRON_TAIL;
			case ELECTRON_TAIL:
				return WIRE;
			case WIRE:
				return (hasOneOrTwoHeadNeighbours(neighboursStates)) ? ELECTRON_HEAD : WIRE;
			default:
				return VOID;
		}
	}
	
	boolean hasOneOrTwoHeadNeighbours(Set<Cell> neighboursStates) {
		int headCounter = 0;
		for (Cell cell : neighboursStates) {
			if (cell.getState() == ELECTRON_HEAD) {
				headCounter++;
			}
		}
		return (headCounter == 1 || headCounter == 2);
	}
}
