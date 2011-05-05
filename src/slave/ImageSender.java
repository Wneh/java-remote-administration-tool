package slave;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;

/**
 * Creates a printscreen and send's it over a TCP
 * 
 * @author Carl
 *
 */
public class ImageSender extends Thread{
	
	private Thread t;
	private Boolean run;
	private Robot r;
	private ServerSocket ss;
	private Socket s;
	
	/**
	 * Creates a new ImageSender
	 * @param port
	 */
	public ImageSender(int port){
		run = true;
		try {
			ss = new ServerSocket(port);		
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Starts the thread
	 * (Used from the contructor, not implementet at the moment)
	 */
	public void startSending() {
        t = new Thread( this );
        run = true;
        t.start();
    }
	/**
	 * Run the thread and keeps sending printscreen over the socket.
	 */
	public void run(){
		try {
			s = ss.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(run){
			try{
				OutputStream os = s.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(getScreen());
			}
			catch(SocketTimeoutException x ) {
				//no host, but try first to send it again
            }
            catch(Exception x ) {
                x.printStackTrace();
            }			
		}
	}
	/**
	 * Creates a printecreen and return it in a ImageIcon
	 * @return
	 */
	private ImageIcon getScreen(){
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		BufferedImage bi = r.createScreenCapture(new Rectangle(size));
		return new ImageIcon(bi);
	}
	/**
	 * Closes all the streams and sockets connections
	 */
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
