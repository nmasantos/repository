package connection;

import org.mavlink.messages.MAVLinkMessage;
import org.mavlink.messages.ardupilotmega.msg_gps_raw_int;
import org.mavlink.messages.ardupilotmega.msg_heartbeat;
import org.mavlink.messages.ardupilotmega.msg_vfr_hud;

import drone.Drone;
import screens.GCSGuiController;

public class MessageHandler{

	private Drone drone;
	private GCSGuiController gui;
	private int count = 0;
	
	public MessageHandler(Drone drone, GCSGuiController gui) {
		this.drone = drone;
		this.gui = gui;
	}
	

	public void getMessage(MAVLinkMessage msg) {
		 
		
		switch(msg.messageType){
			case 74:
				msg_vfr_hud hud_msg = (msg_vfr_hud) msg;
				drone.setAltitude(hud_msg.alt);
				drone.setGroundspeed(hud_msg.groundspeed);
				drone.setThrottle(hud_msg.throttle);
				
				gui.changeHUDValue(drone);
				
				break;
			case 0:
				msg_heartbeat hb_msg = (msg_heartbeat) msg;
				drone.setMavMode(hb_msg.base_mode);
				drone.setAutopilot(hb_msg.autopilot);
				
				break;
			
			case 24:
				msg_gps_raw_int gps_msg = (msg_gps_raw_int) msg;
				double latitude = (double) (gps_msg.lat/10000000.0);
				double longitude = (double) (gps_msg.lon/10000000.0);
				
				drone.setLatitude(latitude);
				drone.setLongitude(longitude);
				
				
				gui.updateLocation();
				
				count++;
				break;
				
		}
	}

}
