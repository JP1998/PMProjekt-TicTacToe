// Initialisiert das Spiel, indem es das dem Canvas den Event-Listener hinzufügt.
function initGame()
{
  document.getElementById('gameCanvas').addEventListener('click', handleCickOnCanvas);
}

// Funktion für das Klick-Ereignis auf dem Spielfeld
function handleCickOnCanvas(event)
{
  x = event.offsetX;
  y = event.offsetY;
}

// Berechnet anhand der übergebenen Koordinaten die Nummer des Feldes und gibt diese zurück.
function getField(x, y)
{
  field = Math.floor(x / 300);
  field = field + 3 * Math.floor(y / 200);
  field = field + 1;
  return field;
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

