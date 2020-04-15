package m8w.ChatroomApp;

import DataAccess.DAOI;
import DataAccess.RoomDAO;
import Models.Room;
import Models.User;
import DataAccess.UserDAO;

import java.net.ServerSocket;
import java.util.List;

public class ChatroomAppApplication {

	public static void main(String[] args)
	{
		try
		{
			Server serv = new Server(8080);


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
