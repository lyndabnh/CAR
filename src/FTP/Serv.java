
package FTP;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serv {
	private static ServerSocket server;
	private static ServerSocket dataServer;
	private static Socket client;
	private static int dataPort;
	// private static boolean binaryMode = false;

	public static void main(String[] args) throws IOException {
		String user = "bounehar";
		String pass = "b";

		// Initialiser le serveur
		server = new ServerSocket(2020);
		System.out.println("Serveur FTP démarré sur le port 2020");
		client = server.accept();
		/*
		 * Mise en place des flux d'entrée (Scanner) et de sortie (OutputStream) pour
		 * communiquer avec le client
		 */
		InputStream clientSocket = client.getInputStream();
		Scanner scan = new Scanner(clientSocket);
		OutputStream out = client.getOutputStream();
		out.write("220 Service ready\r\n".getBytes());

		// user
		String login = scan.nextLine();
		System.out.println(login);
		if (login.equals("USER " + user)) {
			out.write("331 Login valid\r\n".getBytes());
		} else {
			String strL = "441 Login invalid\r\n";
			out.write(strL.getBytes());
		}

		// password
		String pas = scan.nextLine();
		System.out.println(pas);
		if (pas.equals("PASS " + pass)) {
			out.write("230 User logged in \r\n".getBytes());
		} else {
			out.write("441 Pass invalid\r\n".getBytes());
		}
		/*
		 * SYST et FEAT renvoient des informations sur le système et les fonctionnalités
		 * prises en charge par le serveur.
		 */

		String syss = scan.nextLine();
		System.out.println(syss);
		if (syss.equals("SYST")) {
			out.write("215 UNIX Type: L8\r\n".getBytes());
		}
		String fea = scan.nextLine();
		System.out.println(fea);
		if (fea.equals("FEAT")) {
			out.write("211-Features:\r\n PASV\r\n SIZE\r\n UTF8\r\n211 End\r\n".getBytes());
		}
		// détermine le mode de transfert
		String bin = scan.nextLine();
		System.out.println(bin);
		if (bin.equals("TYPE I")) {
			out.write("200 Binary mode set\r\n".getBytes());
		}
		// size

		String siz = scan.nextLine();
		System.out.println(siz);
		if (siz.startsWith("SIZE")) {
			String[] parts = siz.split("\\s+");
			if (parts.length == 2) {
				String filename = parts[1];
				sendFileSize(filename, out);
			} else {
				out.write("501 Syntax error in parameters or arguments\r\n".getBytes());
			}
		}
		/*
		 * Le serveur ouvre un nouveau ServerSocket pour écouter la connexion de données
		 * sur un port disponible en mode passive
		 */
		String epsvv = scan.nextLine();
		System.out.println(epsvv);
		if (epsvv.equals("EPSV") || epsvv.equals("PASV")) {
			dataServer = new ServerSocket(0);
			dataPort = dataServer.getLocalPort();
			out.write(("229 Entering Extended Passive Mode (|||" + dataPort + "|)\r\n").getBytes());
		}
		/*
		 * Si le client envoie une commande RETR avec le nom d'un fichier, le serveur
		 * accepte une connexion de données.
		 */
		String retr = scan.nextLine();
		System.out.println(retr);
		if (retr.startsWith("RETR")) {
			Socket dataSocket = dataServer.accept();
			out.write("150 Accepted data connection\r\n".getBytes());
			String[] parts = retr.split("\\s+");
			if (parts.length == 2) {
				String filename = parts[1];
				// Lire le fichier et envoyer son contenu
				FileInputStream fileInput = new FileInputStream(filename);
				byte[] buffer = new byte[1024];
				int bytesRead;
				OutputStream dataOut = dataSocket.getOutputStream(); // New data output stream
				while ((bytesRead = fileInput.read(buffer)) != -1) {
					dataOut.write(buffer, 0, bytesRead);
				}
				// fermer la connexion
				dataOut.close();
				dataSocket.close();
				fileInput.close();
			} else {
				out.write("501 Syntax error in parameters or arguments\r\n".getBytes());
			}
			// Send success message on the control connection
			out.write("226 Transfert de fichier réussi\r\n".getBytes());
		}
		
		
		while (true) {
			if (scan.hasNextLine()) {
				// quit
				String quit = scan.nextLine();
				if (quit.equals("QUIT")) {
					out.write("221 User logged out\r\n".getBytes());
				} else {
					out.write("502 Command not implemented\r\n".getBytes());
				}
			}
		}

	}

	private static void sendFileSize(String filename, OutputStream out) throws IOException {
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			long fileSize = file.length();
			out.write(("213 " + fileSize + "\r\n").getBytes());
		} else {
			out.write("550 Requested action not taken. File not found\r\n".getBytes());
		}
	}

}
