package Client;
import Serveur.Serveur;
import java.io.DataOutputStream;
import java.io.File;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

// Application client
public class Client {
	private static Socket socket;
	public int clientPort;
	public String clientAddresse;
	String nomUtilisateur = null;
	String motDePasse = null;
	String nomImage = null;

	public static void getClientInfo(String[] userInfo, Scanner scanner) {
		System.out.print("Veuillez entrer votre nom d'utilisateur: ");
		userInfo[0] = scanner.nextLine();
		
		System.out.print("Veuillez entrer votre mot de passe: ");
		userInfo[1] = scanner.nextLine();
	}
	
	public static boolean verifyClientInfo(String username, String password) {
		File newFile = new File("Server/Serveur/Serveur/baseDonnees.txt");
		String[] donnees = Serveur.getDonnees(newFile, username);
		
		if (donnees[0] != null ) {
			if (donnees[1].equals(password)) {
				System.out.println("Compte existant et valide.");
				return true;
			}
			else {
				System.out.println("Erreur dans la saisie du mot de passe.");
				return false;
			}

		}
		else {
			System.out.println("Creation de compte");
			Serveur.creerCompte(newFile, username, password);
			return true;
			}
		
	} 
	public static String[] getInfosImage(String username, String address, int port, Scanner scanner) {
		String[] pack = new String[2];
		String nomImage = null;
		System.out.print("Veuillez indiquer le nom de l'image à traiter: ");
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
				"Image " + nomImage + ".jpg reçue pour traitement. ";
		pack[0] = infos;
		pack[1] = nomImage;
				
		return pack;
	}
	
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		String[] serverInfo = new String[2];
		Serveur.getServerInfo(serverInfo, input);
		String serverAddress = serverInfo[0];
		int serverPort = Integer.valueOf(serverInfo[1]);
		boolean serverValid = Serveur.verify(serverAddress, serverPort);
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
				System.out.format("Serveur lancé sur [%s:%d]", serverAddress, serverPort);
				System.out.println(" ");
	
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
				System.out.println("Press to continue: " + input.nextLine());
			}
		}
		System.out.println("Press to continue: " + input.nextLine());
		socket.close();
	}
}
