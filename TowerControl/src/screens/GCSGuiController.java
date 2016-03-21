package screens;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;

import connection.UDPClient;
import connection.UDPServer;
import drone.Drone;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class GCSGuiController implements Initializable, ControlledScreen{
	
	ScreensController myController;
	
	@FXML
	private TextField groundspeed;
	@FXML
	private TextField altitude;
	@FXML
	private TextField throttle;
	
	@FXML
	private ListView<String> listView;

	@FXML
	private WebView mapView;
	
	@FXML
	private KeyEvent keyEvent;
	
	private Drone drone;
	private int rpiPort;
	private WebEngine webEngine;
    private final ObservableList<String> currentList=FXCollections.observableArrayList();     
	
	public void changeHUDValue(Drone drone){
		this.groundspeed.setText("" + drone.getGroundspeed());
		this.altitude.setText("" + drone.getAltitude());
		this.throttle.setText("" + drone.getThrottle());
		
		//updateLocation();
	}
	
	public void changes(){
		
	}
	
	public void connectButtonClicked(){
		drone= new Drone(1);
		new Thread(new UDPServer(drone, this)).start(); // Before
		//UDPServer server = new UDPServer(this);
		//server.startServer();
	}
	
	public void changeMode(){
		new Thread(new UDPClient(rpiPort)).start();
	}
	
	public int getRpiPort(){
		return rpiPort;
	}
	
	public void setRpiPort(int rpiPort) {
		this.rpiPort = rpiPort;
	}
	
	public void searchDrones(){
		displayMap();
	}
	
	public void displayMap(){
		webEngine = mapView.getEngine();
		
		webEngine.load(getClass().getResource("/maps/example.html").toString());
		
	}
	
	//Testing the typing of keys
	public void tryKeyTyped(){
		
		groundspeed.setOnKeyTyped(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent ke) {
				 String text = "Key Typed: " + ke.getCharacter();
				 System.out.println(text);
			}
		});
		
	}
	
	//Test method to update drones location
	public void updateLocation(){
		
		double lat = drone.getLatitude();
		double lon = drone.getLongitude();

		System.out.println(lat + "\\" + lon);
		
		//Lisbon coordinates:  lat: 38.736946 lng:  -9.142685
		//webEngine.executeScript("locatingCoordinates(\"" + lat + "," + lon + "\")");
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	webEngine.executeScript("locatingCoordinates(\"" + lat + "," + lon + "\")");
		    }
		});
		
	}
   
    
	@Override
    public void initialize(URL url, ResourceBundle rb) {

		//This method listens to clicks on the listview and writes them on the value goundspeed
        listView.getSelectionModel().selectedItemProperty().addListener(
                         new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                updateLocation();
            }
        });
        
        /*  For a simple ListView as ours, this method can be omitted
         *  However I have included here to give you a hint for the next lessons   
        */
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>(){

              @Override
              public ListCell<String> call(ListView<String> p) {
                  final ListCell<String> cell=new ListCell<String>(){
                      @Override
                      public void updateItem(String item, boolean empty){
                          super.updateItem(item, empty);
                          if (item!=null){
                             setText(item);
                  }}};
                  return cell;
              }});
 
    }

	public void updateDronesList(ArrayList<Drone> dronesList) {
		listView.setItems(currentList);
		for(Drone drone: dronesList){
			Platform.runLater(new Runnable() {
			    @Override
			    public void run(){
			    	currentList.add("Drone: " + drone.systemID );
			    }
			});
		}
	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
}
