package lab.controller.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lab.Main;
import lab.controller.enums.AutomatonType;
import lab.model.exceptions.IllegalBoard2DDimensionException;

import java.io.IOException;

import static java.lang.Math.min;

public class MainViewController {
	public static final int MAX_BOARD_HEIGHT = 560;
	public static final int MAX_BOARD_WIDTH = 800;
	private static final int MAX_ROWS = 60;
	private static final int MAX_COLS = 80;
	private static final int MIN_ROWS = 3;
	private static final int MIN_COLS = 3;
	private static final int PREF_ROWS = 60;
	private static final int PREF_COLS = 80;
	@FXML
	private ChoiceBox automatonChoiceBox;
	@FXML
	private TextField numberOfRowsTextField;
	@FXML
	private TextField numberOfColsTextField;
	
	@FXML
	public void initialize() {
		automatonChoiceBox.setStyle("-fx-font: 16px \"System\";");
		automatonChoiceBox.setPadding(new Insets(0, 0, 0, 30));
		
		automatonChoiceBox.setItems(
			   FXCollections.observableArrayList(AutomatonType.GAME_OF_LIFE, AutomatonType.QUADLIFE,
										  AutomatonType.LANGTON_ANT, AutomatonType.WIREWORLD,
										  AutomatonType.ONE_DIMENSIONAL));
		automatonChoiceBox.setValue(AutomatonType.GAME_OF_LIFE);
	}
	
	public AutomatonType getAutomatonType() {
		return (AutomatonType) automatonChoiceBox.getValue();
	}
	
	public int getNumberOfRowsInAutomaton() {
		try {
			int dim = Integer.parseInt(numberOfRowsTextField.getText());
			if (dim < MIN_ROWS || dim > MAX_ROWS) {
				numberOfRowsTextField.setText(String.format("%d", PREF_ROWS));
				throw new IllegalBoard2DDimensionException(
					   String.format("Rows must be integer between %d i %d", MIN_ROWS, MAX_ROWS));
			}
			return dim;
		} catch (IllegalArgumentException ex) {
			numberOfRowsTextField.setText(String.format("%d", PREF_ROWS));
			throw new IllegalBoard2DDimensionException(
				   String.format("Rows must be integer between %d i %d", MIN_ROWS, MAX_ROWS));
		}
	}
	
	public int getNumberOfColsInAutomaton() {
		try {
			int dim = Integer.parseInt(numberOfColsTextField.getText());
			if (dim < MIN_COLS || dim > MAX_COLS) {
				numberOfColsTextField.setText(String.format("%d", PREF_COLS));
				throw new IllegalBoard2DDimensionException(
					   String.format("Cols must be integer between %d i %d", MIN_COLS, MAX_COLS));
			}
			return dim;
		} catch (IllegalArgumentException ex) {
			numberOfColsTextField.setText(String.format("%d", PREF_COLS));
			throw new IllegalBoard2DDimensionException(
				   String.format("Cols must be integer between %d i %d", MIN_COLS, MAX_COLS));
		}
	}
	
	public void changeView(ActionEvent actionEvent) {
		AnchorPane newAnchorPane = null;
		FXMLLoader loader;
		switch (getAutomatonType()) {
			case LANGTON_ANT:
				loader = new FXMLLoader(Main.class.getResource("/langton_ant.fxml"));
				break;
			case ONE_DIMENSIONAL:
				loader = new FXMLLoader(Main.class.getResource("/one_dimensional.fxml"));
				break;
			case WIREWORLD:
				loader = new FXMLLoader(Main.class.getResource("/wireworld.fxml"));
				break;
			case QUADLIFE:
				loader = new FXMLLoader(Main.class.getResource("/quad_life.fxml"));
				break;
			default:
				loader = new FXMLLoader(Main.class.getResource("/game_of_life.fxml"));
		}
		
		try {
			newAnchorPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BaseGameController controller =
			   loader.getController();
		
		try {
			int rows = getNumberOfRowsInAutomaton();
			int cols = getNumberOfColsInAutomaton();
			int cellSize = min(MAX_BOARD_HEIGHT / rows, MAX_BOARD_WIDTH / cols);
			controller.initView(rows, cols, cellSize);
		} catch (IllegalBoard2DDimensionException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			return;
		}
		
		Stage newView = (Stage) automatonChoiceBox.getScene().getWindow();
		
		Scene scene = new Scene(newAnchorPane);
		newView.setScene(scene);
		newView.centerOnScreen();
		newView.show();
	}
}