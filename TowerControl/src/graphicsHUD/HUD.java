package graphicsHUD;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HUD extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HUD.fxml"));
			
			
			Group root = (Group)fxmlLoader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("HUD display");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			HUDController controller = fxmlLoader.getController();
			controller.paint();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
