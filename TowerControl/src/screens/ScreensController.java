package screens;

import java.util.HashMap;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScreensController extends StackPane {

	private HashMap<String, Parent> screens = new HashMap<>();
	private Stage primaryStage;
	
	public ScreensController(){
		super();
	}
	
	public void addScreen(String name, Parent screen){
		screens.put(name, screen);
	}
	
	public Node getScreen(String name){
		return screens.get(name);
	}
	
	public boolean loadScreen(Stage primaryStage, String name, String resource){
		try{
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
			myScreenController.setScreenParent(this);
			addScreen(name, loadScreen);
			System.out.println(screens.size());
			this.primaryStage = primaryStage;
			return true;
		} catch (Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean setScreen(final String name) {       
     if(screens.get(name) != null){
    	 
    	 
      Scene scene = new Scene(screens.get(name));
      primaryStage.setScene(scene);
      primaryStage.setTitle(name);
      primaryStage.setResizable(false);
      primaryStage.show();
          
    	 return true;
    	 
     } else {
         System.out.println("screen hasn't been loaded!!! \n");
         return false;
     }
		
		/* if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {   //if there is more than one screen
                System.out.println("HEre");
            	Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        getChildren().remove(0);                    //remove the displayed screen
                        getChildren().add(0, screens.get(name));     //add the screen
                        Timeline fadeIn = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
            	System.out.println("There");
                setOpacity(0.0);
                getChildren().add( screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
        */
	} 
	
	  public boolean unloadScreen(String name) {
	        if (screens.remove(name) == null) {
	            System.out.println("Screen didn't exist");
	            return false;
	        } else {
	            return true;
	        }
	    }
}
