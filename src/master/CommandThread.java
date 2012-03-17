package master;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import netPack.EventRAT;

public class CommandThread extends Thread{
	
	private Socket s;
	private boolean run;
	private ObjectInputStream oin;
	private ObjectOutputStream oout;
	
	public CommandThread(Socket s){

		this.s = s;
		
		//Try setting up the streams
		try {
			oout = new ObjectOutputStream(s.getOutputStream());			
			oin = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			System.err.println("[ERROR] - Error trying to setup the objects streams");
		}	
	}
	public void run(){
		run = true;
		
		//Since we don't need to recieve commands we just do a dummy loop
		while(run){
			//Do nothing atm.
		}
	}
	/**
	 * Send a object of KeyEventRAT/MouseEventRAT to the slave to execute
	 * @param toSend Instance of KeyEventRAT/MouseEventRAT
	 */
	public void sendCommand(EventRAT toSend){
		//Fast check that either the keyevent is null or the outputstream is
		if(toSend != null && oout != null){
			try{
				//Sends it out threw the outputsteam
				oout.writeObject(toSend);
				oout.flush();
			} 
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}


}
