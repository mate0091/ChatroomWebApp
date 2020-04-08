package m8w.ChatroomApp;

import DataAccess.DAOI;
import DataAccess.RoomDAO;
import Models.Room;
import Models.User;
import DataAccess.UserDAO;

import java.util.List;

public class ChatroomAppApplication {

	public static void main(String[] args)
	{
		UserDAO userDAO = new UserDAO();

		User u = new User("testUser2", "testPassword2");

		//userDAO.delete(userDAO.findById(2));

		System.out.println("Elements from User:\n");

		List<User> res = userDAO.findAll();

		res.forEach(System.out::println);

		DAOI<Room> roomDAO = new RoomDAO();

		Room r = new Room("regular");

		roomDAO.insert(r);

		List<Room> rooms = roomDAO.findAll();

		System.out.println("\nElements from Room:\n");
		rooms.forEach(System.out::println);
	}

}
