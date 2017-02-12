package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.neighbourhoods.CellNeighbourhood;
import lab.model.rules.Rule2D;
import lab.model.states.CellState;
import lab.model.states.QuadState;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class QuadLife extends GameOfLife {
	
	public QuadLife(CellNeighbourhood cellNeighbourhood, int height, int width, CellStateFactory cellStateFactory) {
		super(cellNeighbourhood, new Rule2D("2,3;3"), height, width, cellStateFactory);
	}
	
	public Automaton newInstance(CellNeighbourhood cellNeighbourhood, CellStateFactory cellStateFactory) {
		return new QuadLife(cellNeighbourhood, getHeight(), getWidth(), cellStateFactory);
	}
	
	@Override
	protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {
		int numberOfAliveNeighbours = countAliveNeighbours(neighboursStates);
		QuadState currentCellState = (QuadState) currentCell.getState();
		
		if ((currentCellState == QuadState.DEAD) && (checkIfDeadBecomeAlive(numberOfAliveNeighbours))) {
			return findNextQuadState(neighboursStates);
		} else if ((currentCellState != QuadState.DEAD) && (checkIfAliveStillAlive(numberOfAliveNeighbours))) {
			return currentCellState;
		}
		return QuadState.DEAD;
	}
	
	CellState findNextQuadState(Set<Cell> neighbours) {
		Map<CellState, Integer> colourCounterMap = new HashMap<>();
		
		Stream.of(QuadState.values()).forEach(colour -> colourCounterMap.put(colour, 0));
		colourCounterMap.remove(QuadState.DEAD);
		
		neighbours.forEach(cell -> {
			if (cell.getState() != QuadState.DEAD) {
				colourCounterMap.put(cell.getState(), colourCounterMap.get(cell.getState()) + 1);
			}
		});
		
		CellState cellState = colourCounterMap.entrySet()
									   .stream()
									   .filter(entry -> (entry.getValue() == 0) || (entry.getValue()) == 2 || (entry.getValue() == 3))
									   .max(Map.Entry.comparingByValue())
									   .get()
									   .getKey();
		return cellState;
	}
}
