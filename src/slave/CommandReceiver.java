package slave;

import java.net.*;
import java.io.*;

public class CommandReceiver implements Runnable{
	
	private Socket s;
	private ServerSocket ss;
	private Thread t;
	private boolean run;
	private RobotSteer rs;
	
	public CommandReceiver(int port){
		try {
			ss = new ServerSocket(port);
			run = true;
			rs = new RobotSteer();			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void startRun(){
		t = new Thread( this );
        run = true;
        t.start();		
	}
	public void run(){
		BufferedReader in = null;
		try {
			s = ss.accept();
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(run){
			try{
				String line = in.readLine();
				System.out.println("Fick in: " + line);
				handleCommand(line);
			}
			catch(IOException e){
				System.out.println("Read failed");
			}
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void handleCommand(String raw){
		String[] temp = raw.split(" ");
				
		if(temp[0].equals("MV")){
			if(temp.length == 3){
				System.out.println("Kom till in MV satsen");
				int x = Integer.parseInt(temp[1]);
				int y = Integer.parseInt(temp[2]);
				rs.moveMouse(x, y);
				System.out.println("X: "+x);
				System.out.println("Y: "+y);
			}
		}
		//MC = Mouse click
		else if(temp[0].equals("MC")){
			//Left click
			if(temp[1].equals("1")){
				rs.clickLeft();
			}
			//Right click
			else if(temp[1].equals("3")){
				rs.clickRight();
			}
		}
		else if(temp[0].equals("KP")){
			rs.typeKey(Integer.parseInt(temp[1]));
		}
		else{
			System.out.println("lol fick else satsen aktiverad");
		}
	}
	public void closeAll(){
		run = false;
		try {
			ss.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
