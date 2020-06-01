package DataAccess;

import Models.Room;
import MySQLConnection.ConnectionFactory;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements DAOI<Room>
{
    @Override
    public List<Room> findAll() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.room;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);

            rs = stat.executeQuery();

            return createRooms(rs);
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
    public Room findById(int id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.room where id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, id);

            rs = stat.executeQuery();

            return createRooms(rs).get(0);
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
    public int insert(Room obj) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "INSERT INTO chatroom.room (type) VALUES(?);";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stat.setString(1, obj.getType());

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
    public boolean update(int id, Room obj2) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "UPDATE chatroom.message SET type=? WHERE id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setString(1, obj2.getType());
            stat.setInt(2, id);

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

        String query = "DELETE FROM chatroom.room where id=?;";

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

    private List<Room> createRooms(ResultSet rs)
    {
        List<Room> results = new ArrayList<>();

        try
        {
            while(rs.next())
            {
                Room instance = new Room(rs.getInt("id"), rs.getString("type"));
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
