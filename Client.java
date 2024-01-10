import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Date;
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
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            Thread serverListener = new Thread(new ServeurThread(socket));
            serverListener.start();

            Scanner scanner = new Scanner(System.in);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            int idMessage=0;
            while (true) {
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.print("Enter a message : "); A laisser pour voir avec une interface graphics
                String message = scanner.nextLine();
                idMessage++;                                // mettre les id pour les messages dans le serveur
                String[] motMessage = message.split(" ");
                if (message.substring(0,1).equals("/")){    // verifie si c'est une commande
                    if (message.equals("/lMessage")){
                        this.getListMessage().forEach(mess -> System.out.println(mess.toString()));    // Affiche les informations de chaque message
                    }
                    else if (motMessage[0].equals("/delete")){
                        try{
                            int convId = Integer.parseInt(motMessage[1]);
                            this.supprMessage(convId);
                        }catch(NumberFormatException e){
                            System.err.println("Erreur sur l'Id du message donnée : " + motMessage[1] );
                        }
                    }
                }
                else{
                    LocalDate localDate = LocalDate.now();
                    Date date = Date.valueOf(localDate);
                    this.listMessage.add(new Message(idMessage++, this.nomUser, message, date, 0));
                    writer.println(message);
                    writer.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void supprMessage(int id){
        Iterator<Message> iterator = listMessage.iterator();
        boolean trouver = false;
        while (iterator.hasNext()) {
            Message message = iterator.next();
            if (message.getIdMess() == id) {
                iterator.remove(); // Supprime le message
                System.out.println("Message supprimé.");
                trouver = true;
            }
        }
        if (trouver==false){
            System.out.println("Aucun message trouvé avec l'ID spécifié.");
        }
    }

    public static void main(String[] args) {
        String pseudo = null;

        Scanner scanner = new Scanner(System.in);

        while(pseudo == null){
            System.out.println("Donner votre pseudo:");
            pseudo = scanner.nextLine();
        }

        new Client(0, pseudo);
        scanner.close();
    }
}
