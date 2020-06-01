package m8w.ChatroomApp.MessageGenerator;

import Models.Message;
import org.json.simple.JSONObject;

public interface MessageType
{
    JSONObject getMessage();
}
