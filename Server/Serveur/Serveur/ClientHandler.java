package Serveur;
import java.io.DataOutputStream;
import java.util.Scanner;
import java.io.DataInputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Client.Sobel;
import java.io.IOException;
import java.net.Socket;

import java.io.File;

public class ClientHandler extends Thread { // pour traiter la demande de chaque client sur un socket particulier
	private Socket socket; 
	private int clientNumber; 
	public ClientHandler(Socket socket, int clientNumber) {
		this.socket = socket;
		this.clientNumber = clientNumber; 
		System.out.println("New connection with client#" + clientNumber + " at" + socket);
		}
	
		public void run() { // Création de thread qui envoi un message à un client
			try {
				Scanner scanner = new Scanner(System.in);
				DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi 
				DataInputStream message = new DataInputStream(socket.getInputStream());
				out.writeUTF("Hello from server - you are client#" + clientNumber); // envoi de message
				out.writeUTF("Press to continue.");
				System.out.println("Press to continue.");
				System.out.print(scanner.nextLine());

				System.out.print("Received from client#" + clientNumber + ":" + message.readUTF());

				String nameImage = message.readUTF();
				File file = new File("Server/Serveur/Client/" + nameImage + ".jpg");
				System.out.println("Image from client has been received.");
				BufferedImage image = ImageIO.read(file);
				BufferedImage newImage = Sobel.process(image);
				File outputFile = new File("Server/Serveur/Client/" + nameImage + "_processed.jpg");
				ImageIO.write(newImage, "jpg", outputFile);
				System.out.println("Resulting image saved in: " + outputFile.getPath());

			} 
			catch (IOException e) {
				System.out.println("Error handling client# " + clientNumber + ": " + e);
			} 
			finally {
				try {
					socket.close();
					System.out.println("Socket closed");
					} 
				catch (IOException e) {
					System.out.println("Couldn't close a socket, what's going on?");
					}
				System.out.println("Connection with client# " + clientNumber+ " closed");
			}
			
		}

}
