import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ServeurThread extends Thread {
    private Socket socket;
    private Scanner reader;

    public ServeurThread(Socket socket) {
        this.socket = socket;
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
                    System.out.println("Received from server: " + message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}