package FTP;

/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {
    private static ServerSocket server;
    private static Socket client;

    public static void main(String[] args) {
        String user = "miage";
        String pass = "car";

        try {
            server = new ServerSocket(2020);
            System.out.println("Serveur FTP démarré sur le port 2020");

            while (true) {
                client = server.accept();
             

                // Flux d'entrée et de sortie pour la communication avec le client
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                OutputStream out = client.getOutputStream();

                // Envoyer la première réponse au client
                out.write("220 Service ready\r\n".getBytes());

                // Lire le nom d'utilisateur du client
                String username = in.readLine();
                System.out.println("Client : " + username);

                // Vérifier le nom d'utilisateur
                if (username.equals("USER " + user)) {
                    out.write("331 User valid\r\n".getBytes());
                } else {
                    out.write("530 Not logged in\r\n".getBytes());
                    client.close();
                    continue;
                }

                // Lire le mot de passe du client
                String password = in.readLine();
                System.out.println("Client : " + password);

                // Vérifier le mot de passe
                if (password.equals("PASS " + pass)) {
                    out.write("230 User logged in\r\n".getBytes());
                } else {
                    out.write("530 Not logged in\r\n".getBytes());
                    client.close();
                    continue;
                }

                // Lire les commandes du client
                while (true) {
                    String command = in.readLine();
                    System.out.println("Client : " + command);

                    // Répondre en fonction de la commande
                    if (command.equals("QUIT")) {
                        out.write("221 User logged out\r\n".getBytes());
                        client.close();
                        break;
                    } else {
                        // Traitez d'autres commandes selon les besoins de votre application
                        // Vous pouvez ajouter des conditions pour d'autres commandes FTP.
                        // Par exemple, gérer les commandes LIST, RETR, STOR, etc.
                        out.write("500 Unknown command\r\n".getBytes());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur{
    private static ServerSocket server;
    private static Socket client;

    public static void main(String[] args) {
        String user = "miage";
        String pass= "car";

        try {
            server = new ServerSocket(2020);
            System.out.println("Serveur FTP démarré sur le port 2020");

            while (true) {
                client = server.accept();
             

                // Flux d'entrée et de sortie pour la communication avec le client
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                OutputStream out = client.getOutputStream();

                
                out.write("220 Service ready\r\n".getBytes());

                // Lire le nom d'utilisateur du client
                String username = in.readLine();
                System.out.println("Client : " + username);

                // Vérifier le nom d'utilisateur
                if (username.equals("USER " + user)) {
                    out.write("331 User valid\r\n".getBytes());
                } else {
                    out.write("530 Not logged in\r\n".getBytes());
                    client.close();
                    continue;
                }

                // Lire le mot de passe du client
                String password = in.readLine();
                System.out.println("Client : " + password);

                // Vérifier le mot de passe
                if (password.equals("PASS " + pass)) {
                    out.write("230 User logged in\r\n".getBytes());
                } else {
                    out.write("530 Not logged in\r\n".getBytes());
                    client.close();
                    continue;
                }

                String ping = in.readLine();
                System.out.println("Client : " + ping);
                 if (ping.equals("PING")) {
                       
                       out.write("200 PING command ok\r\n".getBytes());
                        String response = in.readLine();
                        if (response.equals("PONG")) {
                            System.out.println("Client: 200 PONG command ok\r\n");
                            out.write("PONG\r\n".getBytes());
                        
                        } else {
                            out.write("502 Unknown command\r\n".getBytes());
                        }
                        String quit = in.readLine();
                        System.out.println("Client : " + quit);
                     if (quit.equals("QUIT")) {
                        out.write("221 User logged out\r\n".getBytes());
                        client.close();
                        break;

                    } else {
                        out.write("500 Unknown command\r\n".getBytes());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
