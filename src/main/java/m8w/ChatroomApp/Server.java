package m8w.ChatroomApp;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;


public class Server
{
   public Server(int port) throws Exception
   {
       HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
       server.createContext("/login", new ClientHandler());
       server.createContext("/admin", new AdminHandler());

       server.setExecutor(null);
       server.start();
   }
}
