package slave;

import netPack.*;

/**
 * Handles incoming package of the type from EvenRAT
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
	/**
	 * Takes in an event and execute the correct action for it
	 * @param eventIn Someone that we want the execute
	 */
	public void handleEvent(EventRAT eventIn){
		if(eventIn instanceof KeyEventRAT){
			//We got an keyevent
			executeKey((KeyEventRAT)eventIn);
		}
		else if(eventIn instanceof MouseEventRAT){
			//We got and mouseevent
			executeMouse((MouseEventRAT)eventIn);
		}
		else{
			//We have no idea what we got! Print it out 
			System.out.println("Recieved an unknown package type");
		}
	}
	/**
	 * Private class the handle mouse events 
	 * @param mer And object of MouseEventRAT class
	 */
	private void executeMouse(MouseEventRAT mer){
		System.out.println("[INFO] - Got and MOUSE event");
		//Check if the mouse was clicked
		if(mer.isMouseClicked()){
			//Left click
			if(mer.getMc() == 1){
				rs.clickLeft();
			}
			//Right click
			else if(mer.getMc() == 3){
				rs.clickRight();
			}	
		}
		//Check if the mouse were moved
		if(mer.isMouseMoved()){
			System.out.println("[INFO] - Before moving mouse");
			rs.moveMouse(mer.getX(),mer.getY());
			System.out.println("[INFO] - After moving mouse");
		}
	}
	/**
	 * Private class that executes a key 
	 * @param ker An object of KeyEventRAT class
	 */
	private void executeKey(KeyEventRAT ker){
		System.out.println("[INFO] - Got and KEY event");
		rs.typeKey(ker.getKeyCode());
	}

}
