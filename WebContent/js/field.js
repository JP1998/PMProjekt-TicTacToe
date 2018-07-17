var fieldSize = 200;

function drawField(moves) {
    var g = document.getElementById("gameCanvas").getContext("2d");

    // draw the border
    g.fillStyle = '#ffffff';
    g.fillRect(0, 0, 3 * fieldSize, 3 * fieldSize);

    // draw the inner field borders
    g.beginPath();

    g.lineJoin = "round";
    g.lineCap = "round";
    g.lineWidth = 4;
    g.strokeStyle = "#acacac";

    g.moveTo(0,                fieldSize);
    g.lineTo(3 * fieldSize,    fieldSize);
    g.moveTo(0,                2 * fieldSize);
    g.lineTo(3 * fieldSize,    2 * fieldSize);
    g.moveTo(fieldSize,        0);
    g.lineTo(fieldSize,        3 * fieldSize);
    g.moveTo(2 * fieldSize,    0);
    g.lineTo(2 * fieldSize,    3 * fieldSize);

    g.stroke();

    // draw the moves made by the players
    for(var i = 0; i < moves.length; i++) {
        if(i % 2 === 0) {
            drawCross(
            		g,
            		'#d33523',
            		resolveX(moves[i]),
            		resolveY(moves[i]),
            		fieldSize,
            		fieldSize
            );
        } else {
            drawCircle(
            		g,
            		'#75d222',
            		resolveX(moves[i]),
            		resolveY(moves[i]),
            		fieldSize,
            		fieldSize
            );
        }
    }

    // if there is a winner also display the path with which the player has won
    var win = calculateWinner(moves);
    if(win !== 0) {
        drawWinner(
        		g,
        		'#428cf4',
        		calculateWinFields(moves), 
        		0, 
        		0,
        		3 * fieldSize,
        		3 * fieldSize
        );
    }
}

function arrContains(arr, element) {
    for(var i = 0; i < arr.length; i++) {
        if(arr[i] === element) {
            return true;
        }
    }
    return false;
}

function splitPlayerMoves(arr) {
    var player = [[], []];

    for(var i = 0; i < arr.length; i++) {
        var playerIndex = i % 2;
        player[playerIndex][player[playerIndex].length] = arr[i];
    }

    return player;
}

function calculateWinner(arr) {
    var player = splitPlayerMoves(arr);

    var winPossibilities = [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9],
        [1, 4, 7],
        [2, 5, 8],
        [3, 6, 9],
        [1, 5, 9],
        [3, 5, 7]
    ];

    for (var i = 0; i < winPossibilities.length; i++) {
        if(arrContains(player[0], winPossibilities[i][0]) &&
        		arrContains(player[0], winPossibilities[i][1]) &&
        		arrContains(player[0], winPossibilities[i][2])) {
            return 1;
        } else if(arrContains(player[1], winPossibilities[i][0]) &&
        		arrContains(player[1], winPossibilities[i][1]) &&
        		arrContains(player[1], winPossibilities[i][2])) {
            return -1;
        }
    }
    return 0;
}

function calculateWinFields(arr) {
    var player = splitPlayerMoves(arr);

    var wins = [];

    var winPossibilities = [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9],
        [1, 4, 7],
        [2, 5, 8],
        [3, 6, 9],
        [1, 5, 9],
        [3, 5, 7]
    ];

    for (var i = 0; i < winPossibilities.length; i++) {
        if(
            (
                arrContains(player[0], winPossibilities[i][0]) &&
                arrContains(player[0], winPossibilities[i][1]) &&
                arrContains(player[0], winPossibilities[i][2])
            ) || (
                arrContains(player[1], winPossibilities[i][0]) &&
                arrContains(player[1], winPossibilities[i][1]) &&
                arrContains(player[1], winPossibilities[i][2])
            )
        ) {
            wins[wins.length] = winPossibilities[i];
        }
    }

    return wins;
}

function resolveX(tile)  {
    return ((tile - 1) % 3) * fieldSize;
}

function resolveY(tile) {
    return Math.floor((tile - 1) / 3) * fieldSize;
}

function minArrayElement(arr, pIndex) {
    if(arr.length === 0) {
        return;
    }
    var index = (pIndex !== undefined)? pIndex: false;
    var min = arr[0];
    var ind = 0;

    for(var i = 0; i < arr.length; i++) {
        if(arr[i] < min) {
            min = arr[i];
            ind = i;
        }
    }

    if(index) {
        return ind;
    } else {
        return min;
    }
}

function maxArrayElement(arr, pIndex) {
    if(arr.length === 0) {
        return;
    }
    var index = (pIndex !== undefined)? pIndex: false;
    var max = arr[0];
    var ind = 0;

    for(var i = 0; i < arr.length; i++) {
        if(arr[i] > max) {
            max = arr[i];
            ind = i;
        }
    }

    if(index) {
        return ind;
    } else {
        return max;
    }
}

function drawCross(g, col, x, y, w, h) {
    g.beginPath();
    g.strokeStyle = col;
    g.lineWidth = 12;

    var centerX = x + Math.floor(w / 2);
    var centerY = y + Math.floor(h / 2);

    var radius = Math.floor((((w < h)? w: h) - 40) / 2);

    g.moveTo(centerX - radius, centerY - radius);
    g.lineTo(centerX + radius, centerY + radius);
    g.moveTo(centerX - radius, centerY + radius);
    g.lineTo(centerX + radius, centerY - radius);

    g.stroke();
}

function drawCircle(g, col, x, y, w, h) {
    g.beginPath();
    g.strokeStyle = col;
    g.lineWidth = 12;
    g.arc(x + Math.floor(w / 2), y + Math.floor(h / 2), Math.floor((((w < h)? w: h) - 40) / 2), 0, 2 * Math.PI);
    g.stroke();
}

function drawWinner(g, col, fields, x, y, w, h) {
    for(var i = 0; i < fields.length; i++) {
        var currWin = fields[i];

        var begField = minArrayElement(currWin, false);
        var endField = maxArrayElement(currWin, false);

        var xPart = Math.floor(w / 6);
        var yPart = Math.floor(h / 6);

        var begX = x + (xPart * ((((begField - 1) % 3) * 2) + 1));
        var begY = y + (yPart * ((Math.floor((begField - 1) / 3) * 2) + 1));
        var endX = x + (xPart * ((((endField - 1) % 3) * 2) + 1));
        var endY = y + (yPart * ((Math.floor((endField - 1) / 3) * 2) + 1));

        var l = Math.sqrt(Math.pow(endX - begX, 2) + Math.pow(endY - begY, 2));

        var begDelX = (endX - begX) / l;
        var begDelY = (endY - begY) / l;
        var endDelX = (begX - endX) / l;
        var endDelY = (begY - endY) / l;

        begX = Math.floor(begX - (begDelX * xPart));
        begY = Math.floor(begY - (begDelY * yPart));
        endX = Math.floor(endX - (endDelX * xPart));
        endY = Math.floor(endY - (endDelY * yPart));

        g.beginPath();

        g.lineCap = "round";
        g.lineWidth = 16;
        g.strokeStyle = col;

        g.moveTo(begX, begY);
        g.lineTo(endX, endY);

        g.stroke();
    }
}
