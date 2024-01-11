package FTP ;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Serveur {

    static String USER = "bounehar";
    static String PASS = "b";

    public static void main(String[] args) {
        int port = 2020;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Serveur FTP démarré sur le port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion FTP reçue");

                // Créez un nouveau thread pour gérer la connexion
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                Scanner scanner = new Scanner(clientSocket.getInputStream());
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true)
        ) {

            writer.println("220 Service ready for new user.");

            boolean userAuthenticated = false;

            String clientCommand;
            while (scanner.hasNextLine()) {
                clientCommand = scanner.nextLine();
                System.out.println("Commande reçue: " + clientCommand);

                if (clientCommand.startsWith("USER")) {
                    String[] parts = clientCommand.split("\\s+");
                    if (parts.length == 2 && parts[1].equals(USER)) {
                        userAuthenticated = true;
                        writer.println("331 User name okay, need password.");
                    } else {
                        writer.println("530 Not logged in.");
                    }
                } else if (userAuthenticated && clientCommand.startsWith("PASS")) {
                    String[] parts = clientCommand.split("\\s+");
                    if (parts.length == 2 && parts[1].equals(PASS)) {
                        writer.println("230 User logged in.");
                    } else {
                        writer.println("530 Login incorrect.");
                    }
                } 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
