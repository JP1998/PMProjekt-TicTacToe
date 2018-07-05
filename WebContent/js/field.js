function field(){
	var c = document.getElementById("singlePlayerCanvas");
	var ctx = c.getContext("2d");
	ctx.moveTo(300, 0);
	ctx.lineTo(300, 600);
	ctx.stroke();
	var d = document.getElementById("singlePlayerCanvas");
	var dtx = c.getContext("2d");
	dtx.moveTo(00, 200);
	dtx.lineTo(900, 200);
	dtx.stroke();
	var e = document.getElementById("singlePlayerCanvas");
	var etx = c.getContext("2d");
	etx.moveTo(600, 0);
	etx.lineTo(600, 600);
	etx.stroke();
	var f = document.getElementById("singlePlayerCanvas");
	var ftx = c.getContext("2d");
	ftx.moveTo(00, 400);
	ftx.lineTo(900, 400);
	ftx.stroke();
}

