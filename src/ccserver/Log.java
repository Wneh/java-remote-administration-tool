package ccserver;

/**
 * This class contains methods to easy printout information(either to console or to a .txt file)
 * from other parts of the program.
 * 
 * @author Carl
 *
 */

public class Log {

	
	public Log(){

	}
	public void printlnOut(String toPrint){
		System.out.println(toPrint);
	}
	public void printlnErr(String toPrint){
		System.err.println(toPrint);
	}
	
	/**
	 * More to come later on but for now I will only use the methods above 
	 */

}
