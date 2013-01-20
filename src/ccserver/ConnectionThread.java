package ccserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionThread extends Thread {
	
	private Socket s;
	private int ccserverId;
	private Server parent;
	
	private boolean running;
	private boolean master;
	private boolean inUse;
	private String computerName;
	
	private OutputStream out;
	private DataOutputStream dos;
	private InputStream in;
	private DataInputStream dis;
		
	public ConnectionThread(Socket s,int ccserverId, Server parent){
		this.s = s;
		this.ccserverId = ccserverId;
		this.parent = parent;
		
		//Least privilege rule
		this.running = false;
		this.master = false;
		this.inUse = false;
		
		//Create all the streams from the socket.
		try {
			out = s.getOutputStream();
			dos = new DataOutputStream(out);
			
			in = s.getInputStream();
			dis = new DataInputStream(in);
		} catch (IOException e) {
			parent.log.printlnErr("Something went wrong trying to setup input/output streams");
			e.printStackTrace();
		}	
	}
	
	public void run(){
		running = true;
		byte packageID;
		
		try {
			
			running = doHandShake();
			
			parent.log.printlnOut(computerName + " connected to the server");
			
			while(running && ((packageID = dis.readByte()) != -1)){
				
				switch(packageID){
					//Case 1 reserved for handshake
					//Heartbeat
					case 2:
						// TODO Heartbeat functionality
						break;
					//Ask a slave to connect to a master	
					case 3:
						//Get the slaves ID
						
						//Get the masters ip
						
						//Get the masters port
						
						//Send to slave to connect
						break;
					//Get the list of all slaves connected
					case 4:
						
						break;
				}
				
			}
		} catch (IOException e) {
			parent.log.printlnErr("IO exception from thread #"+ccserverId);
			e.printStackTrace();
		}
	}
	/**
	 * Sends out an array containing information about all the connected to the ccserver
	 */
	private void sendConnectionList(){
		//Setup some stuff
		ArrayList<ConnectionThread> currentConnections = parent.getConnectionThread();
		ConnectionThread tempConnection;
		int size = currentConnections.size();
		try {
			//Send packageID
			dos.writeByte(4);
			//Start sending to master how many entry's we will send
			dos.writeInt(size);
			for(int i = 0; i < size; i++){
				tempConnection = currentConnections.get(i);
				//Send id
				dos.writeInt(tempConnection.getccserverId());
				//Send name
				writeString(tempConnection.computerName);
				//Send if in used
				dos.writeBoolean(tempConnection.isInUse());
				//Send if thread is a master
				dos.writeBoolean(tempConnection.isMaster());
			}
		} catch (IOException e) {
			parent.log.printlnErr("IO exception from thread #"+ccserverId + "@sendConnectionList");
			e.printStackTrace();
		}
	}
	
	/**
	 * This will ask this thread´s client to connect to the IP
	 * This method is called from another ConnectionThread that is a master
	 * 
	 * @param ip IP-address to the master
	 * @param port Port that master is listening on
	 * @return True if the thread is not busy
	 */
	public boolean connectSlaveToMaster(String ip,int port){
		//Check so that the thread is still alive and it not a master and that it not allready in use
		if(running && !master && !inUse){
			//Set that this thread is in use
			inUse = true;			
			//Now send a request to the slave to connect to the master
				
			try {
				//PackageID = 10
				dos.writeByte(10);
				//Send the ip
				writeString(ip);
				//Send the port number
				dos.writeInt(port);
			} catch (IOException e) {
				parent.log.printlnErr("Something went wrong trying to setup input/output streams");
				e.printStackTrace();
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * This handshake is very similar to the TCP 3-way handshake
	 * A more detailed description about it will probably be added
	 * to the wiki
	 * 
	 * @return True or False depending how the handshake went
	 */
	private boolean doHandShake(){
		byte packageID;
		byte content;
	
		//First we should get a package from the client saying like "Hello there"
		try {
			packageID = dis.readByte();
			content = dis.readByte();
			//PackageID shall have value 1
			if(packageID == 1 && content == 1){
				//Everything okey so far so answer the request with packageID = and content =2
				//Send packageID
				dos.writeByte(1);
				//Send content
				dos.writeByte(2);
				
				//Now we should get a response with packageID = 1 and a String with the name of the computer
				packageID = dis.readByte();
				computerName = readString();
				
				//PackageID shall have value 1 and the compuerName shall not be null anymore
				if(packageID == 1 && computerName != null){
					//Everything okey so return true
					return true;
				}
			}
		} catch (IOException e) {
			parent.log.printlnErr("I/O Exception while performing handshake");
			e.printStackTrace();
		}
		return false;
	}
	private void writeString(String toWrite){
		try {
			byte[] data = toWrite.getBytes("UTF-8");
			dos.writeInt(data.length);
			dos.write(data);
		} catch (UnsupportedEncodingException e) {
			parent.log.printlnErr("Wrong enconding on String in writeString()");
			e.printStackTrace();
		} catch (IOException e) {
			parent.log.printlnErr("IO exception trying to send string over DataOutputStream");
			e.printStackTrace();
		}
	}
	private String readString(){
		try {
			int length = dis.readInt();
			byte data[] = new byte[length];
			dis.readFully(data);
			return new String(data,"UTF-8");
		} catch (IOException e) {
			parent.log.printlnErr("IO exception trying to read string from DataInputStream");
			e.printStackTrace();
			return null;
		}
	}

	public int getccserverId(){
		return ccserverId;
	}

	public void setId(int id) {
		this.ccserverId = id;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

}
