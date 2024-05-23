package Serveur;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintWriter;
import Client.Client;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;


public class Serveur {
	private static ServerSocket Listener; // Application Serveur
	//public static String serverAddress; //= "127.0.0.1"; 
	//public static int serverPort;//= 5000;
	//public int numeroClient = 0;
	File file = new File("baseDonnees.txt");
	/*public Serveur() {
		verify(this::askInfo);
		try (ServerSocket serverSocket = new ServerSocket(serverPort)){
			System.out.println("Le serveur ecoute le port " + serverPort);
			while (true) {
	                Socket socket = serverSocket.accept(); // Accept a new client connection
	                numeroClient++;
	                System.out.println(serverSocket);
	                ClientHandler clientHandler = new ClientHandler(socket, numeroClient);
	                System.out.print("Succès d'envoi de l'image");
	                InputStreamReader input = new InputStreamReader(socket.getInputStream());
	                BufferedReader reader = new BufferedReader(input);
	                String message = reader.readLine();
	                System.out.println("Server message: " + message);
	                //OutputStream output = socket.getOutputStream();
	                //PrintWriter writer = new PrintWriter(output, true);
	                //writer.println("Hello, client!"); // Send a message to the client

	                socket.close(); 
	            }
		} catch (IOException e) {
			
		}
	}
	*/
	public static void getServerInfo(String[] info, Scanner scanner) {
		System.out.print("Entrez l'adresse IP du serveur: ");
		info[0] = scanner.nextLine();
		
		System.out.print("Entrez le port d'écoute du serveur: ");
		info[1] = scanner.nextLine();
		//scanner.close();
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
	            System.out.println("Adresse IP invalide. ");
	            //verify(infoRequest, adresse, port);
	            isValid = false;
	        }
		if (ipParts != null) {
		 for (String part : ipParts) {
	            int byteValue = Integer.valueOf(part);
	            if (byteValue < 0 || byteValue > 255) {
	                System.out.println("Adresse IP invalide. ");
	                isValid = false;
	                //verify(infoRequest, adresse, port);
	                }
	        }
		}
		
		 if (port < 5000 || port > 5050) {
	            System.out.println("Numero de port invalide.");
	            isValid = false;
	            //verify(infoRequest, adresse, port);
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
					//System.out.println(line[0] + " " + line[1]);
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
			// Adresse et port du serveur
			//String serverAddress = "127.0.0.1"; 
			//int serverPort = 5000;
			String[] info = new String[2];
			getServerInfo(info, input);
			String serverAddress = info[0];
			int serverPort = Integer.valueOf(info[1]);
			
			boolean isValid = verify(serverAddress, serverPort);
			input.close();
			// Création de la connexion pour communiquer avec les clients
			if (isValid) {
				Listener = new ServerSocket();
				Listener.setReuseAddress(true);
				InetAddress serverIP = InetAddress.getByName(serverAddress);
	
				// Association de l'adresse et du port à la connexien
				Listener.bind(new InetSocketAddress(serverIP, serverPort));
				System.out.format("The server is running on %s:%d%n", serverAddress, serverPort);
				
				//File file = new File("Server/Serveur/Serveur/baseDonnees.txt");
				//creerCompte(file, "layla", "ly");
				
				
				try {
					// À chaque fois qu'un nouveau client se, connecte, on exécute la fonstion
					// run() de l'objet ClientHandler
					while (true) {
					// Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte
					// Une nouvetle connection : on incémente le compteur clientNumber 
						new ClientHandler(Listener.accept(), clientNumber++).start();
					}
				} 
				//catch (IOException e) {
					//System.out.println(e.getMessage());
				//}
				finally {
				// Fermeture de la connexion
					//if (Listener != null && !Listener.isClosed())
					Listener.close();
				} 
			}
		}
}
