// TODO: Event-Listener für Canvas implementieren
document.getElementById('singlePlayerCanvas').addEventListener('click', handleCickOnCanvas);

function handleCickOnCanvas(event)
{
  x = event.offsetX;
  y = event.offsetY;
}
// TODO: XMLHttpRequest Objekt setzen
// TODO: Aktualisierung des Feldes bearbeiten

var request = new XMLHttpRequest();

request.open("GET", "localhost://", true);

request.onreadystatechange = function recieve() 
{
  if (request.readyState == request.DONE) 
  {
    answer = request.responseText;
  }
}

