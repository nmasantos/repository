package screens;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ScreensSetup extends Application {

	
	public static String screen1ID = "login";
	public static String screen1file = "login.fxml";
	public static String screen2ID = "gcsgui";
	public static String screen2file = "GCSGui.fxml";
	public static String screen3ID = "controllerscreen";
	public static String screen3file = "controller.fxml";
	
	@Override
	public void start(Stage primaryStage) {
		ScreensController mainContainer = new ScreensController();
		
		mainContainer.loadScreen(primaryStage, ScreensSetup.screen1ID, ScreensSetup.screen1file);
		mainContainer.loadScreen(primaryStage, ScreensSetup.screen2ID, ScreensSetup.screen2file);
		
		mainContainer.setScreen(ScreensSetup.screen1ID);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
