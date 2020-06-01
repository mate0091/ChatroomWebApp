package DataAccess;

import Models.User;
import MySQLConnection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAOI<User>
{
    @Override
    public List<User> findAll()
    {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.user";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);

            rs = stat.executeQuery();

            return createUsers(rs);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(stat);
            ConnectionFactory.close(conn);
        }

        return null;
    }

    @Override
    public User findById(int id)
    {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.user where id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, id);

            rs = stat.executeQuery();

            return createUsers(rs).get(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(stat);
            ConnectionFactory.close(conn);
        }

        return null;
    }

    @Override
    public int insert(User obj)
    {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "INSERT INTO chatroom.user (username, password) VALUES(?, ?);";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stat.setString(1, obj.getUsername());
            stat.setString(2, obj.getPassword());

            stat.executeUpdate();

            ResultSet rs = stat.getGeneratedKeys();
            if(rs != null && rs.next())
            {
                return rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            ConnectionFactory.close(stat);
            ConnectionFactory.close(conn);
        }

        return 0;
    }

    @Override
    public boolean update(int id, User obj2)
    {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "UPDATE chatroom.user SET username=?, password=?  WHERE id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setString(1, obj2.getUsername());
            stat.setString(2, obj2.getPassword());
            stat.setInt(3, id);

            System.out.println(stat.toString());

            stat.executeUpdate();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            ConnectionFactory.close(stat);
            ConnectionFactory.close(conn);
        }

        return false;
    }

    @Override
    public boolean delete(int id)
    {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "DELETE FROM chatroom.user where id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, id);

            stat.executeUpdate();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            ConnectionFactory.close(stat);
            ConnectionFactory.close(conn);
        }

        return false;
    }

    public List<User> createUsers(ResultSet rs)
    {
        List<User> results = new ArrayList<>();

        try
        {
            while(rs.next())
            {
                User instance = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                results.add(instance);
            }

            return results;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
