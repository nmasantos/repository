package screens;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class LoginController implements Initializable, ControlledScreen {

	ScreensController myController;
	
	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public void goToScreen2(){
		myController.setScreen(ScreensSetup.screen2ID);
	}

	
}
