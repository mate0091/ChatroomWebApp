package DataAccess;

import Models.Message;
import Models.Room;
import Models.RoomMember;
import Models.User;
import MySQLConnection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ComplexDAO
{
    public static User findUserByName(String username)
    {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "Select * FROM chatroom.user where username=?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setString(1, username);

            rs = stat.executeQuery();

            return new UserDAO().createUsers(rs).get(0);
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

    public static List<RoomMember> getRoomsForUser(int userId)
    {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "SELECT distinct rm.*\n" +
                "FROM user as u\n" +
                "\tinner join\n" +
                "    room_member as rm\n" +
                "    on u.id = rm.user_id\n" +
                "    where u.id = ?;";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1, userId);

            rs = stat.executeQuery();

            RoomMemberDAO roomMemberDAO = new RoomMemberDAO();

            return roomMemberDAO.createRoomMembers(rs);
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

    public static List<Message> getMessagesForRoom(int roomId)
    {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "SELECT distinct m.*\n" +
                "FROM room_msg as rm\n" +
                "    inner join\n" +
                "    message as m\n" +
                "    on m.id = rm.message_id\n" +
                "    \n" +
                "    where rm.room_id = ?";

        try
        {
            conn = ConnectionFactory.getConnection();
            stat = conn.prepareStatement(query);
            stat.setInt(1,roomId);

            rs = stat.executeQuery();

            return new MessageDAO().createMessages(rs);
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

    public static void insertMessageInRoom()
    {
        //first insert message, get back the inserted value, then
    }
}
