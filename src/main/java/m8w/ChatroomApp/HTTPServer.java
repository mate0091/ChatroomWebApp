package m8w.ChatroomApp;

import Auth.AdminAuth;
import Controllers.AdminCrud;
import Controllers.AdminHandler;
import Controllers.ClientHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;


public class HTTPServer
{
   public HTTPServer(int port) throws Exception
   {
       HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
       server.createContext("/login", new ClientHandler());
       server.createContext("/admin", new AdminHandler());
       HttpContext ct = server.createContext("/admin/CRUD", new AdminCrud());
       //ct.setAuthenticator(new AdminAuth("admin-auth"));
       System.out.println();


       server.setExecutor(null);
       server.start();


   }
}
