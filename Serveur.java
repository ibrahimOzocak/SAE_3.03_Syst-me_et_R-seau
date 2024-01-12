import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Serveur {
    private static final int PORT = 8080;
    private static List<PrintWriter> clientOutputStreams = new ArrayList<>();
    private static List<Message> messageList = new ArrayList<>();
    private static int idMessage;

    public Serveur(){
        idMessage = 0;
    }

    public static List<PrintWriter> getClientOutputStreams(){
        return clientOutputStreams;
    }

    public static List<Message> getListMessage(){
        return messageList;
    }
    
    public static void main(String[] args) {
        idMessage=0;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);
            
            new CommandesThread().start();

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                new ClientThread(clientSocket).start();                             // prombleme lors de la 2e connexion pour le client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getIdMessage() {
        return idMessage;
    }

    public static void ajoutIdMessage(){
        idMessage++;
    }
    

}
