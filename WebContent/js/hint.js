
function showHint() {
	document.getElementById("hint-background").style.visibility = "visible";
	document.getElementById("hint-content").style.visibility = "visible";
	return false;
}

function dismissHint() {
	document.getElementById("hint-background").style.visibility = "hidden";
	document.getElementById("hint-content").style.visibility = "hidden";
}
