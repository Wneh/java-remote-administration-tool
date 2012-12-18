package master;

public class GUIStatus {
	
	public GUIMASTER main;
	public char[] circle = {'-','\\','|','/','-','\\','|','/'};
	private RunningThread rt;
	private Thread t;
	public String STATUS_PREFIX = "R.A.T - ";
	
	
	public GUIStatus(GUIMASTER main){
		this.main = main;
		rt = new RunningThread(this);
		rt.startRun();
		t = new Thread(rt);
	}
	public void showWaitingForConnection(){
		t.start();
	}
	public void setStatus(String status){
		rt.stopRun();
		main.setTitle(STATUS_PREFIX + status);
	}
	
	/**
	 * This inner class is used for loopting the spinning circle while the master is wating for connections
	 *
	 */
	class RunningThread extends Thread{
		private boolean run;
		public GUIStatus parent;
		
		public RunningThread(GUIStatus gs){
			//Do nothing
			this.parent = gs;
		}
		public void startRun(){
			run = true;
		}
		public void stopRun(){
			run = false;
		}
		public void run(){
			int i = 0;
			while(run){
				//Set the new message to the GUIMASTERS titlerow
				parent.main.setTitle(STATUS_PREFIX + "Waiting for connection " + parent.circle[i]);
				//Increment the counter and check for out of bounds
				i++;
				if(i == circle.length){
					i = 0;
				}
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
