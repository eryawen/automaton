package lab.controller.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.GameOfLifeCell;
import lab.controller.enums.AutomatonType;
import lab.controller.enums.NeighbourhoodType;
import lab.controller.structures.GameOfLifeStructure;

public class GameOfLifeController extends Controller2D {
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
	private ChoiceBox gameOfLifeStructureChoiceBox;
	@FXML
	private Button backButton;
	
	@Override
	public void initStructuresAdd() {
		
		gameOfLifeStructureChoiceBox.setItems(FXCollections.observableArrayList(GameOfLifeStructure.values(
		)));
		
		gameOfLifeStructureChoiceBox.setValue(GameOfLifeStructure.ALIVE_CELL);
	}
	
	@FXML
	public void initialize() {
		super.initialize(board, startNewSimulationButton, clearButton,
					  gameOfLifeStructureChoiceBox, wrappingCheckBox, randomStateCheckBox,
					  neighbourhood2DChoiceBox, radiusTextField, continueButton, pauseButton, backButton);
	}
	
	public AutomatonType getAutomatonType() {
		return AutomatonType.GAME_OF_LIFE;
	}
	
	@Override
	protected CellOnBoard getCellWithDefaultState(int i, int j) {
		return new GameOfLifeCell(i, j);
	}
	
	public String getRule() {
		return String.format("%s;%s", bornRuleTextField.getText(), deadRuleTextField.getText());
	}
}
