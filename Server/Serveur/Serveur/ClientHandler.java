package Serveur;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Client.Sobel;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;

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
				//in.close();
				DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi 
				out.writeUTF("Hello from server - you are client#" + clientNumber); // envoi de message
				DataInputStream message = new DataInputStream(socket.getInputStream());
				//String message = in.readUTF();
				System.out.println("Received from client#" + clientNumber + ":" + message.readUTF());
				//System.out.println(message.readUTF());
				
				//String response = "Message received: " + message;
				//out.writeUTF(response);
				//DataInputStream in = new DataInputStream(socket.getInputStream());
				//String imageInfo = in.readUTF();
				//out.writeUTF(imageInfo);
				//System.out.println(System.getProperty("user.dir"));
				//DataInputStream in = new DataInputStream(socket.getInputStream());
				//String imageInfo = in.readUTF();
				//System.out.println("Received from client: " + imageInfo);
				//String nameImage = message.readUTF();
				File file = new File("Server/Serveur/Client/polyImage.jpg");
				System.out.println("Image from client has been received.");
				BufferedImage image = ImageIO.read(file);
				BufferedImage newImage = Sobel.process(image);
				File outputFile = new File("Server/Serveur/Client/polyImage_processed.jpg");
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
		//public void main() {
			//ClientHandler clientHandler = new ClientHandler(new Socket(), 5);
			
		//}
}
