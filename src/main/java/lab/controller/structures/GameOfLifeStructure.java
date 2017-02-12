package lab.controller.structures;

import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.GameOfLifeCell;
import lab.model.coords.Coords2D;
import lab.model.states.BinaryState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public enum GameOfLifeStructure implements Structure {
	ALIVE_CELL, DEAD_CELL, GLIDER, BLINKER, BEACON, TOAD, PULSAR, LIGHTWEIGHT_SPACESHIP, BLOCK, BEEHIVE, LOAF, GOSPER_GLIDER_GUN,
	PENTADECATHLON, BLINKER_PUFFER, PUFFER_FISH;
	
	@Override
	public HashMap<Coords2D, CellOnBoard> getStructure(int row, int col) {
		HashMap<Coords2D, CellOnBoard> map = new HashMap<>();
		String structure = "";
		File file = new File("src\\main\\resources\\structures\\" + this + ".txt");
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				structure = new StringBuilder().append(structure).append(scanner.nextLine()).toString();
			}
			structure = structure.replaceAll("\n", ""); //
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int positionInChar = 0;
		int rowIterator = 0;
		int colIterator = 0;
		String howManyCellsAddInString = "";
		int howManyCellsAdd;
		
		while (true) {
			if (structure.charAt(positionInChar) == '!') {
				break;
			}
			if (structure.charAt(positionInChar) == '$') {
				howManyCellsAdd = (howManyCellsAddInString == "") ? 1 : Integer.valueOf(howManyCellsAddInString);
				howManyCellsAddInString = "";
				rowIterator = rowIterator + howManyCellsAdd;
				colIterator = 0;
				positionInChar++;
				continue;
			}
			
			if (Character.isDigit(structure.charAt(positionInChar))) {
				howManyCellsAddInString = howManyCellsAddInString + structure.charAt(positionInChar);
				positionInChar++;
				continue;
			}
			
			howManyCellsAdd = (howManyCellsAddInString == "") ? 1 : Integer.valueOf(howManyCellsAddInString);
			BinaryState state = (structure.charAt(positionInChar) == 'b') ? BinaryState.DEAD : BinaryState.AlIVE;
			for (int i = 0; i < howManyCellsAdd; i++) {
				map.put(new Coords2D(rowIterator + row, colIterator + col + i),
					   new GameOfLifeCell(rowIterator + row, colIterator + col + i, state));
			}
			positionInChar++;
			colIterator = colIterator + howManyCellsAdd;
			howManyCellsAddInString = "";
		}
		
		return map;
	}
}
