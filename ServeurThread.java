import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ServeurThread extends Thread {
    private Socket socket;
    private Scanner reader;
    private static int id;

    public ServeurThread(Socket socket) {
        this.socket = socket;
        try {
            this.reader = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getIdServeurMess(){
        return id;
    }

    private static boolean isEntier(String str) {
        // Utiliser une expression régulière pour vérifier si la chaîne est composée uniquement de chiffres
        return str.matches("\\d+");
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (reader.hasNext()) {
                    String message = reader.nextLine();      // revoir sa, le message recu pour etre sur que ce soit le bon
                    if (isEntier(message)){
                        id = Integer.parseInt(message);
                        System.out.println(id);
                    }
                    else{
                        System.out.println("Received from server: " + message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}