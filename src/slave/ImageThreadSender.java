package slave;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;

public class ImageThreadSender extends Thread {
	
	private String host;
	private int port;
	private Robot r;
	private Socket s;
	private boolean run;
	private OutputStream out;
	private DataOutputStream dos;
	
	public ImageThreadSender(String host, int port){
		this.host = host;
		this.port = port;	
		
		try {
			r = new Robot();
		} catch (AWTException e) {
			System.err.println("[ERROR] - Could not create the robot object");
		}
	}
	
	public void run(){
		run = true;
		
		try {
			s = new Socket(host,port);
			out = s.getOutputStream(); 
			dos = new DataOutputStream(out);
			
			while(run){
				//Keep sending pictures to the server as fast as possible
				System.out.println("[INFO] - Start sending picture");
				//Get the image
				byte[] ba = ByteImage();
				//Then send the image over the socket
				sendBytes(ba,0,ba.length);
				System.out.println("[INFO] - Done sending picture");
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Failed at UnknownHost");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("IOException failed");
		}
	}
	
	/**
	 * Creates a printecreen and return it in a BufferedImage
	 * 
	 * @return Screenshot of the computer screen in a BufferadImage
	 */
	private BufferedImage getScreen(){
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		BufferedImage bi = r.createScreenCapture(new Rectangle(size));
		return bi;
	}
	
	/**
	 * Takes a printscreen of the computer screen and stores it in a byte array
	 * 
	 * @return The image of the screen in a byte array
	 * @throws IOException Could not convert the image
	 */
	private byte[] ByteImage() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(this.getScreen(), "jpg", baos);
		baos.flush();
		return baos.toByteArray();
	}
	
	/**
	 * Send the byte array over the DataOutputStream
	 * Starts with sending the length of the byte array, continues with the array of bytes
	 * 
	 * @param myByteArray The bytearray to send
	 * @param start Startposition in the array
	 * @param len The length of the array
	 * @throws IOException If something went trying to send the array over the DataOutputStream
	 */
	private void sendBytes(byte[] myByteArray, int start, int len) throws IOException {
	    if (len < 0){
	        throw new IllegalArgumentException("Negative length not allowed");
	    }
	    if (start < 0 || start >= myByteArray.length){
	        throw new IndexOutOfBoundsException("Out of bounds: " + start);
	    }
	    
	    dos.writeInt(len);
	    if (len > 0) {
	        dos.write(myByteArray, start, len);
	    }
	}
	/**
	 * Stops the thread, streams and socket
	 */
	public void stopThread(){
		run = false;
		try {
			out.close();
			dos.close();
			s.close();
		} catch (IOException e) {
			System.err.println("[ERROR] - Failed to close the streams or socket in ImageThreadSender");
			e.printStackTrace();
		}
	}
}
