package m8w.ChatroomApp;

public class HelloServer
{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString()
    {
        return "Hello" + this.name;
    }
}
