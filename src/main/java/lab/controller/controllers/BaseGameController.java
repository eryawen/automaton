package lab.controller.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lab.Main;
import lab.controller.Game;
import lab.controller.cellOnBoard.CellOnBoard;
import lab.controller.enums.AutomatonType;
import lab.controller.enums.GameState;
import lab.controller.enums.NeighbourhoodType;
import lab.model.automatons.Automaton;
import lab.model.cellStateFactories.CellStateFactory;
import lab.model.cellStateFactories.GeneralStateFactory;
import lab.model.coords.CellCoordinates;
import lombok.Getter;

import java.io.IOException;
import java.util.Map;

public abstract class BaseGameController {
	
	@Getter
	private static int cellSize;
	private final int speed = 200;
	ToggleButton startButton;
	ToggleButton clearButton;
	CheckBox wrappingCheckBox;
	@Getter
	Canvas board;
	GraphicsContext gc;
	Map<CellCoordinates, CellOnBoard> cellsOnBoard;
	Game game = new Game(this);
	Automaton automaton;
	private Button backButton;
	private ToggleButton continueButton;
	private ToggleButton pauseButton;
	@Getter
	private int numberOfColsInAutomaton;
	@Getter
	private int numberOfRowsInAutomaton;
	private TextField radiusTextField;
	private CheckBox randomStateCheckBox;
	
	protected void setButtons(Canvas board, ToggleButton startButton, ToggleButton clearButton,
						 CheckBox wrappingCheckBox, Button backButton, ToggleButton pauseButton,
						 ToggleButton continueButton, CheckBox randomStateCheckBox) {
		this.startButton = startButton;
		this.clearButton = clearButton;
		this.wrappingCheckBox = wrappingCheckBox;
		this.board = board;
		this.backButton = backButton;
		this.pauseButton = pauseButton;
		this.continueButton = continueButton;
		this.randomStateCheckBox = randomStateCheckBox;
	}
	
	protected abstract void initChoiceBoxesInConcreteAutomaton();
	
	public void drawBoard() {
		Platform.runLater(() -> {
			gc.clearRect(0, 0, getBoardWidth(), getBoardHeight());
			for (CellOnBoard c : cellsOnBoard.values()) {
				gc.setFill(c.getColour());
				gc.fillRect(c.getPx(), c.getPy(), cellSize, cellSize);
			}
		});
	}
	
	public void startNewSimulation() {
		
		game.setInitialized(false);
		game.init();
		if (game.isInitialized()) {
			
			disableChangeInAutomaton(true);
			clearButton.setDisable(false);
			continueButton.setDisable(true);
			pauseButton.setDisable(false);
			game.setGameState(GameState.STARTED);
		} else {
			pauseButtonPressed();
		}
	}
	
	public void clearButtonPressed() {
		game.setGameState(GameState.STOPPED);
		game.setInitialized(false);
		disableChangeInAutomaton(false);
		continueButton.setDisable(true);
		pauseButton.setDisable(true);
		initBoard();
	}
	
	public void continueButtonPressed() {
		disableChangeInAutomaton(true);
		continueButton.setDisable(true);
		pauseButton.setDisable(false);
		automaton.insertStructure(((GeneralStateFactory) getStateFactory()).getStates());
		game.setGameState(GameState.STARTED);
	}
	
	public void pauseButtonPressed() {
		game.setGameState(GameState.PAUSED);
		continueButton.setDisable(false);
		clearButton.setDisable(false);
		disableChangeInAutomaton(false);
	}
	
	public void backButtonPressed() {
		
		AnchorPane newAnchorPane;
		try {
			newAnchorPane = FXMLLoader.load(Main.class.getResource("/main_view.fxml"));
		} catch (IOException e) {
			return;
		}
		Scene scene = new Scene(newAnchorPane);
		Stage newView = (Stage) backButton.getScene().getWindow();
		newView.setScene(scene);
		newView.show();
	}
	
	abstract void disableChangeInAutomaton(boolean isDisabled);
	
	public int getBoardHeight() {
		return numberOfRowsInAutomaton * cellSize;
	}
	
	public int getBoardWidth() {
		return numberOfColsInAutomaton * cellSize;
	}
	
	public boolean isStateRandom() {
		return randomStateCheckBox.isSelected();
	}
	
	public boolean withWrapping() {
		return wrappingCheckBox.isSelected();
	}
	
	public abstract NeighbourhoodType getNeighbourhoodKind();
	
	public abstract AutomatonType getAutomatonType();
	
	public abstract void initBoard();
	
	public abstract void gameLoop();
	
	public abstract CellStateFactory getStateFactory();
	
	protected abstract CellOnBoard getCellWithDefaultState(int i, int j);
	
	public void setAutomaton(Automaton automaton) {
		this.automaton = automaton;
	}
	
	public void initView(int numberOfRowsInAutomaton, int numberOfColsInAutomaton, int cellsize) {
		cellSize = cellsize;
		this.numberOfColsInAutomaton = numberOfColsInAutomaton;
		this.numberOfRowsInAutomaton = numberOfRowsInAutomaton;
		
		initChoiceBoxesInConcreteAutomaton();
		
		game = new Game(this);
		
		board.setHeight(getBoardHeight());
		board.setWidth(getBoardWidth());
		gc = board.getGraphicsContext2D();
		initBoard();
		
		final Duration oneFrameAmt = Duration.millis(speed);
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(oneFrameAmt,
										 event -> gameLoop()));
		timeline.play();
	}
	
	public Map<CellCoordinates, CellOnBoard> getCellsOnBoard() {
		return cellsOnBoard;
	}
}
