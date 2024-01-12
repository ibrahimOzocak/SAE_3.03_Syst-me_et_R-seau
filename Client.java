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

    private static int idUser;
    private static String nomUser;
    private static List<Message> listMessage = new ArrayList<>();

    // idMess commence a 0, prend la bonne valeur pour le 2sd message

    private static int idMess=ServeurThread.getIdServeurMess();

    public Client(int idUser, String nomUser){
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
                //idMessage++;                                // mettre les id pour les messages dans le serveur
                String[] motMessage = message.split(" ");
                if (message.substring(0,1).equals("/")){    // verifie si c'est une commande
                    if (message.equals("/lMessage")){
                        getListMessage().forEach(mess -> System.out.println(mess.toString()));    // Affiche les informations de chaque message
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
                    writer.println("teste");        // changer le message utiliser, c pour avoir l'idMessage
                    writer.flush();                 // a partir du serveur
                    idMess = ServeurThread.getIdServeurMess();
                    System.out.println("Client "+idMess);
                    LocalDate localDate = LocalDate.now();
                    Date date = Date.valueOf(localDate);
                    Message mess = new Message(idMess, nomUser, message, date, 0);
                    listMessage.add(mess);
                    writer.println(message);
                    writer.println(nomUser);
                    writer.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getIdUser(){
        return idUser;
    }

    public static String getNomUser(){
        return nomUser;
    }

    public static List<Message> getListMessage(){
        return listMessage;
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
            System.out.print("Donner votre pseudo : ");
            pseudo = scanner.nextLine();
        }
        
        new Client(idUser, pseudo);
        idUser++;
        scanner.close();
    }
}
