import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            Thread serverListener = new Thread(new ServerListener(socket));
            serverListener.start();

            Scanner scanner = new Scanner(System.in);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            while (true) {
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.print("Enter a message : "); A laisser pour voir avec une interface graphics
                String message = scanner.nextLine();
                
                writer.println(message);
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ServerListener implements Runnable {
        private Socket socket;
        private Scanner reader;

        public ServerListener(Socket socket) {
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
}
