package MySQLConnection;
import java.sql.*;

public class ConnectionFactory
{
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DBURL = "jdbc:mysql://localhost:3306/chatroom";
    public static final String USER = "root";
    public static final String PASS = "Taviranyito009";

    private static ConnectionFactory INSTANCE = new ConnectionFactory();

    private ConnectionFactory()
    {
        try
        {
            Class.forName(DRIVER);
        }

        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private Connection createConnection()
    {
        Connection con = null;

        try
        {
            con = DriverManager.getConnection(DBURL, USER, PASS);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return con;
    }

    public static Connection getConnection()
    {
        return INSTANCE.createConnection();
    }

    public static void close(Connection con)
    {
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement st)
    {
        if(st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs)
    {
        if(rs != null)
        {
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
