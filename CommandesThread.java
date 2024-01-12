import java.util.Scanner;

public class CommandesThread extends Thread {
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextLine()) {
                String commande = scanner.nextLine();
                if (commande.equals("/lMessage")) {
                    Serveur.getListMessage().forEach(mess -> System.out.println(mess.toString()));
                }
                System.out.println(Serveur.getIdMessage());
            }
        }
    }
}
