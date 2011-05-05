package slave;

public class NetWork {
	
	private Thread threadImage;
	private Thread threadCommand;
	
	private ImageSender im;
	private CommandReceiver cr;
	
	public NetWork(){		
		
		im = new ImageSender(2000);
		cr = new CommandReceiver(2001);
		
		threadImage = new Thread(im);
		threadCommand = new Thread(cr);
		
		threadImage.start();
		threadCommand.start();	
	}
	public void closeAll(){
		im.closeAll();
		cr.closeAll();
	}
}
