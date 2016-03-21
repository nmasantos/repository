package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.mavlink.messages.MAVLinkMessage;
import org.mavlink.messages.MAV_AUTOPILOT;
import org.mavlink.messages.MAV_CMD;
import org.mavlink.messages.MAV_COMPONENT;
import org.mavlink.messages.MAV_MODE;
import org.mavlink.messages.MAV_MODE_FLAG;
import org.mavlink.messages.MAV_STATE;
import org.mavlink.messages.MAV_TYPE;
import org.mavlink.messages.ardupilotmega.msg_command_long;
import org.mavlink.messages.ardupilotmega.msg_heartbeat;
import org.mavlink.messages.ardupilotmega.msg_set_mode;

public class UDPClient implements Runnable{

	
	      DatagramSocket clientSocket;
	      private int rpiPort;
	      private InetAddress clientAddress;
	      
	      private int sequence = 0;
	      
	      public UDPClient(int rpiPort) {
	    	  this.rpiPort = rpiPort;
	      }
	      
	      @Override
	      public void run() {
	    	 
	    	try { 

	    	 clientAddress = InetAddress.getByName("192.168.2.108");
	    	 
	    	 clientSocket = new DatagramSocket();
	    	 
	    	 System.out.println("client sendin");
	//    	while(true){	
	    		
//			  msg_heartbeat hb = new msg_heartbeat(1,1);
//			  hb.sequence = sequence++;
//			  hb.messageType = hb.MAVLINK_MSG_ID_HEARTBEAT;
//			  hb.autopilot = MAV_AUTOPILOT.MAV_AUTOPILOT_ARDUPILOTMEGA;
//			  hb.base_mode = MAV_MODE.MAV_MODE_STABILIZE_ARMED;
//			  hb.system_status = MAV_STATE.MAV_STATE_POWEROFF;
//			  hb.type = MAV_TYPE.MAV_TYPE_HEXAROTOR;
//			  hb.mavlink_version = hb.MAVPROT_PACKET_START_V10;
	                
	    	
	    	 msg_command_long msg_command_long = new msg_command_long();
	         msg_command_long.target_system = 1;
	         msg_command_long.target_component = MAV_COMPONENT.MAV_COMP_ID_SYSTEM_CONTROL;
	         msg_command_long.command = MAV_CMD.MAV_CMD_COMPONENT_ARM_DISARM;
	         msg_command_long.confirmation = 0;
	         msg_command_long.param1 = 1;
	         byte[] mavMsg = new byte[msg_command_long.length];
	         
	         mavMsg = msg_command_long.encode();
	         
	    	
			
			  System.out.println("SENT: " + msg_command_long.toString());
			  DatagramPacket sendPacket = new DatagramPacket(mavMsg, mavMsg.length, clientAddress, rpiPort);
			
		      clientSocket.send(sendPacket);
		      
		      	 msg_command_long msg_command_long2 = new msg_command_long();
		         msg_command_long2.target_system = 1;
		         msg_command_long2.target_component = MAV_COMPONENT.MAV_COMP_ID_SYSTEM_CONTROL;
		         msg_command_long2.command = MAV_CMD.MAV_CMD_DO_MOTOR_TEST;
		         msg_command_long2.confirmation = 0;
		         msg_command_long2.param1 = 2;
		         msg_command_long2.param2 = 0;
		         msg_command_long2.param3 = 100;
		         msg_command_long2.param4 = 2;
		         byte[] mavMsg2 = new byte[msg_command_long2.length];
		         
		         mavMsg2 = msg_command_long2.encode();
		         
		         DatagramPacket sendPacket2 = new DatagramPacket(mavMsg2, mavMsg2.length, clientAddress, rpiPort);
					
			      clientSocket.send(sendPacket2);
			      Thread.sleep(1000);
		         
		         
	    //	}
	    	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	         
	      
	      }
    }

