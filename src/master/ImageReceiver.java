package master;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Extends a jlabel and addes a socket functions the the label pushes pictures 
 * printscreens from the slave.
 * 
 * @author Carl
 */

public class ImageReceiver extends JLabel implements Runnable, Serializable{
	/**
	 * To make eclipse stfu ;)
	 */
	private static final long serialVersionUID = 4973476970776748360L;
	private boolean run;
	private Thread t;
	private Socket s;

	/**
	 * Creates a new ImageReciver
	 * 
	 * @param host
	 * @param port
	 */
	public ImageReceiver(String host,int port){
		try {
			s = new Socket(InetAddress.getByName(host),port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		run = false;
		startRun();
	}
	/**
	 * Starts the thread. This one is called from the constuctor by default
	 */
	public void startRun(){
		t = new Thread( this );
        run= true;
        t.start();		
	}
	
	/**
	 * The threads that keeps receiving printscreens from the slave.
	 */
	public void run(){
		while(run){
			try {
				InputStream is = s.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				ImageIcon image = (ImageIcon)ois.readObject();
				setIcon(image);	
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}	
		}
	}
	/**
	 * Closes all the streams and sockets 
	 * Use for a clean exit.
	 */
	public void closeAll(){
		run = false;
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
