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
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
			System.out.println("Creation de compte");
			//System.out.println(System.getProperty("user.dir"));
			Serveur.creerCompte(newFile, username, password);
			return true;
			}
		
	} 
	public static String[] getInfosImage(String username, String address, int port, Scanner scanner) {
		String[] pack = new String[2];
		String nomImage = null;
		//Scanner scanner = new Scanner(System.in);
		System.out.print("Veuillez indiquer le nom de l'image à traiter: ");
		nomImage = scanner.nextLine();
		try {
			File file = new File("Server/Serveur/Client/" + nomImage + ".txt");
		}
		finally {
			System.out.println("Image name same as path");
		}
		//scanner.close();
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		LocalTime roundedTime = LocalTime.of(time.getHour(), time.getMinute(), time.getSecond());
		String infos = 
				"[" + username + " - " + address + ":" + String.valueOf(port) +
				" - " + String.valueOf(date) + "@" + String.valueOf(roundedTime) + "] : " +
				"Image " + nomImage + ".jpg reçue pour traitement.";
		pack[0] = infos;
		pack[1] = nomImage;
				
		return pack;
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
		//String infoImage = getInfosImage(username, serverAddress, serverPort, input);
		//input.close();
		
		// Création d'une nouvelle connexion aves le serveur
		if (clientValid) {
			try {
				//socket = new Socket(serverAddress, serverPort);
				System.out.format("Serveur lancé sur [%s:%d]", serverAddress, serverPort);
				System.out.println(" ");
	
				String[] infoImage = getInfosImage(username, serverAddress, serverPort, input);
				//System.out.println(infoImage);
				//System.out.println(input.nextLine());
				socket = new Socket(serverAddress, serverPort);
				
				//DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				//out.writeUTF(infoImage[0]);
				//out.writeUTF(infoImage[1]);
				//System.out.println(input.nextLine());
				//String imageInfo = getInfosImage();
				//String infoImage = getInfosImage(username, serverAddress, serverPort, input);
				//String im = input.nextLine();
				//out.writeUTF(infoImage);
				//System.out.println("Message sent to the server.");
				//String random = input.nextLine();
				//out.writeUTF("Hello from client!");
				//out.writeUTF(infoImage);
				//System.out.println(input.next());
				//String inf = input.nextLine();
				//out.writeUTF(inf);
				//PrintWriter writer = new PrintWriter(output, true);
				//writer.println(getInfosImage(username, serverAddress, serverPort));
				
				//InputStreamReader read = new InputStreamReader(socket.getInputStream());
		        //BufferedReader lecteur = new BufferedReader(read);
		
		        //String message = lecteur.readLine();
		        //System.out.println("Server message: " + message);
				//DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	            //out.writeUTF(getInfosImage(username, serverAddress, serverPort));
	
				//String imageInfo = getInfosImage(username, serverAddress, serverPort);
				//String[] infoImage = getInfosImage(username, serverAddress, serverPort, input);
				//System.out.println(getInfosImage(username, serverAddress, serverPort));
				// Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
				//DataInputStream in = new DataInputStream(socket.getInputStream());
				//DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				DataInputStream in = new DataInputStream(socket.getInputStream());
				System.out.println(in.readUTF());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				// Attente de la réception d'un message envoyé par le, server sur le canal
				//String helloMessageFromServer = in.readUTF();
				//String hello = in.readUTF();
				//String hello = in.readUTF();
				//System.out.println(helloMessageFromServer);
				//int random = input.nextInt();
				//System.out.println(input.nextLine());
				//String[] infoImage = getInfosImage(username, serverAddress, serverPort, input);
				//DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF(infoImage[0]);
				out.writeUTF(infoImage[1]);
				//in.close();
				// fermeture de La connexion avec le serveur
				
				//socket.close();
			}
			finally {
				System.out.println("Press to continue: " + input.nextLine());
				//input.close();
			}
		}
		System.out.println("Press to continue: " + input.nextLine());
		socket.close();
		//input.close();
	}
}
