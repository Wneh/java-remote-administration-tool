package master;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageThread extends Thread{
	
	private JLabel jl;
	private Socket s;
	private boolean run;	
	private InputStream in;
	private DataInputStream dis;
	private GUIMASTER parent;
	/*
	 * Will keep receiving images from the slave threw a socket connection
	 * and set the image to the ImageIcon that comes from the GUIMASTER class
	 */
	public ImageThread(Socket s,JLabel jl,GUIMASTER parent){
		this.s = s;
		this.jl = jl;
		this.parent = parent;
	}
	
	public void run(){
		run = true;
		byte[] imageByte;
		
		//Loop and update the incoming pictures for ever and ever until we say stop.
		try {	
			in = s.getInputStream();
		    dis = new DataInputStream(in);
		    
			
			while (run && ((imageByte = readBytes()) != null)){
				//Grab the picture and set it into the ImageIcon
				System.out.println("[INFO] - start picture");
				//Start converting the image
				InputStream inImage = new ByteArrayInputStream(imageByte);
				BufferedImage bi = ImageIO.read(inImage);
				//Create and ImageIcon from the BufferedImage and display it.
				jl.setIcon(new ImageIcon(bi));
				System.out.println("[INFO] - stop picture");
			}
		} catch (IOException e) {
			System.err.println("[ERROR] - IOException in reciveving the picture");
			//e.printStackTrace();
			parent.resetNetwork();
		}
		finally{
			stopThread();
		}
	}
	/**
	 * Help function that reads bytes from the DataInputStream
	 * First it reads an integer that represent how many bytes to read
	 * 
	 * @return The image in an byte array
	 * @throws IOException
	 */
	private byte[] readBytes() throws IOException {  
	    int len = dis.readInt();
	    byte[] data = new byte[len];
	    if (len > 0) {
	        dis.readFully(data);
	    }
	    return data;
	}
	
	/**
	 * Stops the thread by setting run = false;
	 * and closing all the streams and socket.
	 */
	public void stopThread(){
		run = false;
		//And try closing the streams and socket.
		try{
			in.close();
			dis.close();
			s.close();
		}catch (IOException e) {
			System.err.println("[ERROR] - Failed to close the streams in ImageThread");
			//parent.resetNetwork();
			//e.printStackTrace();
		}
	}
}
