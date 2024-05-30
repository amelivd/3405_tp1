package Server;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Server {
	private static ServerSocket Listener; // Application Serveur
	public static void getServerInfo(String[] info, Scanner scanner) {
		System.out.print("Enter the server IP adress: ");
		info[0] = scanner.nextLine();
		
		System.out.print("Enter the server Port: ");
		info[1] = scanner.nextLine();
	}

	
	public static boolean verify( String adresse, int port) {
		String[] ipParts = null;
		boolean isValid = true;
		try {
			ipParts = adresse.split("\\.");
		}
		catch (NumberFormatException e){
		}
		if (ipParts.length != 4) {
	            System.out.println("Invalid IP address ");
	            isValid = false;
	        }
		if (ipParts != null) {
		 for (String part : ipParts) {
	            int byteValue = Integer.valueOf(part);
	            if (byteValue < 0 || byteValue > 255) {
	                System.out.println("Invalid IP address ");
	                isValid = false;
	                }
	        }
		}
		
		 if (port < 5000 || port > 5050) {
	            System.out.println("Invalid Port");
	            isValid = false;
	     }
		 return isValid;
		
	}
	
		
	public static void creerCompte(File file, String nomUtilisateur, String password) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
				writer.write(nomUtilisateur + " ");
				writer.write(password);
				writer.newLine();
			} catch (IOException e) {
				System.err.println("Error writing to file: " + file.getPath());
	            e.printStackTrace();
			}
		}
		
		
		public static String[] getDonnees(File data, String element) {
			String elem1 = null;
			String elem2 = null;
			try {
				Scanner scanner = new Scanner(data);
				while(scanner.hasNextLine()) {
					String[] line = scanner.nextLine().split(" ");
					if (line[0].equals(element)) {
						elem1 = line[0];
						elem2 = line[1];
						break;
					}
				}
				scanner.close();
			}
			catch (FileNotFoundException e) {
				 System.err.println("File not found: " + data.getPath());
			}
			String[] infos = new String[] {elem1, elem2};
			return infos;
		}
		
		public static void main(String[] args) throws Exception {
			// Compteur incrémenté à chaque connexion d'un client au serveur
			Scanner input = new Scanner(System.in);
			int clientNumber = 0;

			String[] serverInfo = new String[2];
			getServerInfo(serverInfo, input);
			String serverAddress = serverInfo[0];
			int serverPort = Integer.valueOf(serverInfo[1]);
			
			boolean isValid = verify(serverAddress, serverPort);
			
			
			// Création de la connexion pour communiquer avec les clients
			if (isValid) {
				Listener = new ServerSocket();
				Listener.setReuseAddress(true);
				InetAddress serverIP = InetAddress.getByName(serverAddress);
	
				// Association de l'adresse et du port à la connexien
				Listener.bind(new InetSocketAddress(serverIP, serverPort));
				System.out.format("The server is running on %s:%d%n", serverAddress, serverPort);								
				
				try {
					// À chaque fois qu'un nouveau client se connecte on exécute la fonction
					// run() de l'objet ClientHandler
					while (true) {
					// Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte
					// Une nouvelle connection : on incrémente le compteur clientNumber 
						new ClientHandler(Listener.accept(), clientNumber++).start();
					}
				} 

				finally {
				// Fermeture de la connexion
					Listener.close();
				} 
			}
		}
}
