package lab.controller.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.LangtonAntCell;
import lab.controller.enums.AutomatonType;
import lab.controller.enums.NeighbourhoodType;
import lab.controller.structures.LangtonAntStructure;

public class LangtonAntController extends Controller2D {
	
	@FXML
	private TextField radiusTextField;
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
	private ChoiceBox langtonAntStructureChoiceBox;
	@FXML
	private Button backButton;
	
	@Override
	public void drawBoard() {
		Platform.runLater(() -> {
			gc.clearRect(0, 0, getBoardWidth(), getBoardHeight());
			for (CellOnBoard c : cellsOnBoard.values()) {
				gc.setFill(c.getColour());
				gc.fillRect(c.getPx(), c.getPy(), getCellSize(), getCellSize());
				double[] a = ((LangtonAntCell) c).getAntPxPy(getCellSize());
				Polygon polygon = new Polygon(a);
				gc.setFill(Color.RED);
				gc.fillPolygon(new double[]{a[0], a[2], a[4]},
							new double[]{a[1], a[3], a[5]}, 3);
			}
		});
	}
	
	@FXML
	public void initialize() {
		super.initialize(board, startNewSimulationButton, clearButton,
					  langtonAntStructureChoiceBox, wrappingCheckBox,
					  randomStateCheckBox, neighbourhood2DChoiceBox, radiusTextField, continueButton, pauseButton,
					  backButton);
	}
	
	@Override
	public void initStructuresAdd() {
		langtonAntStructureChoiceBox.setItems(FXCollections.observableArrayList(LangtonAntStructure.values(
		)));
		
		langtonAntStructureChoiceBox.setValue(LangtonAntStructure.NORTH);
	}
	
	@Override
	public AutomatonType getAutomatonType() {
		return AutomatonType.LANGTON_ANT;
	}
	
	@Override
	protected CellOnBoard getCellWithDefaultState(int i, int j) {
		return new LangtonAntCell(i, j);
	}
}
