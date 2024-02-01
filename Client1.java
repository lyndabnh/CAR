package FTP;
/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client1 {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; 
        int port = 2020; 

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Client connecté au serveur.");

            // Lire les messages du serveur
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Serveur : " + serverMessage);

                // Répondre en fonction des messages du serveur
                if (serverMessage.equals("220 Service ready")) {
                    // Le serveur est prêt, vous pouvez envoyer le nom d'utilisateur
                    System.out.print("Entrez votre nom d'utilisateur : ");
                    String username = consoleInput.readLine();
                    out.println("USER " + username);
                } else if (serverMessage.equals("331 User valid")) {
                    // Le nom d'utilisateur est OK, vous pouvez envoyer le mot de passe
                    System.out.print("Entrez votre mot de passe : ");
                    String password = consoleInput.readLine();
                    out.println("PASS " + password);
                } else if (serverMessage.equals("230 User logged in")) {
                   
                 } else if  (serverMessage.equals("221 Service closing control connection")) {
                	// Le serveur a accepté la commande "quit", fermez la connexion du client
                	 /*System.out.println("Fermeture de la connexion.");
                	 String quit = consoleInput.readLine();
                     out.println("quit");*/
                	 /*break;
                	 
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client1 {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; 
        int port = 2020; // Remplacez ceci par le port que vous avez utilisé pour votre serveur

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Client connecté au serveur.");

            // Envoyer le nom d'utilisateur défini dans le serveur
            out.println("USER miage");

            // Lire les messages du serveur
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Serveur : " + serverMessage);

                // Répondre en fonction des messages du serveur
                if (serverMessage.equals("331 User valid")) {
                    // Envoyer le mot de passe défini dans le serveur
                   out.println("PASS car");
                } else if (serverMessage.equals("230 User logged in")) {
                    // L'utilisateur est connecté, vous pouvez effectuer d'autres opérations si nécessaire
                    System.out.println("Utilisateur connecté avec succès.");
                     // Sortir de la boucle après la connexion réussie
                } else if (serverMessage.equals("221 Service closing control connection")) {
                    // Le serveur a accepté la commande "quit", fermez la connexion du client
                   // System.out.println("quit");
                    
                }
                
                   
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client1 {
    public static void main(String[] args) {
        startClient();
    }

    private static void startClient() {
    	 
    	String host= "localhost";
        int port = 2020; 

        try {
            Socket socket = new Socket(host, port);

            // Créer des flux de sortie et d'entrée pour la communication avec le serveur
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Lire la première réponse du serveur
            String serverResponse = in.readLine();
            System.out.println("Serveur : " + serverResponse);

            // Envoyer le nom d'utilisateur au serveur
            String username = "miage"; 
            out.println("USER " + username);

            // Lire la réponse du serveur
            serverResponse = in.readLine();
            System.out.println("Serveur : " + serverResponse);

            // Envoyer le mot de passe au serveur
            String password = "car"; 
            out.println("PASS " + password);

            // Lire la réponse du serveur
            serverResponse = in.readLine();
            System.out.println("Serveur : " + serverResponse);

            

            // Envoyer la commande QUIT pour se déconnecter
            out.println("QUIT");

            // Lire la réponse du serveur après la commande QUIT
            serverResponse = in.readLine();
            System.out.println("Serveur : " + serverResponse);

           
            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
