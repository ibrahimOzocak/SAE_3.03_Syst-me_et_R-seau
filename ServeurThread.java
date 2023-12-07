import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServeurThread {
    private static final int PORT = 8080;
    private static List<PrintWriter> clientOutputStreams = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread clientHandler = new Thread(new ClientHandler(clientSocket));
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private Scanner reader;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                this.reader = new Scanner(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (reader.hasNext()) {
                        String message = reader.nextLine();
                        System.out.println("Received: " + message);
                        broadcast(message);
                    }
                }
            } finally {
                // Remove client's PrintWriter when the client disconnects
                try {
                    clientOutputStreams.remove(new PrintWriter(clientSocket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void broadcast(String message) {
        for (PrintWriter writer : clientOutputStreams) {
            try {
                writer.println(message);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
