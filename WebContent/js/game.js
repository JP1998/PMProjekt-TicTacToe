// TODO: Event-Listener für Canvas implementieren

function initGame()
{
  document.getElementById('gameCanvas').addEventListener('click', handleCickOnCanvas);
}

function handleCickOnCanvas(event)
{
  x = event.offsetX;
  y = event.offsetY;
  alert("X: " + x + " Y: " + y);
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

