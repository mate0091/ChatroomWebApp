package m8w.ChatroomApp;

import DataAccess.*;
import Models.*;
import m8w.ChatroomApp.MessageGenerator.MessageFactory;
import m8w.ChatroomApp.MessageGenerator.MessageType;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class ChatServer extends WebSocketServer
{
    Map<Integer, List<WebSocket>> rooms;

    public ChatServer(int port)
    {
        super(new InetSocketAddress(port));
        start();
        initRooms();
    }

    public ChatServer(InetSocketAddress address) {
        super(address);
        start();
        initRooms();
    }

    private void initRooms()
    {
        rooms = new HashMap<>();
        DAOI<Room> roomDAO = new RoomDAO();

        for (Room i : roomDAO.findAll())
        {
            rooms.put(i.getId(), new ArrayList<>());
        }

        rooms.forEach((k, v) ->
        {
            System.out.println("Key: " + k + "Value: " + v);
        });
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake)
    {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b)
    {
        //remove websocket from all possible rooms
        rooms.forEach((k, v) ->
        {
            for (int j = 0; j < v.size(); j++)
            {
                if(v.get(j) == webSocket)
                {
                    v.remove(j);
                }
            }
        });
    }

    @Override
    public void onMessage(WebSocket webSocket, String s)
    {
        JSONObject obj=null;

        try {
            obj = (JSONObject) new JSONParser().parse(s);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String cmd = (String) obj.get("command");

        System.out.println("Received command :" + (String)obj.get("command"));

        if(cmd.compareToIgnoreCase("authenticate") == 0)
        {
            authenticate(webSocket, new JSONObject((Map) obj.get("credentials")));
        }

        else if(cmd.compareToIgnoreCase("changeroom") == 0)
        {
            changeRoom(webSocket, new JSONObject((Map) obj.get("content")));
        }

        else if(cmd.compareToIgnoreCase("message") == 0)
        {
            sendMessage(new JSONObject((Map) obj.get("content")));
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e)
    {
        rooms.forEach((k, v) ->
        {
            for (int j = 0; j < v.size(); j++)
            {
                if(v.get(j) == webSocket)
                {
                    v.remove(j);
                }
            }
        });
    }

    @Override
    public void onStart()
    {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }


    private void authenticate(WebSocket socket, JSONObject obj)
    {
        String username = (String)obj.get("username");
        String password = (String)obj.get("password");

        User u = ComplexDAO.findUserByName(username);

        if(password.compareTo(u.getPassword()) == 0)
        {
            //login ok!
            JSONObject signInObj = new JSONObject();
            signInObj.put("message", "Welcome to the chat server " + username);
            socket.send(signInObj.toJSONString());

            //put it into rooms
            List<RoomMember> rooms = ComplexDAO.getRoomsForUser(u.getId());

            rooms.forEach(e ->
            {
                this.rooms.get(e.getRoom_id()).add(socket);
            });

            //send rooms
            JSONArray array = new JSONArray();

            rooms.forEach(e ->
            {
                array.add(e.getRoom_id());
            });

            JSONObject response = new JSONObject();

            response.put("rooms", array);

            System.out.println(response.toJSONString());

            socket.send(response.toJSONString());
        }

        else
        {
            socket.close();
        }
    }

    private void changeRoom(WebSocket socket, JSONObject obj)
    {
        //send every message from the given room
        int room_id = toIntExact((Long) obj.get("room_id"));
        System.out.println(room_id);

        List<Message> msgs = ComplexDAO.getMessagesForRoom(room_id);

        System.out.println(msgs);

        JSONObject response = new JSONObject();

        List<JSONObject> messages = new ArrayList<>();

        for(Message msg : msgs)
        {
            MessageType messageToSend = MessageFactory.fromMessage(msg);
            JSONObject temp = messageToSend.getMessage();
            messages.add(temp);
        }

        response.put("newRoomMessages", messages);

        System.out.println(response.toJSONString());

        socket.send(response.toJSONString());
    }

    private void sendMessage(JSONObject content)
    {
        int room_id = toIntExact((Long) content.get("room_id"));

        List<WebSocket> socketsToUpdate = rooms.get(room_id);

        //put into database first, then send the last to all

        DAOI<Message> messageDAOI = new MessageDAO();
        DAOI<RoomMsg> roomMsgDAOI = new RoomMsgDAO();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String date = dtf.format(now);

        Message msg = messageDAOI.findById(messageDAOI.insert(new Message(date, content.toJSONString())));

        roomMsgDAOI.insert(new RoomMsg(room_id, msg.getId()));

        MessageType messageToSend = MessageFactory.fromMessage(msg);

        JSONObject response = messageToSend.getMessage();

        for (WebSocket socket : socketsToUpdate)
        {
            socket.send(response.toJSONString());
        }
    }
}
