package ftp;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {
	private static ServerSocket server;
	private static Socket client;

 public static void main(String[] args) throws IOException{

	 String user = "bounehar";
	 String pass = "b"; 

	 

	 

	 server = new ServerSocket(2121);
	 System.out.println("Serveur FTP démarré sur le port 2121");
	 client = server.accept(); // Accepter la connexion

	
	 InputStream clientSocket = client.getInputStream();
	 Scanner scan = new Scanner(clientSocket);
	 OutputStream out = client.getOutputStream();
	 out.write( "220 Service ready\r\n".getBytes());

	 // user

	 String login = scan.nextLine();
	 System.out.println(login);
	 if(login.equals("USER " +user)){
	 out.write("331 user name ok \r\n".getBytes());
	 }else {
	 String strL = "441 Login invalid\r\n";
	 out.write( strL.getBytes());

	 }

	// password

	 String pas = scan.nextLine();
	 System.out.println(pas);
	 if(pas.equals("PASS " +pass)){
	 out.write("230 User logged in \r\n".getBytes());
	 }else {
     out.write("441 Pass invalid\r\n".getBytes());
     } 
	 
	// quit
 
	 while(true){
     if (scan.hasNextLine()) {
     String quit = scan.nextLine();
     if (quit.equals("QUIT")) {
     out.write("221 User logged out\r\n".getBytes());
     } else {
     out.write("502 Command not implemented\r\n".getBytes());
     }
     }
	 }
 }
}

	 

	 

	 

	