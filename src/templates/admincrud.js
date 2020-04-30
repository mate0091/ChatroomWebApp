window.onload = () =>
{
  fill('friends');
}

document.getElementById('nav-friends-tab').setAttribute('onclick', 'fill(\'friends\')');
document.getElementById('nav-message-tab').setAttribute('onclick', 'fill(\'message\')');
document.getElementById('nav-room-tab').setAttribute('onclick', 'fill(\'roommember\')');
document.getElementById('nav-roommember-tab').setAttribute('onclick', 'fill(\'room\')');
document.getElementById('nav-roommsg-tab').setAttribute('onclick', 'fill(\'roommsg\')');
document.getElementById('nav--tab').setAttribute('onclick', 'fill(\'user\')');

function fill(tabname)
{
  var tab = document.getElementById('nav-' + tabname);
  let http_xchg = new XMLHttpRequest();
  http_xchg.open("GET", "/admin/CRUD?action=findall&table=" + tabname, true);
  http_xchg.send();

  http_xchg.onreadystatechange = function()
  {
    if(this.readyState == 4 && this.status == 200)
    {
      tab.innerHTML = this.responseText;
    }
  }
}
