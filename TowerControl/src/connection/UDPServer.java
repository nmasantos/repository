package connection;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.mavlink.IMAVLinkMessage;
import org.mavlink.MAVLinkReader;
import org.mavlink.io.LittleEndianDataInputStream;
import org.mavlink.messages.MAVLINK_DATA_STREAM_TYPE;
import org.mavlink.messages.MAVLinkMessage;
import org.mavlink.messages.ardupilotmega.msg_heartbeat;

import drone.Drone;
import screens.GCSGuiController;

public class UDPServer extends Thread{

	private DatagramSocket serverSocket;
	private int SERVERPORT = 14550;
	private String SERVERIP = "localhost";
    private byte[] receiveData = new byte[263];
    private MessageHandler msgHandler;
	private ArrayList<Drone> dronesList = new ArrayList<Drone>();
	
    private GCSGuiController gcsGuiController;
    
    //Latest constructor
    public UDPServer(GCSGuiController gcsGuiController){
    	this.gcsGuiController = gcsGuiController;
    }
    
    // Previous constructor
    public UDPServer(Drone drone, GCSGuiController gcsGuiController){
    	msgHandler = new MessageHandler(drone, gcsGuiController);
    	this.gcsGuiController = gcsGuiController;
    }
    /*
    public void startServer(){
    	
		try {
			
			InetAddress serverAddress = InetAddress.getByName(SERVERIP);
			serverSocket = new DatagramSocket(SERVERPORT);
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			boolean running = true;

			System.out.println("Server up and runnin");
			int i = 0;
			while(running){
				//Socket receives byte Packets

				serverSocket.receive(receivePacket);
				msgHandler = new MessageHandler(new Drone(1), gcsGuiController);
				MultipleDroneHandler handler = new MultipleDroneHandler(serverSocket, receivePacket, msgHandler);
				handler.start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    */
 // Before creating the Multiple Drone Handler; 
	@Override
	public void run() {
		 try {
			
			 //Creates the server adress
			InetAddress serverAddress = InetAddress.getByName(SERVERIP);
			
			//Open the UDP Port
			serverSocket = new DatagramSocket(SERVERPORT);
			
			//Creating a Packet to receive the data
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			ByteArrayInputStream bais;
            DataInputStream dis;
			
			boolean running = true;

			System.out.println("Server up and runnin");
			int i = 0;
			while(running){
				//Socket receives byte Packets

				serverSocket.receive(receivePacket);

				
				receiveData = receivePacket.getData();
				
				
				bais = new ByteArrayInputStream(receiveData);
				dis = new DataInputStream(bais);
				
				
				MAVLinkReader MAVReader = new MAVLinkReader(dis);
				
				MAVLinkMessage msg = MAVReader.getNextMessage();
					if(msg != null ){
						addDrone(msg);
						msgHandler.getMessage(msg);
						System.out.println("SysID:" + msg.sysId + "		CompID:" + msg.componentId + "		Seq:" + msg.sequence + "	" +msg.toString() + " HEY "+dronesList.size());
				}
				dis.close();
				i++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	
	}
	
	public void addDrone(MAVLinkMessage msg){
		boolean exists = false;
		if(dronesList.isEmpty()){
			dronesList.add(new Drone(msg.sysId));
			gcsGuiController.updateDronesList(dronesList);
		} else {
		for(Drone drone : dronesList){
			 if(drone.getSystemID() == msg.sysId)
				exists = true;
		}
		if(!exists){
			dronesList.add(new Drone(msg.sysId));
			gcsGuiController.updateDronesList(dronesList);
		}
	}
	}
    
    /*
    private class MultipleDroneHandler extends Thread {

    	private DatagramSocket droneSocket;
    	private byte[] receiveData = new byte[263];
    	private MessageHandler msgHandler;
    	private DatagramPacket receivePacket;
    	
    	
    	public MultipleDroneHandler(DatagramSocket droneSocket, DatagramPacket receivePacket, MessageHandler msgHandler){
    		this.droneSocket = droneSocket;
    		this.receivePacket = receivePacket;
    		this.msgHandler = msgHandler;
    	}
    	
    	@Override
    	public void run() {
    		
    		ByteArrayInputStream bais;
            DataInputStream dis;
    		
    		try {
    			
    			receiveData = receivePacket.getData();
    			
    			
    			bais = new ByteArrayInputStream(receiveData);
    			dis = new DataInputStream(bais);
    			
    			
    			MAVLinkReader MAVReader = new MAVLinkReader(dis);
    			
    			MAVLinkMessage msg = MAVReader.getNextMessage();
    				if(msg != null ){
    					addDrone(msg);
    					msgHandler.getMessage(msg);
    					System.out.println("SysID:" + msg.sysId + "		CompID:" + msg.componentId + "		Seq:" + msg.sequence + "	" +msg.toString() + " HEY " + dronesList.size());
    			}
    			dis.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	public void addDrone(MAVLinkMessage msg){
    		boolean exists = false;
			if(dronesList.isEmpty())
				dronesList.add(new Drone(msg.sysId));
			else {
			for(int i = 0; i!= dronesList.size(); i++){
				 if(dronesList.get(i).getSystemID() == msg.sysId)
					exists = true;
			}
			if(!exists)
				dronesList.add(new Drone(msg.sysId));
		}
    	}
    }
    */

}

