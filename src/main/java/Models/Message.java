package Models;

import java.sql.Blob;

public class Message
{
    private int id;
    private String dateTime;
    private Blob content;

    public Message(int id, String dateTime, Blob content) {
        this(dateTime, content);
        this.id = id;
    }

    public Message(String dateTime, Blob content) {
        this.id = 0;
        this.dateTime = dateTime;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Blob getContent() {
        return content;
    }
}
