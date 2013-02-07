package master;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIMaster {
	
	public CLIMaster(){
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("IP:");
		//Read the line out
		String ip = scan.nextLine();
		//Read the port
		System.out.println("Port:");
		int port = scan.nextInt();
		System.out.println("Your name");
		scan.nextLine();
		String name = scan.nextLine();
		
		ClientMaster cm = new ClientMaster(ip,port,name);
		cm.start();
		
		int choise;
		mainloop:
		while(true){
			System.out.println("1 - Get list of connected users");
			System.out.println("2 - Connect to slave");
			System.out.println("3 - Exit");
			choise = scan.nextInt();
			switch(choise){
				case 1:
					
					ArrayList<ConnectedUser> users = cm.requestConnectedList();
					
					//Now print the result
					for(ConnectedUser user: users){
						System.out.println(user.toString());
					}
					break;
				case 2:
					System.out.println("Slaves id:");
					int slaveID = scan.nextInt();
					System.out.println("Your external IP:");
					scan.nextLine();
					String yourIp = scan.nextLine();
					System.out.println("Your port:");
					int yourPort = scan.nextInt();
					cm.requestConnectToSlave(slaveID, yourIp, yourPort);
					break;
				case 3:
					System.out.println("Stopping Server...");
					cm.stopServer();
					System.out.println("...Server Stopped");
					break mainloop;
				}
		}		
	}
	
	public static void main(String[] args){
		new CLIMaster();
	}

}
