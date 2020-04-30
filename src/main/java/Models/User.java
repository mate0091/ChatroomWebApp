package Models;

public class User
{
    private int id;
    private String username;
    private String password;

    public User()
    {
        this("", "");
    }

    public User(int id, String username, String password)
    {
        this(username, password);
        this.id = id;
    }

    public User(String username, String password) {
        this.id = 0;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ID: " + id + " username: " + username + " password: " + password;
    }
}
