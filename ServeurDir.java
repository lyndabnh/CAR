
package FTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServeurDir {
	private static ServerSocket server;
	private static Socket client;
	private static ServerSocket dataServer;
	private static int dataPort;

	private static void start() throws IOException {
		String user = "bounehar";
		String pass = "b";

		// Initialiser le serveur
		server = new ServerSocket(2020);
		System.out.println("Serveur FTP démarré sur le port 2020");
		client = server.accept();
		/*
		 * Mise en place des flux d'entrée et de sortie (OutputStream) pour communiquer
		 * avec le client
		 */
		InputStream clientSocket = client.getInputStream();
		Scanner scan = new Scanner(clientSocket);
		OutputStream out = client.getOutputStream();
		out.write("220 Service ready\r\n".getBytes());
		String logi = scan.nextLine();
		System.out.println(logi);

		while (true) {
			if (logi.equals("USER " + user)) {
				System.out.println("user valid");

				out.write("331 user valid\r\n".getBytes());
			} else if (!(logi.equals("USER " + user))) {

				out.write("530 Not logged in\r\n".getBytes());
			}

			String pas = scan.nextLine();
			System.out.println(pas);
			if (pas.equals("PASS " + pass)) {

				out.write("230 User logged in\r\n".getBytes());
				while (true) {

					while (scan.hasNext()) {
						String commande = scan.nextLine();
						System.out.println(commande);
						if (commande.equals("QUIT")) {

							out.write("221 User logged out\r\n".getBytes());
							break;
						} else if (commande.equals("TYPE I")) {

							out.write("200 Binary mode set\r\n".getBytes());
						} else if (commande.startsWith("SIZE")) {

							String[] parts = commande.split("\\s+");
							if (parts.length == 2) {
								String filename = parts[1];
								sendFileSize(filename, out);
							} else {

								out.write("501 Syntax error in parameters or arguments\r\n".getBytes());
							}

						} else if (commande.equals("SYST")) {

							out.write("215 UNIX Type: L8\r\n".getBytes());

						} else if (commande.equals("FEAT")) {

							out.write("211-Features:\r\n PASV\r\n SIZE\r\n UTF8\r\n211 End\r\n".getBytes());

						} else if (commande.equals("EPSV")) {

							dataServer = new ServerSocket(0);

							dataPort = dataServer.getLocalPort();

							out.write(("229 Entering Extended Passive Mode (|||" + dataPort + "|)\r\n").getBytes());
						} else if (commande.equals("PASV")) {
							dataServer = new ServerSocket(0);
							dataPort = dataServer.getLocalPort();
							out.write(("229 Entering Passive Mode (|||" + dataPort + "|)\r\n").getBytes());
						} else if (commande.startsWith("RETR")) {
							Socket dataSocket = dataServer.accept();
							out.write("150 Accepted data connection\r\n".getBytes());
							String[] parts = commande.split("\\s+");
							if (parts.length == 2) {
								String filename = parts[1];

								FileInputStream fileInputStream = new FileInputStream(filename);
								OutputStream dataOut = dataSocket.getOutputStream();

								byte[] buffer = new byte[1024];
								int bytesRead;
								while ((bytesRead = fileInputStream.read(buffer)) != -1) {
									dataOut.write(buffer, 0, bytesRead);
								}

								fileInputStream.close();

								out.write("226 Closing data connection\r\n".getBytes());

							} else {

								out.write("501 Syntax error in parameters or arguments\r\n".getBytes());
							}

							dataServer.close();
							dataSocket.close();

						}

						else if (commande.startsWith("LIST")) {
							String[] parts = commande.split("\\s+");
							String directoryPath = (parts.length > 1) ? parts[1] : ".";

							Socket dataSocket = dataServer.accept();

							out.write("150 Accepted data connection\r\n".getBytes());

							StringBuilder directoryListing = new StringBuilder();

							File dir = new File(directoryPath);
							File[] files = dir.listFiles();
							if (files != null) {
								for (File file : files) {
									String fileDetails = String.format(" %s\r\n", file.getName());
									directoryListing.append(fileDetails);
								}

							}

							OutputStream dataOut = dataSocket.getOutputStream();
							dataOut.write(directoryListing.toString().getBytes());
							dataSocket.close();

							out.write("226 Closing data connection\r\n".getBytes());

						} else if (commande.startsWith("CWD")) {
							String[] parts = commande.split("\\s+");

							if (parts.length == 2) {
								String newDirectory = parts[1];

								File newDir = new File(newDirectory);
								if (newDir.exists() && newDir.isDirectory()) {

									System.setProperty("user.dir", newDirectory);

									out.write("250 Directory successfully changed\r\n".getBytes());
								} else {

									out.write("550 Directory not found\r\n".getBytes());
								}
							} else {

								out.write("501 Syntax\r\n".getBytes());
							}
						} else {

							out.write("502 commande not implemented\r\n".getBytes());
						}
					}
				}
			} else if (!(pas.equals("PASS " + pass))) {
				System.out.println("user invalid");

				out.write("530 Not logged in\r\n".getBytes());
			}
		}
	}

	private static void sendFileSize(String filename, OutputStream out) throws IOException {
		File file = new File(filename);
		if (file.exists() && file.isFile()) {
			long fileSize = file.length();
			String sizeResponse = "213 " + fileSize + "\r\n";
			out.write(sizeResponse.getBytes());
		} else {

			out.write("550 Requested action not taken. File not found\r\n".getBytes());
		}
	}

	public static void main(String[] args) throws IOException {
		start();
	}
}
