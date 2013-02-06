package master;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIMaster {
	
	public CLIMaster(){
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("IP:");
		//Read the line out
		scan.nextLine();
		String ip = scan.nextLine();
		//Read the port
		System.out.println("Port:");
		int port = scan.nextInt();
		
		ClientMaster cm = new ClientMaster(ip,port);
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
					//Request the server to get all the users
					cm.requestConnectedList();
					//Wait until the thread is they are done.
					try {
						cm.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Get the users and iterate over them and print them out
					ArrayList<ConnectedUser> users = cm.getCcServerUsers();
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
