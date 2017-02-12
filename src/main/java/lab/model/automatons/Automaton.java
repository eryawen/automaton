package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.coords.CellCoordinates;
import lab.model.neighbourhoods.CellNeighbourhood;
import lab.model.states.CellState;

import java.util.*;

/**
 * Base class which represents cellular automaton
 */

public abstract class Automaton {
	private CellNeighbourhood neighborsStrategy;
	private CellStateFactory cellStateFactory;
	private Map<CellCoordinates, CellState> cells;
	
	public Automaton(CellNeighbourhood neighborsStrategy, CellStateFactory cellStateFactory) {
		this.neighborsStrategy = neighborsStrategy;
		this.cellStateFactory = cellStateFactory;
		cells = new HashMap<>();
	}
	
	private Set<Cell> mapCoordinates(Set<CellCoordinates> cellCoordinates) {
		Set<Cell> mappedCoords = new HashSet<>();
		cellCoordinates.forEach((coords) -> mappedCoords.add(new Cell(coords, cells.get(coords))));
		return mappedCoords;
	}
	
	public Automaton nextState() {
		CellIterator currentAutomatonIterator = this.cellIterator();
		
		Automaton nextAutomaton = newInstance(neighborsStrategy, cellStateFactory);
		CellIterator nextAutomatonIterator = nextAutomaton.cellIterator();
		
		Cell prevCell;
		while (currentAutomatonIterator.hasNext()) {
			prevCell = currentAutomatonIterator.next();
			nextAutomatonIterator.next();
			
			Set<CellCoordinates> neighbours = neighborsStrategy.getCellNeighbours(prevCell.getCoords());
			Set<Cell> mapCoordinates = mapCoordinates(neighbours);
			CellState newState = nextCellState(prevCell, mapCoordinates);
			nextAutomatonIterator.setState(newState);
		}
		return nextAutomaton;
	}
	
	public CellIterator cellIterator() {
		return new CellIterator();
	}
	
	public void insertStructure(Map<CellCoordinates, CellState> structure) {
		structure.forEach((coords, state) -> cells.put(coords, state));
	}
	
	/**
	 * @param cellNeighbourhood represents strategy of determining neighbours
	 * @param cellStateFactory  contains initial state of automaton
	 * @return new instance of specific Automaton
	 */
	protected abstract Automaton newInstance(CellNeighbourhood cellNeighbourhood, CellStateFactory cellStateFactory);
	
	/**
	 * @return coordinates before the first in order of iteration
	 */
	protected abstract CellCoordinates initialCoordinates();
	
	protected abstract boolean hasNextCoordinates(CellCoordinates cellCoordinates);
	
	protected abstract CellCoordinates nextCoordinates(CellCoordinates cellCoordinates);
	
	/**
	 * @param currentCell      cell which next state will be calculated
	 * @param neighboursStates states of {@code currentCell} neighbours
	 * @return next state of {@code currentCell}
	 */
	protected abstract CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates);
	
	/**
	 * This method init automaton with states from {@code cellStateFactory}. Should be called after creating automaton.
	 */
	public void init() {
		CellCoordinates currentCoords = initialCoordinates();
		while (hasNextCoordinates(currentCoords)) {
			currentCoords = nextCoordinates(currentCoords);
			cells.put(currentCoords, cellStateFactory.initialState(currentCoords));
		}
	}
	
	public CellState getState(CellCoordinates cellCoordinates) {
		return cells.get(cellCoordinates);
	}
	
	public class CellIterator implements Iterator<Cell> {
		private CellCoordinates currentCoords;
		
		public CellIterator() {
			currentCoords = initialCoordinates();
		}
		
		@Override
		public boolean hasNext() {
			return hasNextCoordinates(currentCoords);
		}
		
		@Override
		public Cell next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			currentCoords = nextCoordinates(currentCoords);
			return new Cell(currentCoords, cells.get(currentCoords));
		}
		
		public void setState(CellState cellState) {
			cells.put(currentCoords, cellState);
		}
	}
}
