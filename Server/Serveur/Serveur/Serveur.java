package Serveur;
import java.util.Scanner;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class Serveur {
	private static ServerSocket Listener; // Application Serveur
	String serverAddress; //= "127.0.0.1"; 
	int serverPort;//= 5000;
	
	public Serveur() {
		checkIfValid();
	}
	
	public void askInfo()  {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Entrez votre adresse IP: ");
		String ipInput = scanner.nextLine();
		this.serverAddress = ipInput;
		
		System.out.print("Entrez votre numero de port: ");
		String portInput = scanner.nextLine();
		
		this.serverPort =  Integer.valueOf(portInput);
		//scanner.close();
	}
	
	public void checkIfValid() {
		askInfo();
		String[] ipParts = serverAddress.split("\\.");
		//System.out.println(ipParts.length + "      ");
		if (ipParts.length != 4) {
	            System.out.println("Adresse IP invalide. Veuillez entrer une adresse IP valide.");
	            checkIfValid();
	            //return;
	        }
		 for (String part : ipParts) {
	            int byteValue = Integer.valueOf(part);
	            //System.out.println(byteValue);
	            if (byteValue < 0 || byteValue > 255) {
	                System.out.println("Adresse IP invalide. Veuillez entrer une adresse IP valide.");
	                checkIfValid();
	                //return;
	                }
	            else {
	                //System.out.println("Adresse IP invalide. Veuillez entrer une adresse IP valide.");
	                //checkIfValid();
	                //return;
	            }
	        }
		
		 if (serverPort < 5000 || serverPort > 5050) {
	            System.out.println("Numero de port invalide. Veuillez entrer un numero de port entre 5000 et 5050.");
	            checkIfValid();
	            return;
	        }
		
		};
		
		public static void main(String[] args) {
			Serveur serveur = new Serveur();
		}
	
		/*public static void main(String[] args) throws Exception {
			// Compteur incrémenté à chaque connexion d'un client au serveur
			int clientNumber = 0;
			// Adresse et port du serveur
			String serverAddress = "127.0.0.1"; 
			int serverPort = 5000;
			// Création de la connexien pour communiquer ave les, clients
			Listener = new ServerSocket();
			Listener.setReuseAddress(true);
			InetAddress serverIP = InetAddress.getByName(serverAddress);
			// Association de l'adresse et du port à la connexien
			Listener.bind(new InetSocketAddress(serverIP, serverPort));
			System.out.format("The server is running on %s:%d%n", serverAddress, serverPort);
			try {
			// À chaque fois qu'un nouveau client se, connecte, on exécute la fonstion
			// run() de l'objet ClientHandler
			while (true) {
			// Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte
			// Une nouvetle connection : on incémente le compteur clientNumber 
				new ClientHandler(Listener.accept(), clientNumber++).start();
			}
			} 
			finally {
			// Fermeture de la connexion
			Listener.close();
			} 
		}
		*/
	}
