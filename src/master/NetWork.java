package master;

import java.io.IOException;
import java.net.*;
import javax.swing.JLabel;
import netPack.EventRAT;

public class NetWork{
	
	private ServerSocket ss;
	private int port;
	private ImageThread it;
	private CommandThread ct;
	private Thread ti;
	private Thread tt;
	public boolean CONNECTED;
	private GUIMASTER parent;
	 
	
	public NetWork(JLabel jl,int port,GUIMASTER parent){
		CONNECTED = false;
		this.port = port;
		this.parent = parent;
		Socket s1,s2;
		
		//Wait for the connections for the picture and command sockets
		try{
			parent.guis.showWaitingForConnection();
			ss = new ServerSocket(this.port);
			
			System.out.println("Waiting for connection");
			System.out.println("Setting up image connection");
				s1 = ss.accept();
				it = new ImageThread(s1,jl,parent);
				ti = new Thread(it);
				ti.start();
			System.out.println("Done with image");
			System.out.println("Setting up command connection");
				s2 = ss.accept();
				ct = new CommandThread(s2,parent);
				tt = new Thread(ct);
				tt.start();
			System.out.println("Done with command");
			parent.guis.setStatus("Connected to " + s1.getInetAddress());
			CONNECTED = true;
		} 
		catch (IOException ex){
			System.err.println(ex.getMessage());
		} 
	}
	public void sendCommand(EventRAT toSend){
		//Check so the CommandThread is created properly and we are CONNECTED
		if(ct != null && CONNECTED){
			ct.sendCommand(toSend);
		}
	}
	/**
	 * Closing the threads.
	 */
	public void closeAllThreads(){
		//Close the threads
		if(ct != null){
			ct.stopThread();
		}
		if(it != null){
			it.stopThread();
		}
		//And set that we are not connected
		CONNECTED = false;
	}
}
