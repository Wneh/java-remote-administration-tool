package slave;

import netPack.*;

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
	
//	/**
//	 * Takes in an event and execute the correct action for it
//	 * @param eventIn Someone that we want the execute
//	 */
//	public void handleEvent(EventRAT eventIn){
//		if(eventIn instanceof KeyEventRAT){
//			//We got an keyevent
//			executeKey((KeyEventRAT)eventIn);
//		}
//		else if(eventIn instanceof MouseEventRAT){
//			//We got and mouseevent
//			executeMouse((MouseEventRAT)eventIn);
//		}
//		else{
//			//We have no idea what we got! Print it out 
//			System.out.println("Recieved an unknown package type");
//		}
//	}
//	/**
//	 * Private class the handle mouse events 
//	 * @param mer And object of MouseEventRAT class
//	 */
//	private void executeMouse(MouseEventRAT mer){
//		System.out.println("[INFO] - Got and MOUSE event");
//		//Check if the mouse was clicked
//		if(mer.isMouseClicked()){
//			//Left click
//			if(mer.getMc() == 1){
//				rs.clickLeft();
//			}
//			//Right click
//			else if(mer.getMc() == 3){
//				rs.clickRight();
//			}	
//		}
//		//Check if the mouse were moved
//		if(mer.isMouseMoved()){
//			System.out.println("[INFO] - Before moving mouse");
//			rs.moveMouse(mer.getX(),mer.getY());
//			System.out.println("[INFO] - After moving mouse");
//		}
//	}
//	/**
//	 * Private class that executes a key 
//	 * @param ker An object of KeyEventRAT class
//	 */
//	private void executeKey(KeyEventRAT ker){
//		System.out.println("[INFO] - Got and KEY event");
//		rs.typeKey(ker.getKeyCode());
//	}

}
