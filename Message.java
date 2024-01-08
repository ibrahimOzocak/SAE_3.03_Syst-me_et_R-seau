import java.sql.Date;

public class Message {
    private int idMess;
    private Client user;
    private String content;
    private Date date;
    private int nbLike;

    public Message(int idMess, Client user, String content, Date date, int nbLike){
        this.idMess = idMess;
        this.user = user;
        this.content = content;
        this.date = date;
        this.nbLike = nbLike;
    }
}
