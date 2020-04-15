package m8w.ChatroomApp;

import DataAccess.DAOI;
import DataAccess.UserDAO;
import Models.User;
import com.google.common.base.Splitter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

public class AdminHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        URI uri = httpExchange.getRequestURI();

        String requestMethod = httpExchange.getRequestMethod();
        if(requestMethod.compareTo("GET") == 0)
        {
            System.out.println();
            //process get method
            byte[] response1 = Files.readAllBytes(Paths.get("src/templates/admin_login.html"));

            httpExchange.sendResponseHeaders(200, response1.length);
            OutputStream out = httpExchange.getResponseBody();
            out.write(response1);
            out.close();
        }

        else if (requestMethod.compareTo("POST") == 0)
        {
            InputStream is = httpExchange.getRequestBody();
            int i;
            StringBuilder resp = new StringBuilder();

            while((i = is.read()) != -1)
            {
                resp.append((char)i);
            }

            Map<String, String> pairs = Splitter.on("&").withKeyValueSeparator("=").split(resp.toString());

            if(pairs.get("login").compareTo("admin") == 0 && pairs.get("pass").compareTo("password") == 0)
            {
                byte[] response1 = Files.readAllBytes(Paths.get("src/templates/login_ok.html"));

                httpExchange.sendResponseHeaders(200, response1.length);
                OutputStream out = httpExchange.getResponseBody();
                out.write(response1);
                out.close();
            }

            else
            {
                byte[] response1 = Files.readAllBytes(Paths.get("src/templates/admin_login.html"));

                httpExchange.sendResponseHeaders(200, response1.length);
                OutputStream out = httpExchange.getResponseBody();
                out.write(response1);
                out.close();
            }
        }
    }
}
