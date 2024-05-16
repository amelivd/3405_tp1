package Serveur;
import java.util.Scanner;

public class Serveur {

	public String adresseIP;
	public int port;
	
	public static void main(String[] string) {
		System.out.println("Hello, World");
	}
	
	public Serveur() {
		checkIfValid();
	}
	
	public void askInfo() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Entrez votre adresse IP");
		String ipInput = scanner.nextLine();
		this.adresseIP = ipInput;
		
		System.out.print("Entrez votre numero de port");
		String portInput = scanner.nextLine();
		this.port = Integer.parseInt(portInput);
		scanner.close();
	}
	
	
	
	public void checkIfValid() {
		askInfo();
		String[] ipParts = adresseIP.split("\\.");
		 if (ipParts.length != 4) {
	            System.out.println("Adresse IP invalide. Veuillez entrer une adresse IP valide.");
	            checkIfValid();
	            return;
	        }
		 for (String part : ipParts) {
                int byteValue = Integer.parseInt(part);
                if (byteValue < 0 || byteValue > 255) {
                    System.out.println("Adresse IP invalide. Veuillez entrer une adresse IP valide.");
                    checkIfValid();
                    return;
	                }
                else {
	                System.out.println("Adresse IP invalide. Veuillez entrer une adresse IP valide.");
	                checkIfValid();
	                return;
	            }
	        }
		
		 if (port < 5000 || port > 5050) {
	            System.out.println("Numero de port invalide. Veuillez entrer un numero de port entre 5000 et 5050.");
	            checkIfValid();
	            return;
	        }
		
	}
	
}