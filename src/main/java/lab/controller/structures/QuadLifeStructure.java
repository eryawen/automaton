package lab.controller.structures;

import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.QuadLifeCell;
import lab.model.coords.Coords2D;
import lab.model.states.BinaryState;
import lab.model.states.QuadState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class QuadLifeStructure implements Structure {
	private final static Random RANDOM_GEN = new Random();
	
	GameOfLifeStructure choosenGameOfLifeStructure;
	private QuadLifeCellColour colour;
	
	public QuadLifeStructure(GameOfLifeStructure choosenGameOfLifeStructure, QuadLifeCellColour colour) {
		this.choosenGameOfLifeStructure = choosenGameOfLifeStructure;
		this.colour = colour;
	}
	
	@Override
	public HashMap<Coords2D, CellOnBoard> getStructure(int row, int col) {
		
		HashMap<Coords2D, CellOnBoard> structure = choosenGameOfLifeStructure.getStructure(row, col);
		HashMap<Coords2D, CellOnBoard> newStructure = new HashMap<>();
		
		structure.forEach((key, value) -> {
			Coords2D coords2D = key;
			if (value.getCellState() == BinaryState.DEAD) {
				newStructure.put(key, new QuadLifeCell(coords2D.getX(), coords2D.getY(), QuadState.DEAD));
			} else {
				newStructure.put(key, new QuadLifeCell(coords2D.getX(), coords2D.getY(), colour.getQuadState()));
			}
		});
		
		return newStructure;
	}
	
	public enum QuadLifeCellColour {
		GREEN(QuadState.GREEN), YELLOW(QuadState.YELLOW), RED(QuadState.RED), BLUE(QuadState.BLUE), RANDOM;
		
		private final List<QuadState> quadStateValues = Arrays.asList(QuadState.BLUE, QuadState.GREEN, QuadState.RED,
														  QuadState.YELLOW);
		private QuadState quadstate;
		
		QuadLifeCellColour() {
		}
		
		QuadLifeCellColour(QuadState quadstate) {
			this.quadstate = quadstate;
		}
		
		public QuadState getQuadState() {
			if (this == RANDOM) {
				return quadStateValues.get(RANDOM_GEN.nextInt(quadStateValues.size()));
			}
			return quadstate;
		}
	}
}
