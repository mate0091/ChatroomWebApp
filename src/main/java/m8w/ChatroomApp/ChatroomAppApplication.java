package m8w.ChatroomApp;

import DataAccess.ComplexDAO;
import DataAccess.DAOI;
import DataAccess.MessageDAO;
import DataAccess.UserDAO;
import HTMLgenerators.*;
import HTMLgenerators.EditPage.UserEditPageGenerator;
import HTMLgenerators.ShowPage.MessageShowPageGenerator;
import Models.Message;
import Models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChatroomAppApplication {

	public static void main(String[] args) throws IOException
	{
		try {
			HTTPServer httpserv = new HTTPServer(80);
			ChatServer chatServer = new ChatServer(8887);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
