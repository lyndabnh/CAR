package FTP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) {
        startClient();
    }

    private static void startClient() {
        String host = "localhost";
        int port = 2020;

        try {
            Socket socket = new Socket(host, port);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            
            String serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

            
            String username = "miage";
            out.println("USER " + username);

            
            serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

           
            String password = "car";
            out.println("PASS " + password);

           
            serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

           
            out.println("PING");

            
            serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

            if (serverResponse.equals("200 PING command ok")) {
               
                out.println("PONG");

                
                serverResponse = in.readLine();
                System.out.println("Server: " + serverResponse);
            } else {
                System.out.println("Server did not respond properly to PING");
            }

           
            out.println("QUIT");

           
            serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

           
            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
