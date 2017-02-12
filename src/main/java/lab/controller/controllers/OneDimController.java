package lab.controller.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.cellOnBoard.OneDimAutomatonCell;
import lab.controller.enums.AutomatonType;
import lab.controller.enums.GameState;
import lab.controller.enums.NeighbourhoodType;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.cellStateFactories.GeneralStateFactory;
import lab.model.coords.CellCoordinates;
import lab.model.coords.Coords1D;
import lab.model.coords.Coords2D;
import lab.model.states.BinaryState;
import lab.model.states.CellState;

import java.util.HashMap;

public class OneDimController extends BaseGameController {
	@FXML
	private ToggleButton startNewSimulationButton;
	@FXML
	private ToggleButton clearButton;
	@FXML
	private ToggleButton pauseButton;
	@FXML
	private ToggleButton continueButton;
	@FXML
	private TextField rule1D;
	@FXML
	private CheckBox wrappingCheckBox;
	@FXML
	private CheckBox randomStateCheckBox;
	
	@FXML
	private Canvas board;
	@FXML
	private Button backButton;
	private int rowWhichIsDrawed = 0;
	
	@FXML
	public void initialize() {
		setButtons(board, startNewSimulationButton, clearButton, wrappingCheckBox, backButton, pauseButton,
				 continueButton, randomStateCheckBox);
	}
	
	public void gameLoop() {
		drawBoard();
		
		if (game.getGameState() == GameState.STARTED) {
			if (rowWhichIsDrawed < getNumberOfRowsInAutomaton()) {
				drawBoard();
				automaton = automaton.nextState();
				updateBoard1DAfterNextState(rowWhichIsDrawed);
				rowWhichIsDrawed++;
			} else {
				rowWhichIsDrawed = 0;
			}
		}
	}
	
	@Override
	protected void initChoiceBoxesInConcreteAutomaton() {
		
	}
	
	public void startNewSimulation() {
		game.setGameState(GameState.PAUSED);
		game.setInitialized(false);
		initBoard();
		game.init();
		if (game.isInitialized()) {
			disableChangeInAutomaton(true);
			clearButton.setDisable(false);
			continueButton.setDisable(true);
			pauseButton.setDisable(false);
			game.setGameState(GameState.STARTED);
			rowWhichIsDrawed = (isStateRandom()) ? 0 : 1;
		} else {
			pauseButtonPressed();
		}
	}
	
	public void continueButtonPressed() {
		disableChangeInAutomaton(true);
		continueButton.setDisable(true);
		pauseButton.setDisable(false);
		game.setGameState(GameState.STARTED);
	}
	
	@Override
	public CellStateFactory getStateFactory() {
		HashMap<CellCoordinates, CellState> map = new HashMap<>();
		
		cellsOnBoard.entrySet().forEach(cellOnBoard -> {
			Coords2D coords = (Coords2D) cellOnBoard.getKey();
			if (coords.getX() == 0) {
				map.put(new Coords1D(coords.getY()), cellOnBoard.getValue().getCellState());
			}
		});
		return new GeneralStateFactory(map);
	}
	
	@Override
	protected CellOnBoard getCellWithDefaultState(int i, int j) {
		return new OneDimAutomatonCell(i, j);
	}
	
	@Override
	public NeighbourhoodType getNeighbourhoodKind() {
		return NeighbourhoodType.ONE_DIMENSIONAL;
	}
	
	public String getRule() {
		return String.format(rule1D.getText());
	}
	
	public void initBoard() {
		cellsOnBoard = new HashMap<>();
		for (int i = 0; i < getNumberOfRowsInAutomaton(); i++) {
			for (int j = 0; j < getNumberOfColsInAutomaton(); j++) {
				cellsOnBoard.put(new Coords2D(i, j), getCellWithDefaultState(i, j));
			}
		}
		if (!isStateRandom()) {
			cellsOnBoard.put(new Coords2D(0, getNumberOfColsInAutomaton() / 2),
						  new OneDimAutomatonCell(0, getNumberOfColsInAutomaton() / 2, BinaryState.AlIVE));
		}
	}
	
	public void updateBoard1DAfterNextState(int generationCount) {
		for (int i = 0; i < getNumberOfColsInAutomaton(); i++) {
			cellsOnBoard.put(new Coords2D(generationCount, i),
						  new OneDimAutomatonCell(generationCount, i, automaton.getState(new Coords1D(i))));
		}
	}
	
	@Override
	public AutomatonType getAutomatonType() {
		return AutomatonType.ONE_DIMENSIONAL;
	}
	
	@Override
	void disableChangeInAutomaton(boolean isDisabled) {
		
	}
}
