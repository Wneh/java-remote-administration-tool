package master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import netPack.KeyEventRAT;
import netPack.MouseEventRAT;

public class NetWork implements Runnable{
	
	private Socket s;
	private ServerSocket ss;
	private int port;
	private boolean run;
	private Thread t;
	private ObjectInputStream oin = null;       
    private ObjectOutputStream oout = null;  
	
	
	public NetWork(){
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
	public void startRun(){
		t = new Thread(this);
		t.start();
	}
	public void run(){
		BufferedReader in = null;
		Object inputPackage;
		try {
			s = ss.accept();
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			//Loop forever and ever.
			while(run && ((inputPackage=(Object)oin.readObject()) != null )){
				//Check what kind of package we got
				if(inputPackage instanceof KeyEventRAT){
					//We got an keyevent

				}
				else if(inputPackage instanceof MouseEventRAT){
					//We got and mouseevent

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
