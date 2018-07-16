
function addHint() {
  document.getElementById("hintsign").addEventListener('click', showHint);
}

function showHint() {
	alert("Ziel des Spieles ist es, als erster drei gleiche Symbole in einer Reihe, Spalte oder Diagonal zu setzen. Die Spieler sind abwechselnd am Zug.");
}

function dismissHint() {
	
}