package HTMLgenerators;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AppPageGenerator extends HTMLGenerator
{
    private String username;
    private String password;

    public AppPageGenerator(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    @Override
    protected String header() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial scale=1\">\n" +
                "    <title>Chat</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "    <style>\n" +
                "      #parent\n" +
                "      {\n" +
                "        position: relative;\n" +
                "      }\n" +
                "\n" +
                "      #child\n" +
                "      {\n" +
                "        position: fixed;\n" +
                "        bottom: 0;\n" +
                "      }\n" +
                "  #messageContainer\n" +
                "      {\n" +
                "        padding-bottom: 100px;\n" +
                "      }" +
                "\n" +
                "    </style>" +
                "</head>\n";
    }

    @Override
    protected String body()
    {
        List<String> mainAppJS = null;

        try {
            mainAppJS = Files.readAllLines(Paths.get("src/templates/mainapp.js"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder mainapp = new StringBuilder();
        StringBuilder htmlTemplate = new StringBuilder();

        for (String i : mainAppJS)
        {
            mainapp.append(i);
        }

        return
                "<body>\n" +
                        "  <div class='row container'>\n" +
                        "    <div class=\"col-2\" id=\"list-area\">\n" +
                        "      <ul class=\"list-group\" id=\"list\">\n" +
                        "        <li class='list-group-item active'>Room1</li>\n" +
                        "        <li class='list-group-item'>Room2</li>\n" +
                        "        <li class='list-group-item'>Room3</li>\n" +
                        "      </ul>\n" +
                        "    </div>\n" +
                        "    <div class='col' id='messageContainer'>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "\n" +
                        "  <div class='col text-center' id='child'>\n" +
                        "    <textarea class='form-control' rows=2 id='textarea'></textarea>\n" +
                        "  </div>\n" +
                        "  <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n" +
                        "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\n" +
                        "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>\n" +
                        "  <script>" +
                        "const username=\"" + this.username + "\";\n"+
                        "const password=\"" + this.password + "\";\n"+
                        "var rooms = [];\n" +
                        "\n" +
                        "const textarea = document.getElementById('textarea');\n" +
                        "const roomList = document.getElementById(\"list\");\n" +
                        "var currentroom = 0;\n" +
                        "\n" +
                        "textarea.addEventListener('keydown', event =>\n" +
                        "{\n" +
                        "  if (event.isComposing || event.keyCode === 229) {\n" +
                        "    return;\n" +
                        "  }\n" +
                        "\n" +
                        "  if(event.key == 'Enter')\n" +
                        "  {\n" +
                        "    sendMessage(textarea.value);\n" +
                        "\n" +
                        "    textarea.value = null;\n" +
                        "  }\n" +
                        "})\n" +
                        "\n" +
                        "\n" +
                        "function createMessageBox(msg)\n" +
                        "{\n" +
                        "  $('#messageContainer').append(msg);\n" +
                        "}\n" +
                        "\n" +
                        "function sendMessage(data)\n" +
                        "{\n" +
                        "  var msg = {\n" +
                        "    \"command\": \"message\",\n" +
                        "    \"content\":\n" +
                        "    {\n" +
                        "      \"username\": username,\n" +
                        "      \"room_id\": parseInt(currentroom),\n" +
                        "      \"data\": data\n" +
                        "    }\n" +
                        "  };\n" +
                        "\n" +
                        "  sock.send(JSON.stringify(msg));\n" +
                        "}\n" +
                        "\n" +
                        "let sock = new WebSocket(\"ws://localhost:8887\");\n" +
                        "let signInInfo = {\n" +
                        "  \"command\": \"authenticate\",\n" +
                        "  \"credentials\":\n" +
                        "  {\n" +
                        "    \"username\": username,\n" +
                        "    \"password\": password\n" +
                        "  }\n" +
                        "};\n" +
                        "\n" +
                        "sock.onopen = function(e)\n" +
                        "{\n" +
                        "  createMessageBox(\"Connected\");\n" +
                        "  sock.send(JSON.stringify(signInInfo));\n" +
                        "}\n" +
                        "\n" +
                        "sock.onmessage = function(e)\n" +
                        "{\n" +
                        "  let cmd = JSON.parse(e.data);\n" +
                        "\n" +
                        "  if(cmd.rooms != null)\n" +
                        "  {\n" +
                        "    fillRooms(cmd.rooms);\n" +
                        "  }\n" +
                        "\n" +
                        "  else if (cmd.newRoomMessages != null)\n" +
                        "  {\n" +
                        "    fillNewRoomMessages(cmd.newRoomMessages);\n" +
                        "  }\n" +
                        "\n" +
                        "  else if(cmd.message != null)\n" +
                        "  {\n" +
                        "    if(cmd.room_id == currentroom)\n" +
                        "    createMessageBox(cmd.message);\n" +
                        "  }\n" +
                        "}\n" +
                        "\n" +
                        "sock.onclose = function(e)\n" +
                        "{\n" +
                        "  alert(\"Socket closed\");\n" +
                        "}\n" +
                        "\n" +
                        "sock.onerror = function(e)\n" +
                        "{\n" +
                        "  alert(\"An error occured\");\n" +
                        "}\n" +
                        "\n" +
                        "\n" +
                        "function fillRooms(r)\n" +
                        "{\n" +
                        "  roomList.innerHTML = \"\";\n" +
                        "\n" +
                        "  for(item of r)\n" +
                        "  {\n" +
                        "    let listItem = document.createElement(\"li\");\n" +
                        "    let node = document.createTextNode(item);\n" +
                        "    listItem.className = \"list-group-item\";\n" +
                        "    listItem.setAttribute(\"onclick\", \"activateRoom(\" + item + \")\");\n" +
                        "    listItem.appendChild(node);\n" +
                        "    roomList.appendChild(listItem);\n" +
                        "  }\n" +
                        "}\n" +
                        "\n" +
                        "function activateRoom(id)\n" +
                        "{\n" +
                        "  $('#messageContainer').html(\"\");\n" +
                        "\n" +
                        "  for(item of roomList.children)\n" +
                        "  {\n" +
                        "    item.classList.remove(\"active\");\n" +
                        "\n" +
                        "    if(id == item.innerText)\n" +
                        "    {\n" +
                        "      item.classList.add(\"active\");\n" +
                        "      currentroom = item.innerText;\n" +
                        "\n" +
                        "      let requestObj = {\n" +
                        "        \"command\": \"changeroom\",\n" +
                        "        \"content\":\n" +
                        "        {\n" +
                        "          \"room_id\": parseInt(currentroom)\n" +
                        "        }\n" +
                        "      }\n" +
                        "\n" +
                        "      sock.send(JSON.stringify(requestObj));\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n" +
                        "\n" +
                        "function fillNewRoomMessages(data)\n" +
                        "{\n" +
                        "  for(item of data)\n" +
                        "  {\n" +
                        "    let message = item.message;\n" +
                        "    createMessageBox(message);\n" +
                        "  }\n" +
                        "}" +
                        "</script>" +
                "\n" +
                "</body>";
    }
}
