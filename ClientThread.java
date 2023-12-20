import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ClientThread extends Thread {
        private Socket clientSocket;
        private Scanner reader;


        public ClientThread(Socket socket) {
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
                    Serveur.getClientOutputStreams().remove(new PrintWriter(clientSocket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    private static void broadcast(String message) {
        for (PrintWriter writer : Serveur.getClientOutputStreams()) {
            try {
                writer.println(message);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
