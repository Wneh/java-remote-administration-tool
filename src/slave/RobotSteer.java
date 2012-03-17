package slave;


import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.InputEvent;
/**
 * Help class that use the java Robot class
 * @author Carl
 */
public class RobotSteer{
	
	private Robot rBot;
	
	/**
	 * Creates a new Robot
	 */
	public RobotSteer(){
		try{
			rBot = new Robot();
		}
		 catch (AWTException e) { 
			 System.err.println("[ERROR] - Could not create the robot object");
			 e.printStackTrace(); 
		 } 
	}
	/**
	 * Left click on mouse
	 */
	public void clickLeft(){
		rBot.mousePress(InputEvent.BUTTON1_MASK);
		rBot.delay(100);
		rBot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	/**
	 * Right click the mouse
	 */
	public void clickRight(){
		rBot.mousePress(InputEvent.BUTTON3_MASK);
		rBot.delay(100);
		rBot.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	/**
	 * Moves the mouse the x and y.
	 * @param x
	 * @param y
	 */
	public void moveMouse(int x, int y){
		System.out.println("[INFO] - Moving mouse to X: "+x+" Y: "+y);
		rBot.mouseMove(x, y);
	}
	/**
	 * Simulate a keypress
	 * @param keyCode
	 */
	public void typeKey(int keyCode){
		rBot.keyPress(keyCode);
		rBot.delay(50);
		rBot.keyRelease(keyCode);
	}
}
