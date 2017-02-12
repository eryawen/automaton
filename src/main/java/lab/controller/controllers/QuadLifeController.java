package lab.controller.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.QuadLifeCell;
import lab.controller.enums.AutomatonType;
import lab.controller.enums.NeighbourhoodType;
import lab.controller.structures.GameOfLifeStructure;
import lab.controller.structures.QuadLifeStructure;

public class QuadLifeController extends Controller2D {
	@FXML
	public TextField radiusTextField;
	@FXML
	private TextField bornRuleTextField;
	@FXML
	private TextField deadRuleTextField;
	@FXML
	private ToggleButton startNewSimulationButton;
	@FXML
	private ToggleButton clearButton;
	@FXML
	private ToggleButton pauseButton;
	@FXML
	private ToggleButton continueButton;
	
	@FXML
	private ChoiceBox<NeighbourhoodType> neighbourhood2DChoiceBox;
	@FXML
	private CheckBox wrappingCheckBox;
	@FXML
	private CheckBox randomStateCheckBox;
	
	@FXML
	private Canvas board;
	@FXML
	private ChoiceBox quadLifeStructureChoiceBox;
	@FXML
	private ChoiceBox colourOfStructureChoiceBox;
	@FXML
	private Button backButton;
	
	@FXML
	public void initialize() {
		super.initialize(board, startNewSimulationButton, clearButton,
					  quadLifeStructureChoiceBox, wrappingCheckBox, randomStateCheckBox,
					  neighbourhood2DChoiceBox, radiusTextField, continueButton, pauseButton, backButton);
	}
	
	@Override
	public AutomatonType getAutomatonType() {
		return AutomatonType.QUADLIFE;
	}
	
	public QuadLifeStructure.QuadLifeCellColour getColourChoiceBoxValue() {
		return (QuadLifeStructure.QuadLifeCellColour) colourOfStructureChoiceBox.getValue();
	}
	
	@Override
	protected CellOnBoard getCellWithDefaultState(int i, int j) {
		return new QuadLifeCell(i, j);
	}
	
	@Override
	public void initStructuresAdd() {
		
		quadLifeStructureChoiceBox.setItems(FXCollections.observableArrayList(GameOfLifeStructure.values(
		)));
		quadLifeStructureChoiceBox.setValue(GameOfLifeStructure.ALIVE_CELL);
		
		colourOfStructureChoiceBox.setItems(
			   FXCollections.observableArrayList(QuadLifeStructure.QuadLifeCellColour.values()));
		
		colourOfStructureChoiceBox.setValue(QuadLifeStructure.QuadLifeCellColour.RANDOM);
	}
}
