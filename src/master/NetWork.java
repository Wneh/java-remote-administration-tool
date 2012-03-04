package master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.swing.ImageIcon;

import netPack.EventRAT;
import netPack.KeyEventRAT;
import netPack.MouseEventRAT;
import netPack.PictureEventRAT;

public class NetWork implements Runnable{
	
	private Socket s;
	private ServerSocket ss;
	private int port;
	private boolean run;
	private Thread t;
	private ObjectInputStream oin = null;       
    private ObjectOutputStream oout = null; 
    
    private ImageIcon image;
	
	
	public NetWork(ImageIcon image){
		this.image = image;
		run = false;
	}
	/**
	 * Setup the serversocket and make it ready to receive connections
	 */
	public void startServer(){
		try{
			ss = new ServerSocket(port);
			run = true;
		} 
		catch (IOException ex){
			System.err.println(ex.getMessage());
			run = false;
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
			} 
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	public void startRun(){
		t = new Thread(this);
		t.start();
	}
	public void run(){
		BufferedReader in = null;
		EventRAT inputPackage;
		try {
			System.out.println("Waiting for connection");
			s = ss.accept();
			System.out.println("Connection establish");
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			//Loop forever and ever.
			while(run && ((inputPackage=(EventRAT)oin.readObject()) != null )){
				//Check what kind of package we got
				if(inputPackage instanceof KeyEventRAT){
					//We got an keyevent

				}
				else if(inputPackage instanceof MouseEventRAT){
					//We got and mouseevent

				}
				else if(inputPackage instanceof PictureEventRAT){
					image = ((PictureEventRAT) inputPackage).getIi();
					//Now do somehthing with the picture that just we got
				}
				else{
					//We have no idea what we got! Print it out 
					System.out.println("Recieved an unknown package type");
				}
			}
		}
		catch(IOException e){
			System.out.println("Read failed");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class not found failed");
			e.printStackTrace();
		}
		//Finally close all the stuff that we opened
		finally{
			try{
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
