package lab.model.automatons;

import lab.model.Cell;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.coords.Coords2D;
import lab.model.neighbourhoods.CellNeighbourhood;
import lab.model.states.AntState;
import lab.model.states.BinaryState;
import lab.model.states.CellState;
import lab.model.states.LangtonCellState;

import java.util.HashSet;
import java.util.Set;

public class LangtonAnt extends Automaton2Dim {
	
	public LangtonAnt(CellNeighbourhood neighborsStrategy, int height, int width, CellStateFactory cellStateFactory) {
		super(neighborsStrategy, height, width, cellStateFactory);
	}
	
	@Override
	protected Automaton newInstance(CellNeighbourhood cellNeighbourhood, CellStateFactory cellStateFactory) {
		return new LangtonAnt(cellNeighbourhood, getHeight(), getWidth(), cellStateFactory);
	}
	
	@Override
	protected CellState nextCellState(Cell currentCell, Set<Cell> neighboursStates) {
		LangtonCellState currentCellState = (LangtonCellState) currentCell.getState();
		Coords2D currentCellcoords = (Coords2D) currentCell.getCoords();
		
		BinaryState nextBinaryState = findNextBinaryState(currentCellState);
		HashSet<LangtonCellState.Ant> currentCellAntSet = new HashSet<LangtonCellState.Ant>();
		
		for (Cell cell : neighboursStates) {
			LangtonCellState neighbourState = (LangtonCellState) cell.getState();
			Coords2D neighbourCoords = (Coords2D) cell.getCoords();
			HashSet<LangtonCellState.Ant> ants = neighbourState.getAnts();
			
			for (LangtonCellState.Ant ant : ants) {
				AntState nextAntDirection = ant.getNextAntDirection(neighbourState.getColour());
				Coords2D nextAntCoords = ant.getNextAntCoords(neighbourCoords, neighbourState.getColour());
				nextAntCoords = Automaton2Dim.translateCoords(nextAntCoords.getX(), nextAntCoords.getY(),
													 getHeight(), getWidth());
				
				if (nextAntCoords.equals(currentCellcoords)) {
					currentCellAntSet.add(new LangtonCellState.Ant(nextAntDirection, ant.getAntID()));
				}
			}
		}
		return new LangtonCellState(nextBinaryState, currentCellAntSet);
	}
	
	BinaryState findNextBinaryState(LangtonCellState currentLangtonCellState) {
		if (currentLangtonCellState.getAnts().size() != 0) {
			return (currentLangtonCellState.getBinaryState() == BinaryState.DEAD) ? BinaryState.AlIVE :
				  BinaryState.DEAD;
		}
		
		return currentLangtonCellState.getBinaryState();
	}
}
