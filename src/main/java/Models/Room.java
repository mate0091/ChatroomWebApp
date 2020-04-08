package Models;

public class Room
{
    private int id;
    private String type;

    public Room(int id, String type) {
        this(type);
        this.id = id;
    }

    public Room(String type) {
        id = 0;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", type='" + type + '\'';
    }
}
