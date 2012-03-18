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
	 
	
	public NetWork(JLabel jl,int port){
		this.port = port;
		Socket s1,s2;
		
		//Wait for the connections for the picture and command sockets
		try{
			ss = new ServerSocket(this.port);
			
			System.out.println("Waiting for connection");
			System.out.println("Setting up image connection");
				s1 = ss.accept();
				it = new ImageThread(s1,jl);
				ti = new Thread(it);
				ti.start();
			System.out.println("Done with image");
			System.out.println("Setting up command connection");
				s2 = ss.accept();
				ct = new CommandThread(s2);
				tt = new Thread(ct);
				tt.start();
			System.out.println("Done with command");
		} 
		catch (IOException ex){
			System.err.println(ex.getMessage());
		} 
	}
	public void sendCommand(EventRAT toSend){
		//Check so the CommandThread is created properly
		if(ct != null){
			ct.sendCommand(toSend);
		}
	}
	/**
	 * Closing the threads.
	 */
	public void closeAllThreads(){
		if(ct != null){
			ct.stopThread();
		}
		if(it != null){
			it.stopThread();
		}
	}
}
