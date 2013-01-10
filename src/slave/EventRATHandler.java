package slave;

/**
 * Handles incoming package of the type from EvenRAT
 * 
 * NOTE:	During the rewrite of the code this class might seems to be unnecessary
 * 			but more things might be added in the future. Like method that up on connection
 * 			lost release all the pushed down buttons.
 * 
 * @author Carl
 *
 */

public class EventRATHandler {
	
	private RobotSteer rs;
	
	public EventRATHandler(){
		//Setup the robot class
		rs = new RobotSteer();
	}
	public void executeMouseMove(int x, int y){
		System.out.println("[INFO] - Before moving mouse");
		rs.moveMouse(x,y);
		System.out.println("[INFO] - After moving mouse");
	}
	public void executeMouseClick(byte mouseKeyCode, byte mouseKeyState){
		rs.clickMouse(mouseKeyCode, mouseKeyState);
	}
	public void executeKey(int keyCode,byte keyState){
		rs.clickKey(keyCode, keyState);
	}
}
