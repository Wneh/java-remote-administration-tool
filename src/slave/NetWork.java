package slave;
/**
 * Class thats handles the commandclass and the ImageSender
 * 
 * @author Carl
 *
 */
public class NetWork {
	
	private Thread threadImage;
	private Thread threadCommand;
	
	private ImageSender im;
	private CommandReceiver cr;
	
	/**
	 * Creates a new NetWork
	 * This will also starts each thread in the classes
	 */
	public NetWork(){		
		
		im = new ImageSender(2000);
		cr = new CommandReceiver(2001);
		
		threadImage = new Thread(im);
		threadCommand = new Thread(cr);
		
		threadImage.start();
		threadCommand.start();	
	}
	/**
	 * Calls the closeAll functions in CommandReciever and ImageSender
	 */
	public void closeAll(){
		im.closeAll();
		cr.closeAll();
	}
}
