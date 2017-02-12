package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.coords.Coords1D;
import lab.model.neighbourhoods.CellNeighbourhood;
import lab.model.rules.Rule1D;
import lab.model.states.BinaryState;
import lab.model.states.CellState;

import java.util.Set;

public class Automaton1DimImpl extends Automaton1Dim {
	private Rule1D rule;
	
	public Automaton1DimImpl(CellNeighbourhood neighborsStrategy, int size, Rule1D rule,
						CellStateFactory cellStateFactory) {
		super(neighborsStrategy, size, cellStateFactory);
		this.rule = rule;
	}
	
	@Override
	protected Automaton newInstance(CellNeighbourhood cellNeighbourhood, CellStateFactory cellStateFactory) {
		return new Automaton1DimImpl(cellNeighbourhood, getSize(), rule, cellStateFactory);
	}
	
	@Override
	protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {
		Coords1D currentCellCoords = (Coords1D) currentCell.getCoords();
		BinaryState currentCellState = (BinaryState) currentCell.getState();
		
		BinaryState leftNeighbourState = BinaryState.DEAD;
		BinaryState rightNeighbourState = BinaryState.DEAD;
		
		for (Cell cell : neighboursStates) {
			if (cell.getState() == null) {
				cell.setState(BinaryState.DEAD);
			}
			
			Coords1D neighbourCoords = (Coords1D) cell.getCoords();
			BinaryState neighbourState = (BinaryState) cell.getState();
			
			if (neighbourCoords.getX() == currentCellCoords.getX() - 1) {
				leftNeighbourState = neighbourState;
			} else {
				rightNeighbourState = neighbourState;
			}
		}
		
		String states = getStateInBinary(leftNeighbourState) + getStateInBinary(currentCellState) + getStateInBinary(
			   rightNeighbourState);
		return rule.getNextBinaryState(states);
	}
	
	private String getStateInBinary(BinaryState binaryState) {
		return (binaryState == BinaryState.AlIVE) ? "1" : "0";
	}
}
