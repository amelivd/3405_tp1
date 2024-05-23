package Client;
import Serveur.Serveur;
import java.io.DataOutputStream;
import Serveur.ClientHandler;
import java.io.File;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.OutputStream;
import java.io.PrintWriter;
import Serveur.InfoRequest;

// Application client
public class Client {
	private static Socket socket;
	public int clientPort;
	public String clientAddresse;
	String nomUtilisateur = null;
	String motDePasse = null;
	String nomImage = null;
	/*
	public Client(Socket mySocket) {
		socket = mySocket;
		Serveur serveur = new Serveur();
		//serveur.verify(this::getAddressInfo);
		verifyClientInfo();
		try (Socket sc = new Socket(serveur.serverAddress, serveur.serverPort)) {
			 while (true) {
				 OutputStream output = sc.getOutputStream();
				 PrintWriter writer = new PrintWriter(output, true);
				 writer.println(infosImage()); // Client envoie infos au Serveur
			 }
			/*InputStreamReader input = new InputStreamReader(sc.getInputStream());
	        BufferedReader lecteur = new BufferedReader(input);
	
	        String message = lecteur.readLine();
	        System.out.println("Server message: " + message);
	        */
		/*} catch (IOException e) {
			System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
	    }
	}
	
	public void getAddressInfo()  {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Entrez votre adresse IP: ");
		String ipInput = scanner.nextLine();
		this.clientAddresse = ipInput;
		
		System.out.print("Entrez votre numero de port: ");
		String portInput = scanner.nextLine();
		this.clientPort = Integer.valueOf(portInput);
		//scanner.close();
	}
	
	*/
	public static void getClientInfo(String[] info, Scanner scanner) {
		System.out.print("Veuillez entrer votre nom d'utilisateur: ");
		info[0] = scanner.nextLine();
		
		System.out.print("Veuillez entrer votre mot de passe: ");
		info[1] = scanner.nextLine();
		//scanner.close();
	}
	
	public static boolean verifyClientInfo(String username, String password) {
		//getClientInfo();
		File newFile = new File("Server/Serveur/Serveur/baseDonnees.txt");
		String[] donnees = Serveur.getDonnees(newFile, username);
		//System.out.print(donnees[0]);
		
		if (donnees[0] != null ) {
			if (donnees[1].equals(password)) {
				System.out.println("Compte existant et valide.");
				return true;
			}
			else {
				System.out.println("Erreur dans la saisie du mot de passe.");
				return false;
				//verifyClientInfo();
			}

		}
		else {
			System.out.print("Creation de compte");
			//System.out.println(System.getProperty("user.dir"));
			Serveur.creerCompte(newFile, username, password);
			return true;
			}
		
	} 
	public static String getInfosImage(String username, String address, int port,Scanner scanner) {
		String nomImage = null;
		//Scanner scanner = new Scanner(System.in);
		System.out.print("Veuillez indiquer le nom de l'image à traiter: ");
		nomImage = scanner.nextLine();
		//scanner.close();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		String infos = 
				"[" + username + " - " + address + ":" + String.valueOf(port) +
				" - " + String.valueOf(date) + "@" + String.valueOf(time) + "] : " +
				"Image " + nomImage + "jpg reçue pour traitement.";
				
		return infos;
	}
	
	public static void main(String[] args) throws Exception {
		//Client client = new Client(new Socket("127.0.0.1", 5000));
		//String serverAddress = "127.0.0.1";
		//int port = 5000;
		Scanner input = new Scanner(System.in);
		String[] info = new String[2];
		Serveur.getServerInfo(info, input);
		String serverAddress = info[0];
		int serverPort = Integer.valueOf(info[1]);
		boolean serverValid = Serveur.verify(serverAddress, serverPort);
		// Adresse et port du serveur
		boolean clientValid = false;
		String username = null;
		String password = null;
		if (serverValid) {
			getClientInfo(info, input);
			username = info[0];
			password = info[1];
			clientValid = verifyClientInfo(username, password);
		}
		
		//input.close();
		
		// Création d'une nouvelle connexion aves le serveur
		if (clientValid) {
			System.out.format("Serveur lancé sur [%s:%d]", serverAddress, serverPort);
			System.out.println(getInfosImage(username, serverAddress, serverPort, input));
			System.out.println(input.nextLine());
			socket = new Socket(serverAddress, serverPort);
			//DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			//out.writeUTF(getInfosImage(username, serverAddress, serverPort, input));
			//PrintWriter writer = new PrintWriter(output, true);
			//writer.println(getInfosImage(username, serverAddress, serverPort));
			
			//InputStreamReader read = new InputStreamReader(socket.getInputStream());
	        //BufferedReader lecteur = new BufferedReader(read);
	
	        //String message = lecteur.readLine();
	        //System.out.println("Server message: " + message);
			//DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //out.writeUTF(getInfosImage(username, serverAddress, serverPort));

			//String imageInfo = getInfosImage(username, serverAddress, serverPort);

			//System.out.println(getInfosImage(username, serverAddress, serverPort));
			// Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
			DataInputStream in = new DataInputStream(socket.getInputStream());
			// Attente de la réception d'un message envoyé par le, server sur le canal
			String helloMessageFromServer = in.readUTF();
			System.out.println(helloMessageFromServer);
			// fermeture de La connexion avec le serveur
			socket.close();
		}
		input.close();
	}
}
