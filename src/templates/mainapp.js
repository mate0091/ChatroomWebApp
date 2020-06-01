var rooms = [];

const textarea = document.getElementById('textarea');
const roomList = document.getElementById("list");
var currentroom = 0;

textarea.addEventListener('keydown', event =>
{
  if (event.isComposing || event.keyCode === 229) {
    return;
  }

  if(event.key == 'Enter')
  {
    sendMessage(textarea.value);

    textarea.value = null;
  }
})


function createMessageBox(msg)
{
  $('#messageContainer').append('<div class=\'conatiner\'>' + '<p>' + msg + '</p></div>');
}

function sendMessage(data)
{
  var msg = {
    "command": "message",
    "content":
    {
      "username": username,
      "room_id": parseInt(currentroom),
      "data": data
    }
  };

  sock.send(JSON.stringify(msg));
}

let sock = new WebSocket("ws://localhost:8887");
let signInInfo = {
  "command": "authenticate",
  "credentials":
  {
    "username": username,
    "password": password
  }
};

sock.onopen = function(e)
{
  createMessageBox("Connected");
  sock.send(JSON.stringify(signInInfo));
}

sock.onmessage = function(e)
{
  let cmd = JSON.parse(e.data);

  if(cmd.rooms != null)
  {
    fillRooms(cmd.rooms);
  }

  else if (cmd.newRoomMessages != null)
  {
    fillNewRoomMessages(cmd.newRoomMessages);
  }

  else if(cmd.message != null)
  {
    if(cmd.message.room_id == currentroom)
    createMessageBox(JSON.stringify(cmd.message));
  }
}

sock.onclose = function(e)
{
  alert("Socket closed");
}

sock.onerror = function(e)
{
  alert("An error occured");
}


function fillRooms(r)
{
  roomList.innerHTML = "";

  for(item of r)
  {
    let listItem = document.createElement("li");
    let node = document.createTextNode(item);
    listItem.className = "list-group-item";
    listItem.setAttribute("onclick", "activateRoom(" + item + ")");
    listItem.appendChild(node);
    roomList.appendChild(listItem);
  }
}

function activateRoom(id)
{
  $('#messageContainer').html("");

  for(item of roomList.children)
  {
    item.classList.remove("active");

    if(id == item.innerText)
    {
      item.classList.add("active");
      currentroom = item.innerText;

      let requestObj = {
        "command": "changeroom",
        "content":
        {
          "room_id": parseInt(currentroom)
        }
      }

      sock.send(JSON.stringify(requestObj));
    }
  }
}

function fillNewRoomMessages(data)
{
  for(item of data)
  {
    createMessageBox(item);
  }
}
