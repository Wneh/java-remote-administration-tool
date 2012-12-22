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
	private GUIMASTER parent;
	
	public CommandThread(Socket s,GUIMASTER parent){
		this.parent = parent;
		this.s = s;
		
		//Try setting up the streams
		try {
			oout = new ObjectOutputStream(s.getOutputStream());			
			oin = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			System.err.println("[ERROR] - Error trying to setup the objects streams");
			parent.resetNetwork();
		}	
	}
	public void run(){
		run = true;
		
		//Since we don't need to recieve commands we just do a dummy loop
		while(run){
			//Do nothing atm.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				//e.printStackTrace();
				//Something went wrong to reset and wait for the connection again.
				parent.resetNetwork();
			}
			finally{
				this.stopThread();
			}
		}
	}
	/**
	 * Stops the thread by setting run = false;
	 * and closing all the streams and socket.
	 */
	public void stopThread(){
		run = false;
		
		try {
			oout.flush();
			oout.close();
			oin.close();
			s.close();
		} catch (IOException e) {
			System.err.println("[ERROR] - Failed to close the streams in CommandThread");
			//e.printStackTrace();
			parent.resetNetwork();
		}
	}


}
