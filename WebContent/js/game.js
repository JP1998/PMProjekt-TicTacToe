// Initialisiert das Spiel, indem es das dem Canvas den Event-Listener hinzufügt.
var gamestate = {
		turn : false,
		fields : [],
		message : "",
		gameEnded : false,
		playerwithturn : ""
};

function initGame() {
	document.getElementById('gameCanvas').addEventListener('click', handleCickOnCanvas);
	updateGameState();
}

function resetGame() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
	  		updateGameState();
		}
	}

	request.open("POST", url + "/reset", true);
	request.send();
}

function deleteGame() {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
	  		updateGameState();
		}
	}

	request.open("GET", url + "/delete", true);
	request.send();
}

// Funktion für das Klick-Ereignis auf dem Spielfeld
function handleCickOnCanvas(event) {
	if(gamestate.gameEnded) {
		resetGame();
		return;
	}
	
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

  		request.open("POST", url + "/taketurn?field=" + field, true);
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


function repaint() {
	drawField(gamestate.fields);
	console.log(document.getElementById("gameMessage"));
	document.getElementById("gameMessage").innerHTML = gamestate.message;
	document.getElementById("playerwithturn-name").innerHTML = (gamestate.gameEnded)? "Spiel beendet." : "Am Zug: " + gamestate.playerwithturn;
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
	
	request.open("GET", url + "/update", true);
	request.send();	
}

