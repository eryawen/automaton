package lab.model.states;

import lab.model.coords.Coords2D;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;

@EqualsAndHashCode
@Getter
public class LangtonCellState implements CellState {
	
	private BinaryState binaryState;
	private HashSet<Ant> ants;
	private LangtonColour colour;
	
	public LangtonCellState(BinaryState cellBinaryState, HashSet<Ant> ants) {
		this.binaryState = cellBinaryState;
		setColour(cellBinaryState);
		this.ants = ants;
	}
	
	void setColour(BinaryState binaryState) {
		if (binaryState == BinaryState.DEAD) {
			this.colour = LangtonColour.WHITE;
		} else {
			this.colour = LangtonColour.BLACK;
		}
	}
	
	public enum LangtonColour implements CellState {
		BLACK, WHITE
	}
	
	@Getter
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class Ant {
		private static int nextID = 1;
		private AntState antState;
		private final int antID;
		
		public Ant(AntState antState) {
			this.antState = antState;
			antID = nextID;
			nextID++;
		}
		
		public AntState getNextAntDirection(LangtonColour langtonColour) {
			return (langtonColour == LangtonColour.BLACK) ? turnAntLeft() : turnAntRight();
		}
		
		private AntState turnAntLeft() {
			switch (antState) {
				case NORTH:
					return AntState.EAST;
				case EAST:
					return AntState.SOUTH;
				case SOUTH:
					return AntState.WEST;
				case WEST:
					return AntState.NORTH;
				default:
					return AntState.NONE;
			}
		}
		
		private AntState turnAntRight() {
			switch (antState) {
				case NORTH:
					return AntState.WEST;
				case EAST:
					return AntState.NORTH;
				case SOUTH:
					return AntState.EAST;
				case WEST:
					return AntState.SOUTH;
				default:
					return AntState.NONE;
			}
		}
		
		public Coords2D getNextAntCoords(Coords2D cellCoords, LangtonColour langtonColour) {
			AntState nextAntState = getNextAntDirection(langtonColour);
			int newX = cellCoords.getX();
			int newY = cellCoords.getY();
			switch (nextAntState) {
				case NORTH:
					newX--;
					break;
				case SOUTH:
					newX++;
					break;
				case EAST:
					newY++;
					break;
				case WEST:
					newY--;
					break;
			}
			return new Coords2D(newX, newY);
		}
	}
}
