package m8w.ChatroomApp.MessageGenerator;

import Models.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MessageFactory
{
    public static MessageType fromMessage(Message msg)
    {
        JSONObject content = null;

        try
        {
            content = (JSONObject) new JSONParser().parse(msg.getContent());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String data = (String) content.get("data");

        if(data.endsWith(".gif") || data.endsWith(".jpg") || data.endsWith(".png"))
        {
            return new ImgMessage(msg);
        }

        else return new TextMessage(msg);
    }
}
