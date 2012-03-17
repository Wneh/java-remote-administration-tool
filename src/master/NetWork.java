package master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import netPack.EventRAT;
import netPack.KeyEventRAT;
import netPack.MouseEventRAT;
import netPack.PictureEventRAT;

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
			ss = new ServerSocket(port);
			
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
	
	
/*	public void run(){
		
		
		EventRAT inputPackage;
		try {
			System.out.println("Waiting for connection");
			s = ss.accept();
			System.out.println("Connection establish");
			
			System.out.println("[INFO] - Creating input and output streams");
			oout = new ObjectOutputStream(s.getOutputStream());
            oin = new ObjectInputStream(s.getInputStream());
            System.out.println("[INFO] - Done creating input and output streams");
            
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try{
			//Loop forever and ever.
			while(run && ((inputPackage=(EventRAT)oin.readObject()) != null )){
				System.out.println("[INFO] - Inside the loop");
				//Check what kind of package we got
				if(inputPackage instanceof KeyEventRAT){
					//We got an keyevent
					//Do nothing cause we're the one to be in control
				}
				else if(inputPackage instanceof MouseEventRAT){
					//We got and mouseevent
					//Do nothing cause we're the one to be in control
				}
				else if(inputPackage instanceof PictureEventRAT){
					image = ((PictureEventRAT) inputPackage).getIi();
					System.out.println("[INFO] - Got and picture");
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
				//in.close();
				oout.close();
				oin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

}
