package m8w.ChatroomApp.MessageGenerator;

import Models.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TextMessage implements MessageType
{
    private JSONObject output;

    public TextMessage(Message msg)
    {
        //return with simple formatted text

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
        String date = msg.getDateTime();
        String username = (String) content.get("username");

        String html = "<div class=\"container\"><h4>\n" + username + "<small> "+ date + " </small></h4>\n" +
                "<p>" + data + "</p>" +
                "\n" +
                "</div>";

        JSONObject response = new JSONObject();
        response.put("message", html);
        response.put("room_id", content.get("room_id"));

        this.output = response;
    }

    @Override
    public JSONObject getMessage()
    {
        return this.output;
    }
}
