
function addHint() {
  document.getElementById("hintsign").addEventListener('click', showHint);
}

function showHint() {
	document.getElementById("hint-background").style.visibility = "visible";
	document.getElementById("hint-content").style.visibility = "visible";
}

function dismissHint() {
	document.getElementById("hint-background").style.visibility = "hidden";
	document.getElementById("hint-content").style.visibility = "hidden";
}