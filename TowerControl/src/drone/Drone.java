package drone;

public class Drone {

	public int systemID;
	public float altitude;
	public float groundspeed;
	public float yaw;
	public float pitch;
	public float throttle;
	public float verticalSpeed;
	public int MavMode;
	public int autopilot;
	public double latitude;
	public double longitude;
	
	
	public Drone(int systemID){
		this.systemID = systemID;
	}
	
	public float getAltitude() {
		return altitude;
	}
	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}
	
	public float getGroundspeed() {
		return groundspeed;
	}
	public void setGroundspeed(float groundspeed) {
		this.groundspeed = groundspeed;
	}
	
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public int getSystemID() {
		return systemID;
	}
	public void setSystemID(int systemID) {
		this.systemID = systemID;
	}
	
	public float getThrottle() {
		return throttle;
	}
	public void setThrottle(float throttle) {
		this.throttle = throttle;
	}
	
	public float getVerticalSpeed() {
		return verticalSpeed;
	}
	public void setVerticalSpeed(float verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
	}
	
	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public int getMavMode() {
		return MavMode;
	}
	public void setMavMode(int mavMode) {
		MavMode = mavMode;
	}
	
	public int getAutopilot() {
		return autopilot;
	}
	public void setAutopilot(int autopilot) {
		this.autopilot = autopilot;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
