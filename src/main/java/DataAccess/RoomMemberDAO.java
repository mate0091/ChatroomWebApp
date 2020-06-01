package DataAccess;

import Models.RoomMember;
import MySQLConnection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoomMemberDAO implements DAOI<RoomMember>
{
    @Override
    public List<RoomMember> findAll() {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.room_member;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);

            rs = stat.executeQuery();

            return createRoomMembers(rs);
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
    public RoomMember findById(int id) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.room_member where id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, id);

            rs = stat.executeQuery();

            return createRoomMembers(rs).get(0);
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
    public int insert(RoomMember obj) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "INSERT INTO chatroom.room_member (user_id, room_id, role) VALUES(?, ?, ?);";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stat.setInt(1, obj.getUser_id());
            stat.setInt(2, obj.getRoom_id());
            stat.setString(3, obj.getRole());

            System.out.println("Query: " + query + "\n");

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
    public boolean update(int id, RoomMember obj2) {
        Connection conn = null;
        PreparedStatement stat = null;

        String query = "UPDATE chatroom.room_member SET user_id=?, room_id=?, role=? WHERE id=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, obj2.getUser_id());
            stat.setInt(2, obj2.getRoom_id());
            stat.setString(3, obj2.getRole());
            stat.setInt(4, id);

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

        String query = "DELETE FROM chatroom.room_member where id=?;";

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

    public List<RoomMember> createRoomMembers(ResultSet rs)
    {
        List<RoomMember> results = new ArrayList<>();

        try
        {
            while(rs.next())
            {
                RoomMember instance = new RoomMember(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("room_id"), rs.getString("role"));
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
