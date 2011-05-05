package master;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

/**
 * Use to send mouse and key event threw socket.
 * 
 * @author Carl
 *
 */
public class CommandSender{
	
	private Socket s;
	private PrintWriter out;
	
	/**
	 * Create a new connection. 
	 * @param host Ip adress or host name
	 * @param port 
	 */
	public CommandSender(String host, int port){		
		try {
			s = new Socket(InetAddress.getByName(host),port);
			out = new PrintWriter(s.getOutputStream(),true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Send a mouse move command with x,y coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void sendMouseMove(int x, int y){
		send("MV " + x + " " +y);
	}
	/**
	 * Send a mouse click
	 * 
	 * @param buttons Mousecode 
	 */
	public void sendMouseClick(int buttons){
		send("MC " + buttons);
	}
	/**
	 * Send a keyCode
	 * 
	 * @param keycode
	 */
	public void sendKeyPressed(int keycode){
		send("KP " + keycode);
	}
	/**
	 * Private function that is used by the public send functions
	 * 
	 * @param in
	 */
	private void send(String in){
		out.println(in);
	}
	/**
	 * Close all connections. 
	 * Use for clean exits
	 */
	public void closeAll(){
		try {
			s.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
