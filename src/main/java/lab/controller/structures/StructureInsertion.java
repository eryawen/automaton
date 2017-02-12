package lab.controller.structures;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import lab.controller.Game;
import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.controllers.BaseGameController;
import lab.controller.controllers.QuadLifeController;
import lab.controller.enums.GameState;
import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords2D;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class StructureInsertion {
	BaseGameController baseGameController;
	Game game;
	private ChoiceBox structureChoiceBox;
	@Setter
	private boolean isAddingStructureActive = true;
	
	public StructureInsertion(BaseGameController baseGameController, Game game, ChoiceBox StructureChoiceBox) {
		this.baseGameController = baseGameController;
		this.game = game;
		structureChoiceBox = StructureChoiceBox;
		init();
	}
	
	public void addStructureToBoard(int row, int col) {
		
		HashMap<Coords2D, CellOnBoard> structure;
		
		switch (baseGameController.getAutomatonType()) {
			case QUADLIFE:
				structure = (new QuadLifeStructure((GameOfLifeStructure) structureChoiceBox.getValue(),
											((QuadLifeController) baseGameController).getColourChoiceBoxValue()))
					   .getStructure(row, col);
				break;
			case WIREWORLD:
				structure = (new WireElectronStructure(
					   (WireElectronStructure.WireElectronValue) structureChoiceBox.getValue(),
					   baseGameController.getNumberOfColsInAutomaton())).getStructure(row, col);
				break;
			default:
				structure = ((Structure) structureChoiceBox.getValue()).getStructure(row, col);
		}
		
		for (Coords2D cellCoordinates : structure.keySet())
		
		{
			try {
				if (cellCoordinates.getX() < 0 || (cellCoordinates.getX() >= baseGameController.getNumberOfRowsInAutomaton())
				    || cellCoordinates.getY() < 0 || cellCoordinates.getY() >= baseGameController.getNumberOfColsInAutomaton()) {
					throw new StructureBeyondBoardException();
				}
			} catch (StructureBeyondBoardException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Structure is beyond board");
				alert.showAndWait();
				return;
			}
		}
		
		Map<CellCoordinates, CellOnBoard> cellsOnBoard = baseGameController.getCellsOnBoard();
		structure.entrySet().forEach(cellOnBoard -> cellsOnBoard.put(cellOnBoard.getKey(), cellOnBoard.getValue()));
	}
	
	public void init() {
		baseGameController.getBoard().setOnMouseClicked(event -> {
			if (game.getGameState() != GameState.STARTED) {
				isAddingStructureActive = true;
			}
		});
		
		baseGameController.getBoard().setOnMouseClicked(event -> {
			if (isAddingStructureActive) {
				int row = (int) event.getY();
				row = row / BaseGameController.getCellSize();
				int col = (int) event.getX();
				col = col / BaseGameController.getCellSize();
				
				addStructureToBoard(row, col);
			}
		});
	}
}


