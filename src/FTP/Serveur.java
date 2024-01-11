package FTP;
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
                    OutputStream out =clientSocket.getOutputStream();
                    String str="200 Service ready\r\n";
                    out.write(str.getBytes());
                  
                    InputStream in = clientSocket.getInputStream();
                    
                    Scanner scanner = new Scanner(in);


                    String userName= scanner.nextLine();
                    System.out.println(userName);
                    out.write("331 User name ok \r\n".getBytes());
                 

                    String pwd= scanner.nextLine();
                    out.write("230 User logged in \r\n".getBytes());
                    System.out.println(pwd);

                   scanner.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
        }