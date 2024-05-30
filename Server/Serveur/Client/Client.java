package Client;
import java.io.DataOutputStream;
import java.io.File;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;

import Server.Server;

import java.time.LocalDate;
import java.time.LocalTime;

// Application client
public class Client {
	private static Socket socket;

	public static void getClientInfo(String[] userInfo, Scanner scanner) {
		System.out.print("Enter your username: ");
		userInfo[0] = scanner.nextLine();
		
		System.out.print("Enter your password: ");
		userInfo[1] = scanner.nextLine();
	}
	
	public static boolean verifyClientInfo(String username, String password) {
		File newFile = new File("Server/Serveur/Server/baseDonnees.txt");
		String[] donnees = Server.getDonnees(newFile, username);
		
		if (donnees[0] != null ) {
			if (donnees[1].equals(password)) {
				System.out.println("Valid account");
				return true;
			}
			else {
				System.out.println("Incorrect password");
				return false;
			}

		}
		else {
			System.out.println("Creating account");
			Server.creerCompte(newFile, username, password);
			return true;
			}
		
	} 
	public static String[] getInfosImage(String username, String address, int port, Scanner scanner) {
		String[] pack = new String[2];
		String nomImage = null;
		System.out.print("Enter the name of the image: ");
		nomImage = scanner.nextLine();
		try {
			File file = new File("Server/Serveur/Client/" + nomImage + ".txt");
		}
		finally {
			System.out.println("Image name same as path");
		}
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		LocalTime roundedTime = LocalTime.of(time.getHour(), time.getMinute(), time.getSecond());
		String infos = 
				"[" + username + " - " + address + ":" + String.valueOf(port) +
				" - " + String.valueOf(date) + "@" + String.valueOf(roundedTime) + "] : " +
				"Image " + nomImage + ".jpg received for treatment ";
		pack[0] = infos;
		pack[1] = nomImage;
				
		return pack;
	}
	
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		String[] serverInfo = new String[2];
		Server.getServerInfo(serverInfo, input);
		String serverAddress = serverInfo[0];
		int serverPort = Integer.valueOf(serverInfo[1]);
		boolean serverValid = Server.verify(serverAddress, serverPort);
		// Adresse et port du serveur
		boolean clientValid = false;
		String username = null;
		String password = null;
		if (serverValid) {
			String[] clientInfo = new String[2];
			getClientInfo(clientInfo, input);
			username = clientInfo[0];
			password = clientInfo[1];
			clientValid = verifyClientInfo(username, password);
		}
		// Création d'une nouvelle connexion avec le serveur
		if (clientValid) {
			try {
				System.out.format("Server is running on [%s:%d]", serverAddress, serverPort);
				System.out.println(" ");  // pour sauter de ligne
	
				String[] infoImage = getInfosImage(username, serverAddress, serverPort, input);
				socket = new Socket(serverAddress, serverPort);

				DataInputStream in = new DataInputStream(socket.getInputStream());
				System.out.println(in.readUTF());
				System.out.println(in.readUTF());
				
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				System.out.print(input.nextLine());
				out.writeUTF(infoImage[0]);
				out.writeUTF(infoImage[1]);

			}
			finally {
			}
		}
		socket.close();
	}
}
