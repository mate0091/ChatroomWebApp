package Controllers;

import DataAccess.DAOI;
import DataAccess.UserDAO;
import HTMLgenerators.AppPageGenerator;
import HTMLgenerators.HTMLGenerator;
import Models.User;
import com.google.common.base.Splitter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ClientHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        URI uri = httpExchange.getRequestURI();

        String requestMethod = httpExchange.getRequestMethod();
        if(requestMethod.compareTo("GET") == 0)
        {
            //process get method
            byte[] response1 = Files.readAllBytes(Paths.get("src/templates/user_login.html"));

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

            DAOI<User> userDao = new UserDAO();
            List<User> users = userDao.findAll();

            String typedUser = pairs.get("login");
            String typedPass = pairs.get("pass");

            boolean flag = false;

            for (User user : users)
            {
                if(user.getUsername().compareTo(typedUser) == 0)
                {
                    if(user.getPassword().compareTo(typedPass) == 0)
                    {
                        flag = true;
                    }

                    break;
                }
            }

            if(flag)
            {
                HTMLGenerator gen = new AppPageGenerator(pairs.get("login"), pairs.get("pass"));
                byte[] response1 = gen.generate().getBytes();

                httpExchange.sendResponseHeaders(200, response1.length);
                OutputStream out = httpExchange.getResponseBody();
                out.write(response1);
                out.close();
            }

            else
            {
                byte[] response1 = Files.readAllBytes(Paths.get("src/templates/user_login.html"));

                httpExchange.sendResponseHeaders(200, response1.length);
                OutputStream out = httpExchange.getResponseBody();
                out.write(response1);
                out.close();
            }
        }
    }
}
