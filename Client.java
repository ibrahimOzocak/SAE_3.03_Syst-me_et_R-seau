import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    private int idUser;
    private String nomUser;
    private List<Message> listMessage;

    public Client(int idUser, String nomUser){
        this.idUser = idUser;
        this.nomUser = nomUser;
        this.listMessage = new ArrayList<>();
    }

    public int getIdUser(){
        return this.idUser;
    }

    public String getNomUser(){
        return this.nomUser;
    }

    public List<Message> getListMessage(){
        return this.listMessage;
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            Thread serverListener = new Thread(new ServeurThread(socket));
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
                if (message.substring(0,1).equals("/")){
                    
                }
                this.listMessage.add(message);
                writer.println(message);
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
