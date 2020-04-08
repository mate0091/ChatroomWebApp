package Models;

public class Friends
{
    private int id;
    private int userID1, userID2;

    public Friends(int id, int userID1, int userID2)
    {
        this(userID1, userID2);
        this.id = id;
    }

    public Friends(int userID1, int userID2) {
        this.id = 0;
        this.userID1 = userID1;
        this.userID2 = userID2;
    }

    public int getId() {
        return id;
    }

    public int getUserID1() {
        return userID1;
    }

    public int getUserID2() {
        return userID2;
    }
}
