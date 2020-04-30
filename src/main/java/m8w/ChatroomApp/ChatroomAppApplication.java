package m8w.ChatroomApp;

import DataAccess.DAOI;
import DataAccess.MessageDAO;
import DataAccess.UserDAO;
import HTMLgenerators.*;
import HTMLgenerators.EditPage.UserEditPageGenerator;
import HTMLgenerators.ShowPage.MessageShowPageGenerator;
import Models.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChatroomAppApplication {

	public static void main(String[] args) throws IOException
	{
		try {
			Server serv = new Server(80);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
