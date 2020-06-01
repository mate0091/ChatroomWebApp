package Models;

import java.sql.Blob;

public class Message
{
    private int id;
    private String dateTime;
    private String content;

    public Message() {
        this("", "");
    }

    public Message(int id, String dateTime, String content) {
        this(dateTime, content);
        this.id = id;
    }

    public Message(String dateTime, String content) {
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

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", dateTime='" + dateTime + '\'' +
                ", content=" + content +
                '}';
    }
}
