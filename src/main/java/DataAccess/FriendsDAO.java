package DataAccess;

import Models.Friends;
import MySQLConnection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FriendsDAO implements DAOI<Friends>
{

    @Override
    public List<Friends> findAll() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.friends;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);

            rs = stat.executeQuery();

            return createFriends(rs);
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
    public Friends findById(int id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.friends where id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, id);

            rs = stat.executeQuery();

            return createFriends(rs).get(0);
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
    public int insert(Friends obj) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "INSERT INTO chatroom.user (userid_1, userid_2) VALUES(?, ?);";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stat.setInt(1, obj.getUserID1());
            stat.setInt(2, obj.getUserID2());

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
    public boolean update(int id, Friends obj2) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "UPDATE chatroom.friends SET userid_1=?, userid_2=?  WHERE id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, obj2.getUserID1());
            stat.setInt(2, obj2.getUserID2());
            stat.setInt(3, id);

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
    public boolean delete(int id) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "DELETE FROM chatroom.friends where id=?;";

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

    private List<Friends> createFriends(ResultSet rs)
    {
        List<Friends> results = new ArrayList<>();

        try
        {
            while(rs.next())
            {
                Friends instance = new Friends(rs.getInt("id"), rs.getInt("userid_1"), rs.getInt("userid_2"));
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
