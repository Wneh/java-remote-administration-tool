package master;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import netPack.EventRAT;

public class CommandThread extends Thread{
	
	private Socket s;
	private boolean run;
	private ObjectInputStream oin;
	private ObjectOutputStream oout;
	private GUIMASTER parent;
	
	private DataOutputStream dos;
	private OutputStream out;
	
	public CommandThread(Socket s,GUIMASTER parent){
		this.parent = parent;
		this.s = s;
		
		//Try setting up the streams
		try {
			out = s.getOutputStream(); 
			dos = new DataOutputStream(out);
			
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
	 * Sends keyEvent to the slave to execute
	 * 
	 * @param keyCode The key code for the key that shall used
	 * @param keyState The state for the key(up or down)
	 */
	public void sendKeyEvent(int keyCode,byte keyState){
		try {
			//Start by sending what type of package see will send(Packet ID = 3 for keyEvent)
			dos.writeByte(3);
			//Continue sending the values
			dos.writeInt(keyCode);
			dos.writeByte(keyState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("[Error] - something went from trying to send keyEvent");
		}
	}
	/**
	 * Sends a mousemove request to the slave
	 * 
	 * @param x The X coordinate of the mouse
	 * @param y The Y coordinate of the mouse
	 */
	public void sendMouseMove(int x, int y){
		try {
			//Start by sending what type of package see will send(Packet ID = 1 for mouseMove)
			dos.writeByte(1);
			//Continue sending the values
			dos.writeInt(x);
			dos.writeInt(y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("[Error] - something went from trying to send mouse move");
		}
	}
	public void sendMouseClick(byte mouseKeyCode,byte mouseKeyState){
		try {
			//Start by sending what type of package see will send(Packet ID = 2 for mouse key click)
			dos.writeByte(2);
			//Continue sending the values
			dos.writeByte(mouseKeyCode);
			dos.writeByte(mouseKeyState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			//parent.resetNetwork();
		}
	}


}
