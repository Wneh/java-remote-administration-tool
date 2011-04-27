package master;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class CommandSender{
	
	private Socket s;
	private PrintWriter out;
	
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
	public void sendMouseMove(int x, int y){
		send("MV " + x + " " +y);
	}
	public void sendMouseClick(int buttons){
		send("MC " + buttons);
	}
	public void sendKeyPressed(int keycode){
		send("KP " + keycode);
	}
	public void send(String in){
		out.println(in);
	}
}
