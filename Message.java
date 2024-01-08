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

    public int getIdMess(){
        return this.idMess;
    }

    public Client getUser(){
        return this.user;
    }

    public String getContent(){
        return this.content;
    }

    public Date getDate(){
        return this.date;
    }

    public int getNbLike(){
        return this.nbLike;
    }

    public void setNbLike(){
        this.nbLike+=1;
    }
}
