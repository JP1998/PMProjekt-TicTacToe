function hint()
{
  alert("Ziel des Spieles ist es, drei gleiche Symbole in einer Reihe, Spalte oder Diagonal zu setzen. Die Spieler sind abwechselnd am Zug.");
}

function addHint()
{
  document.getElementById("hintsign").addEventListener('click', hint);
}