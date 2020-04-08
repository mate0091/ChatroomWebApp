package Models;

public class RoomMsg
{
    private int id, room_id, msg_id;

    public RoomMsg(int id, int room_id, int msg_id) {
        this(room_id, msg_id);
        this.id = id;
    }

    public RoomMsg(int room_id, int msg_id)
    {
        this.id = 0;
        this.room_id = room_id;
        this.msg_id = msg_id;
    }

    public int getId() {
        return id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public int getMsg_id() {
        return msg_id;
    }
}
