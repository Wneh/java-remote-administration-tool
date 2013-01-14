package slave;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommandThreadReceiver extends Thread {
	
	private String host;
	private int port;
	private boolean run;
	private Socket s;
	private EventRATHandler erh;
	
	private InputStream in;
	private DataInputStream dis;
	
	public CommandThreadReceiver(String host, int port){
		this.host = host;
		this.port = port;
		
		erh = new EventRATHandler();
	}
	public void run(){
		run = true;
		byte packageID;
		
		try {
			s = new Socket(host,port);
			
			in = s.getInputStream();
		    dis = new DataInputStream(in);		    

			//Keep running until run sets to false or we read null from the socket.
		    while(run && ((packageID = dis.readByte()) != -1)){
				//And now handle the event that got
				System.out.println("[INFO] - start command");
				
				/**
				 * The switch will continue reading i"nput from the socket.
				 * Depending on which kind of package what send we read different primitive types
				 * 
				 * For more information see the project wikipage:
				 * http://code.google.com/p/java-remote-administration-tool/wiki/Protocol 
				 */
				switch(packageID){
					//Mouse move
					case 1:
						//Read to integer's for X and Y position
						erh.executeMouseMove(dis.readInt(),dis.readInt());
						break;
					//Click mouse
					case 2:
						erh.executeMouseClick(dis.readByte(), dis.readByte());
						break;
					//Keyboard event
					case 3:
						erh.executeKey(dis.readInt(), dis.readByte());
						break;
					default:
						System.err.println("[ERROR] - Recieved unknow packageID");
						break;						
				}	
				System.out.println("[INFO] - stop command");
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Stops the thread, streams and socket
	 */
	public void stopThread(){
		run = false;
		try {
			in.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			System.err.println("[ERROR] - Failed to close the streams or socket in CommandThreadReceiver");
			e.printStackTrace();
		}
	}

}
