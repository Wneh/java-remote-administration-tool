package master;

/*
 * Cudos to Jakob Jenkov 
 * http://tutorials.jenkov.com/java-concurrency/locks.html
 */

public class Lock{

	private boolean isLocked = false;

	public synchronized void lock() throws InterruptedException{
		isLocked = true;
		while(isLocked){
			wait();
		}
	}

	public synchronized void unlock(){
		isLocked = false;
		notify();
	}
}
