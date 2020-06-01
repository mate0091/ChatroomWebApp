package DataAccess;

import Models.RoomMsg;
import MySQLConnection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoomMsgDAO implements DAOI<RoomMsg>
{
    @Override
    public List<RoomMsg> findAll() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.room_msg;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);

            rs = stat.executeQuery();

            return createRoomMessages(rs);
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
    public RoomMsg findById(int id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.room_msg where id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, id);

            rs = stat.executeQuery();

            return createRoomMessages(rs).get(0);
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
    public int insert(RoomMsg obj) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "INSERT INTO chatroom.room_msg (room_id, message_id) VALUES(?, ?);";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stat.setInt(1, obj.getRoom_id());
            stat.setInt(2, obj.getMsg_id());

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
    public boolean update(int id, RoomMsg obj2) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "UPDATE chatroom.room_msg SET room_id=?, message_id=? WHERE id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, obj2.getRoom_id());
            stat.setInt(2, obj2.getMsg_id());
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

        String query = "DELETE FROM chatroom.room_msg where id=?;";

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

    public List<RoomMsg> createRoomMessages(ResultSet rs)
    {
        List<RoomMsg> results = new ArrayList<>();

        try
        {
            while(rs.next())
            {
                RoomMsg instance = new RoomMsg(rs.getInt("id"), rs.getInt("room_id"), rs.getInt("message_id"));
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
