// Initialisiert das Spiel, indem es das dem Canvas den Event-Listener hinzufügt.
var gamestate = {
		turn : false,
		fields : [],
		message : ""
};

function initGame() {
	document.getElementById('gameCanvas').addEventListener('click', handleCickOnCanvas);
	updateGameState();
}

// Funktion für das Klick-Ereignis auf dem Spielfeld
function handleCickOnCanvas(event) {
	x = event.offsetX;
  	y = event.offsetY;
  
  	var field = getField(x, y);
  	
  	if(field >= 1 && field <= 9) {
  		var request = new XMLHttpRequest();
  		request.onreadystatechange = function() {
  			if (this.readyState == 4 && this.status == 200) {
  		  		updateGameState();
  			}
  		}

  		request.open("POST", "http://localhost:8080/PMProjekt-TicTacToe/singleplayer/taketurn?field=" + field, true);
  		request.send();
  	}
}

// Berechnet anhand der übergebenen Koordinaten die Nummer des Feldes und gibt diese zurück.
function getField(x, y) {
  field = Math.floor(x / fieldSize);
  field = field + 3 * Math.floor(y / fieldSize);
  field = field + 1;
  return field;
}
// TODO: XMLHttpRequest Objekt setzen
// TODO: Aktualisierung des Feldes bearbeiten


function repaint() {
	drawField(gamestate.fields);
	console.log(document.getElementById("gameMessage"));
	document.getElementById("gameMessage").innerHTML = gamestate.message;
}

function updateGameState() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log(this.responseText);
			gamestate = JSON.parse(this.responseText);
			repaint();
		}
	}
	
	request.open("GET", "http://localhost:8080/PMProjekt-TicTacToe/singleplayer/update", true);
	request.send();	
	
	// setTimeout(updateGameState, 1000);
}

