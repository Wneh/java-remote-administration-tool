package slave;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.InputEvent;

public class RobotSteer{
	
	private Robot rBot;
	
	public RobotSteer(){
		try{
			rBot = new Robot();
		}
		 catch (AWTException e) { 
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
	public void moveMouse(int x, int y){
		rBot.mouseMove(x, y);
	}
	public void typeKey(int keyCode){
		rBot.keyPress(keyCode);
		rBot.delay(50);
		rBot.keyRelease(keyCode);
	}
}
