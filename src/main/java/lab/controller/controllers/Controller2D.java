package lab.controller.controllers;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import lab.controller.enums.GameState;
import lab.controller.enums.NeighbourhoodType;
import lab.controller.structures.StructureInsertion;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.cellStateFactories.GeneralStateFactory;
import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords2D;
import lab.model.states.CellState;

import java.util.HashMap;

public abstract class Controller2D extends BaseGameController {
	StructureInsertion structureInsertion;
	private ChoiceBox structureChoiceBox;
	private ChoiceBox<NeighbourhoodType> neighbourhood2DChoiceBox;
	private TextField radiusTextField;
	
	public void initialize(Canvas board, ToggleButton startButton, ToggleButton clearButton,
					   ChoiceBox structureChoiceBox, CheckBox wrappingCheckBox, CheckBox randomStateCheckBox,
					   ChoiceBox<NeighbourhoodType> neighbourhood2DChoiceBox, TextField radiusTextField,
					   ToggleButton continueButton,
					   ToggleButton pauseButton, Button backButton) {
		this.neighbourhood2DChoiceBox = neighbourhood2DChoiceBox;
		this.radiusTextField = radiusTextField;
		super.setButtons(board, startButton, clearButton, wrappingCheckBox, backButton, pauseButton, continueButton,
					  randomStateCheckBox);
		
		this.structureChoiceBox = structureChoiceBox;
		structureInsertion = new StructureInsertion(this, game, structureChoiceBox);
		initStructuresAdd();
	}
	
	public void initBoard() {
		cellsOnBoard = new HashMap<>();
		for (int i = 0; i < getNumberOfRowsInAutomaton(); i++) {
			for (int j = 0; j < getNumberOfColsInAutomaton(); j++) {
				cellsOnBoard.put(new Coords2D(i, j), getCellWithDefaultState(i, j));
			}
		}
	}
	
	@Override
	public void initChoiceBoxesInConcreteAutomaton() {
		neighbourhood2DChoiceBox.setPadding(new Insets(0, 0, 0, 5));
		neighbourhood2DChoiceBox.setItems(FXCollections.observableArrayList(
			   NeighbourhoodType.MOORE, NeighbourhoodType.VON_NEUMANN));
		neighbourhood2DChoiceBox.setValue(NeighbourhoodType.MOORE);
	}
	
	public void gameLoop() {
		drawBoard();
		if (game.getGameState() == GameState.STARTED) {
			
			automaton = automaton.nextState();
			updateBoard2DAfterNextState();
		}
	}
	
	public void updateBoard2DAfterNextState() {
		cellsOnBoard.entrySet()
				  .forEach(entry -> entry.getValue().updateCellState(automaton.getState(entry.getKey())));
	}
	
	public abstract void initStructuresAdd();
	
	@Override
	public NeighbourhoodType getNeighbourhoodKind() {
		return neighbourhood2DChoiceBox.getValue();
	}
	
	public CellStateFactory getStateFactory() {
		HashMap<CellCoordinates, CellState> map = new HashMap<>();
		
		cellsOnBoard.entrySet()
				  .forEach(cellOnBoard -> map.put(cellOnBoard.getKey(), cellOnBoard.getValue().getCellState()));
		return new GeneralStateFactory(map);
	}
	
	public String getRadius() {
		return radiusTextField.getText();
	}
	
	@Override
	void disableChangeInAutomaton(boolean isDisabled) {
		structureChoiceBox.setDisable(isDisabled);
	}
}