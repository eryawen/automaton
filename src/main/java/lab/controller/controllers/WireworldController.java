package lab.controller.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.WireWorldCell;
import lab.controller.enums.AutomatonType;
import lab.controller.enums.NeighbourhoodType;
import lab.controller.structures.WireElectronStructure;

public class WireworldController extends Controller2D {
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
	private Button backButton;
	@FXML
	private ChoiceBox<NeighbourhoodType> neighbourhood2DChoiceBox;
	@FXML
	private CheckBox wrappingCheckBox;
	@FXML
	private CheckBox randomStateCheckBox;
	@FXML
	private Canvas board;
	@FXML
	private ChoiceBox wireworldStructureChoiceBox;
	
	@FXML
	public void initialize() {
		super.initialize(board, startNewSimulationButton, clearButton,
					  wireworldStructureChoiceBox, wrappingCheckBox,
					  randomStateCheckBox, neighbourhood2DChoiceBox, radiusTextField, continueButton, pauseButton,
					  backButton);
	}
	
	@Override
	public void initStructuresAdd() {
		wireworldStructureChoiceBox.setItems(
			   FXCollections.observableArrayList(WireElectronStructure.WireElectronValue.values(
			   )));
		
		wireworldStructureChoiceBox.setValue(WireElectronStructure.WireElectronValue.DIODE);
	}
	
	@Override
	public AutomatonType getAutomatonType() {
		return AutomatonType.WIREWORLD;
	}
	
	@Override
	protected CellOnBoard getCellWithDefaultState(int i, int j) {
		return new WireWorldCell(i, j);
	}
}
