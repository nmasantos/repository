package screens;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class ControllerScreen implements Initializable, ControlledScreen{

	ScreensController myController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenParent(ScreensController screenParent) {

		myController = screenParent;
	}

}
