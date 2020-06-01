package Models;

public class RoomMember
{
    private int id;
    private int user_id, room_id;
    private String role;

    public RoomMember()
    {
        this(0, 0, "");
    }

    public RoomMember(int id, int user_id, int room_id, String role) {
        this(user_id, room_id, role);
        this.id = id;
    }

    public RoomMember(int user_id, int room_id, String role) {
        this.id = 0;
        this.user_id = user_id;
        this.room_id = room_id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "RoomMember{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", room_id=" + room_id +
                ", role='" + role + '\'' +
                '}';
    }
}
