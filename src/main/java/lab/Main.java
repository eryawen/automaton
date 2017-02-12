package lab;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		AnchorPane mainView = FXMLLoader.load(Main.class.getResource("/main_view.fxml"));
		Scene mainScene = new Scene(mainView);
		primaryStage.setTitle("Automaton");
		primaryStage.setOnCloseRequest(e -> Platform.exit());
		primaryStage.setScene(mainScene);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
