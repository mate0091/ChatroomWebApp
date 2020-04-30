package Controllers;

import DataAccess.*;
import HTMLgenerators.EditPage.*;
import HTMLgenerators.HTMLGenerator;
import HTMLgenerators.ShowPage.*;
import Models.*;
import MySQLConnection.ConnectionFactory;
import com.google.common.base.Splitter;
import com.mysql.cj.jdbc.Blob;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminCrud implements HttpHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        System.out.println(httpExchange.getRequestURI().getPath());

        if(httpExchange.getRequestMethod().compareToIgnoreCase("POST") == 0)
        {
            System.out.println("POST\n");
            editOrInsert(httpExchange);
        }

        else {
            if (httpExchange.getRequestURI().getQuery() == null) {
                noParams(httpExchange);
            } else {
                Map<String, String> query = Splitter.on("&").withKeyValueSeparator("=").split(httpExchange.getRequestURI().getQuery());
                handleAction(httpExchange, query);
            }
        }
    }

    private void noParams(HttpExchange httpExchange) throws IOException
    {
        byte[] response = Files.readAllBytes(Paths.get("src/templates/Admin_crud.html"));
        httpExchange.sendResponseHeaders(200, response.length);
        OutputStream out = httpExchange.getResponseBody();
        out.write(response);
        out.close();
    }

    private void handleAction(HttpExchange httpExchange, Map<String, String> query) throws IOException
    {
        if(query.get("action").equalsIgnoreCase("findall"))
        {
            sendTable(httpExchange, query);
        }

        else if(query.get("action").equalsIgnoreCase("delete"))
        {
            delete(httpExchange, query);
        }

        else if(query.get("action").equalsIgnoreCase("edit"))
        {
            sendEdit(httpExchange, query, true);
        }

        else if(query.get("action").equalsIgnoreCase("insert"))
        {
            sendEdit(httpExchange, query, false);
        }
    }

    private void sendTable(HttpExchange httpExchange, Map<String, String> query) throws IOException
    {
        String name = query.get("table");

        DAOI<?> dao = instantiateDAO(name);
        HTMLGenerator htmlGen = showPageHTML(dao, name);

        byte[] response = htmlGen.generate().getBytes();
        httpExchange.sendResponseHeaders(200, response.length);
        OutputStream out = httpExchange.getResponseBody();
        out.write(response);
        out.close();
    }

    private void sendEdit(HttpExchange httpExchange, Map<String, String> query, boolean edit) throws IOException
    {
        int id = 0;
        String name = query.get("table");
        DAOI<?> dao = instantiateDAO(name);

        if(edit) id = Integer.parseInt(query.get("id"));

        HTMLGenerator gen = editPageHTML(dao, name, id, edit);

        byte[] response = gen.generate().getBytes();
        httpExchange.sendResponseHeaders(200, response.length);
        OutputStream out = httpExchange.getResponseBody();
        out.write(response);
        out.close();
    }

    private void delete(HttpExchange exchange, Map<String, String> query) throws IOException
    {
        int id = Integer.parseInt(query.get("id"));
        String table = query.get("table");

        DAOI<?> dao = instantiateDAO(table);

        dao.delete(id);

        Headers headers = exchange.getResponseHeaders();
        headers.set("Location", "/admin/CRUD");

        exchange.sendResponseHeaders(302, 0);
    }

    private void editOrInsert(HttpExchange exchange) throws IOException
    {
        Map<String, String> query = Splitter.on("&").withKeyValueSeparator("=").split(exchange.getRequestURI().getQuery());

        StringBuilder reqBody = new StringBuilder();
        InputStream is = exchange.getRequestBody();
        int i;
        while((i = is.read()) != -1)
        {
            reqBody.append((char)i);
        }

        Map<String, String> params = Splitter.on("&").withKeyValueSeparator("=").split(reqBody);

        String table = query.get("table");
        //DAOI<Object> dao = instantiateDAO(table);
        String action = query.get("action");
        Object item = null;

        boolean edit = (action.compareToIgnoreCase("edit") == 0);


        if(table.compareToIgnoreCase("Friends") == 0)
        {
            DAOI<Friends> dao = new FriendsDAO();
            if(edit)
            {
                item = new Friends(Integer.parseInt(params.get("id")), Integer.parseInt(params.get("userId1")), Integer.parseInt(params.get("userId2")));
                dao.update(Integer.parseInt(params.get("id")), (Friends) item);
            }
            else
            {
                item = new Friends(Integer.parseInt(params.get("userId1")), Integer.parseInt(params.get("userId2")));
                dao.insert((Friends)item);
            }
        }

        else if(table.compareToIgnoreCase("Message") == 0)
        {
            DAOI<Message> dao = new MessageDAO();
            byte[] bytes = params.get("content").getBytes("UTF-8");
            java.sql.Blob blob = null;
            try {
                blob = ConnectionFactory.getConnection().createBlob();
                blob.setBytes(1, bytes);
            }

            catch (SQLException e)
            {
                e.printStackTrace();
            }

            if(edit)
            {
                item = new Message(Integer.parseInt(params.get("id")), params.get("dateTime"), blob);
                dao.update(Integer.parseInt(params.get("id")), (Message) item);
            }

            else {
                item = new Message(params.get("dateTime"), blob);
                dao.insert((Message) item);
            }
        }

        else if(table.compareToIgnoreCase("Room") == 0)
        {
            DAOI<Room> dao = new RoomDAO();
            if(edit)
            {
                item = new Room(Integer.parseInt(params.get("id")), params.get("type"));
                dao.update(Integer.parseInt(params.get("id")), (Room) item);
            }
            else
            {
                item = new Room(params.get("type"));
                dao.insert((Room)item);
            }
        }

        else if(table.compareToIgnoreCase("RoomMember") == 0)
        {
            DAOI<RoomMember> dao = new RoomMemberDAO();
            if(edit)
            {
                item = new RoomMember(Integer.parseInt(params.get("id")), Integer.parseInt(params.get("user_id")), Integer.parseInt(params.get("room_id")), params.get("role"));
                dao.update(Integer.parseInt(params.get("id")), (RoomMember) item);
            }

            else
            {
                item = new RoomMember(Integer.parseInt(params.get("user_id")), Integer.parseInt(params.get("room_id")), params.get("role"));
                dao.insert((RoomMember) item);
            }
        }

        else if(table.compareToIgnoreCase("RoomMsg") == 0)
        {
            DAOI<RoomMsg> dao = new RoomMsgDAO();
            if(edit)
            {
                item = new RoomMsg(Integer.parseInt(params.get("id")), Integer.parseInt(params.get("room_id")), Integer.parseInt(params.get("msg_id")));
                dao.update(Integer.parseInt(params.get("id")), (RoomMsg) item);
            }
            else
            {
                item = new RoomMsg(Integer.parseInt(params.get("room_id")), Integer.parseInt(params.get("msg_id")));
                dao.insert((RoomMsg) item);
            }
        }

        else if(table.compareToIgnoreCase("User") == 0)
        {
            System.out.println("USER!!\n");
            DAOI<User> dao = new UserDAO();
            if(edit)
            {
                item = new User(Integer.parseInt(params.get("id")), params.get("username"), params.get("password"));
                dao.update(Integer.parseInt(params.get("id")), (User)item);
            }
            else
            {
                item = new User(params.get("username"), params.get("password"));
                dao.insert((User) item);
            }
        }

        Headers headers = exchange.getResponseHeaders();
        headers.set("Location", "/admin/CRUD");

        exchange.sendResponseHeaders(302, 0);
    }

    private DAOI<?> instantiateDAO(String name)
    {
        DAOI<?> dao;

        if(name.equalsIgnoreCase("user"))
        {
            dao = new UserDAO();
        }

        else if(name.equalsIgnoreCase("friends"))
        {
            dao = new FriendsDAO();
        }

        else if(name.equalsIgnoreCase("message"))
        {
            dao = new MessageDAO();
        }

        else if(name.equalsIgnoreCase("room"))
        {
            dao = new RoomDAO();
        }

        else if(name.equalsIgnoreCase("roommember"))
        {
            dao = new RoomMemberDAO();
        }

        else
        {
            dao = new RoomMsgDAO();
        }

        return dao;
    }

    private HTMLGenerator showPageHTML(DAOI<?> dao, String name)
    {
        HTMLGenerator gen;
        if(name.equalsIgnoreCase("user"))
        {
            gen = new UserShowPageGenerator((List<User>) dao.findAll());
        }

        else if(name.equalsIgnoreCase("friends"))
        {
            gen = new FriendsShowPageGenerator((List<Friends>) dao.findAll());
        }

        else if(name.equalsIgnoreCase("message"))
        {
            gen = new MessageShowPageGenerator((List<Message>) dao.findAll());
        }

        else if(name.equalsIgnoreCase("room"))
        {
            gen = new RoomShowPageGenerator((List<Room>) dao.findAll());
        }

        else if(name.equalsIgnoreCase("roommember"))
        {
            gen = new RoomMemberShowPageGenerator((List<RoomMember>) dao.findAll());
        }

        else
        {
            gen = new RoomMsgShowPageGenerator((List<RoomMsg>) dao.findAll());
        }

        return gen;
    }

    private HTMLGenerator editPageHTML(DAOI<?> dao, String name, int id, boolean edit)
    {
        HTMLGenerator gen;
        if(name.equalsIgnoreCase("user"))
        {
            if(edit) gen = new UserEditPageGenerator((User) dao.findById(id), edit);
            else gen = new UserEditPageGenerator(null, edit);
        }

        else if(name.equalsIgnoreCase("friends"))
        {
            if(edit) gen = new FirendsEditPageGenerator((Friends) dao.findById(id), edit);
            else gen = new FirendsEditPageGenerator(null, edit);
        }

        else if(name.equalsIgnoreCase("message"))
        {
            if(edit) gen = new MessageEditPageGenerator((Message) dao.findById(id), edit);
            else gen = new MessageEditPageGenerator(null, edit);
        }

        else if(name.equalsIgnoreCase("room"))
        {
            if(edit) gen = new RoomEditPageGenerator((Room) dao.findById(id), edit);
            else gen = new RoomEditPageGenerator(null, edit);
        }

        else if(name.equalsIgnoreCase("roommember"))
        {
            if(edit) gen = new RoomMemberEditPageGenerator((RoomMember) dao.findById(id), edit);
            else gen = new RoomMemberEditPageGenerator(null, edit);
        }

        else
        {
            if(edit) gen = new RoomMsgEditPageGenerator((RoomMsg) dao.findById(id), edit);
            else gen = new RoomMsgEditPageGenerator(null, edit);
        }

        return gen;
    }
}
