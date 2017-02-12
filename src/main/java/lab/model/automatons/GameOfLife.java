package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.neighbourhoods.CellNeighbourhood;
import lab.model.rules.Rule2D;
import lab.model.states.BinaryState;
import lab.model.states.CellState;
import lab.model.states.QuadState;

import java.util.List;
import java.util.Set;

public class GameOfLife extends Automaton2Dim {
	Rule2D rule2D;
	
	public GameOfLife(CellNeighbourhood cellNeighbourhood, Rule2D rule2D, int height, int width,
				   CellStateFactory cellStateFactory) {
		super(cellNeighbourhood, height, width, cellStateFactory);
		this.rule2D = rule2D;
	}
	
	@Override
	protected Automaton newInstance(CellNeighbourhood cellNeighbourhood, CellStateFactory cellStateFactory) {
		return new GameOfLife(cellNeighbourhood, rule2D, getHeight(), getWidth(), cellStateFactory);
	}
	
	@Override
	protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {
		int numberOfAliveNeighbours = countAliveNeighbours(neighboursStates);
		
		if ((currentCell.getState() == BinaryState.AlIVE) && (checkIfAliveStillAlive(numberOfAliveNeighbours))) {
			return BinaryState.AlIVE;
		}
		if ((currentCell.getState() == BinaryState.DEAD) && (checkIfDeadBecomeAlive(numberOfAliveNeighbours))) {
			return BinaryState.AlIVE;
		}
		return BinaryState.DEAD;
	}
	
	int countAliveNeighbours(Set<Cell> neighboursStates) {
		int numberOfAliveNeighbours = 0;
		
		for (Cell cell : neighboursStates) {
			if (cell.getState() != BinaryState.DEAD && cell.getState() != QuadState.DEAD) {
				numberOfAliveNeighbours++;
			}
		}
		return numberOfAliveNeighbours;
	}
	
	boolean checkIfAlive(List<Integer> neighboursRule, int numberOfAliveNeighbours) {
		
		for (Integer value : neighboursRule) {
			if (numberOfAliveNeighbours == value) {
				return true;
			}
		}
		
		return false;
	}
	
	boolean checkIfAliveStillAlive(int numberOfAliveNeighbours) {
		return checkIfAlive(rule2D.getRuleForAliveCells(), numberOfAliveNeighbours);
	}
	
	boolean checkIfDeadBecomeAlive(int numberOfDeadNeighbours) {
		return checkIfAlive(rule2D.getRuleForDeadCells(), numberOfDeadNeighbours);
	}
}
