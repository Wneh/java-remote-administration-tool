package slave.remoteControl;

public class RemoteHandler {
	
	private String host;
	private int port;
	private ImageThreadSender its;
	private CommandThreadReceiver ctr;
	private Thread it;
	private Thread ct;

	
	/**
	 * Creates a instance that are ready to connect to the master
	 * 
	 * 
	 * @param host The IP to the master
	 * @param port The port the serversocket are listening on (default 2000)
	 */
	public RemoteHandler(String host, int port){
		this.setHost(host);
		this.setPort(port);
		
	}
	/**
	 * Initialize the two threads with the values from host and port
	 * 
	 */
	public void initializeThreads(){
		its = new ImageThreadSender(host,port);
		ctr = new CommandThreadReceiver(host,port);
		
		it = new Thread(its);
		ct = new Thread(ctr);	
	}
	/**
	 * Starts the threads.
	 * Note: initializeThreads must have been called before using this method.
	 */
	public void startThreads(){
		if(ct != null && it != null){
			it.start();
			ct.start();
		}
	}
	/**
	 * Stops the threads.
	 * Note: To start the threads again after stopping the
	 * 		 initializeThreads and startThreads must be called.
	 */
	public void stopThreads(){
		if(ct != null && it != null){
			its.stopThread();
			ctr.stopThread();
			
			ct = null;
			it = null;
		}
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

}
