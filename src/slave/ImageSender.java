package slave;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;


public class ImageSender extends Thread{
	
	private Thread t;
	private Boolean run;
	private Robot r;
	private ServerSocket ss;
	private Socket s;
	
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
	public void startSending() {
        t = new Thread( this );
        run = true;
        t.start();
    }
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
	private ImageIcon getScreen(){
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		BufferedImage bi = r.createScreenCapture(new Rectangle(size));
		return new ImageIcon(bi);
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
