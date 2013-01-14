package ccserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread {
	
	public Log log;
	private int port;
	private ServerSocket ss;
	private boolean running;
	
	int idCounter;
	
	private ArrayList<ConnectionThread> connections;
	
	public Server(){
		this.port = 1999;
	}
	public Server(int port){
		this.port = port;
	}
	
	public void run(){
		//Star with setting everything up
		initializeServer();
		
		//Keep waiting for connection until something say not to by setting running to false
		log.printlnOut("Waiting for connection...");
		while(running){
			try {
				ConnectionThread ct = new ConnectionThread(ss.accept(),idCounter,this);
				idCounter++;
				connections.add(ct);
				ct.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Before the thread dies we need to close everything
		stopServer();
		
		//RIP this.Thread		
	}
	
	/**
	 * Use this make everything ready for receiving connections
	 */
	public void initializeServer(){
		//Create a new logger to use
		log = new Log();
		idCounter = 1;
		connections = new ArrayList<ConnectionThread>();
		
		log.printlnOut("Starting server");
		
		//Try to make a server socket
		try {
			ss = new ServerSocket(port);
			running = true;
		} catch (IOException e) {
			log.printlnErr("Could not start ServerSocket. Is the port allready in use?");
			running = false;
		}
		
		log.printlnOut("Server started!");
	}
	
	/**
	 * Use this to close everything properly
	 */
	public void stopServer(){
		// TODO 
	}
	
	public static void main(String[] args) {
		new Thread(new Server()).start();
	}
}
