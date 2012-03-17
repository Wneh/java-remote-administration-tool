package master;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;
import netPack.EventRAT;
import netPack.PictureEventRAT;

public class ImageThread extends Thread{
	
	private ImageIcon ii;
	private Socket s;
	private boolean run;
	private ObjectInputStream oin;
	private ObjectOutputStream oout;
	
	/*
	 * Will keep receiving images from the slave threw a socket connection
	 * and set the image to the ImageIcon that comes from the GUIMASTER class
	 */
	public ImageThread(Socket s,ImageIcon ii){
		this.s = s;
		this.ii = ii;
		
		//Try setting up the streams
		try {
			oout = new ObjectOutputStream(s.getOutputStream());
			oin = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			System.err.println("[ERROR] - Error trying to setup the objects streams");
		}
	}
	
	public void run(){
		run = true;
		PictureEventRAT inputPic;
		
		//Loop and update the incoming pictures for ever and ever until we say stop.
		try {
			while (run && ((inputPic = (PictureEventRAT)oin.readObject()) != null)){
				//Grab the picture and set it into the ImageIcon
				ii = inputPic.getIi();
			}
		} catch (IOException e) {
			System.err.println("[ERROR] - IOException in reciveving the picture");
		} catch (ClassNotFoundException e) {
			System.err.println("[ERROR] - Uknown class reciveved");
		}
	}
}
