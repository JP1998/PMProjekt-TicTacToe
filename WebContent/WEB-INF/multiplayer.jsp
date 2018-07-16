<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Tic Tac Toe: Mehrspieler</title>
        <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <script type="text/javascript" src="js/field.js"></script>
        <script type="text/javascript" src="js/hint.js"></script>
        <script type="text/javascript" src="js/game.js"></script>
    </head>
    <body>
        <div id="header">
            <div id="navi">
            <a href="/PMProjekt-TicTacToe/index"><img src="images/splash.png" height="42" width="62"/></a>
            <ul>
                <li><a href="/PMProjekt-TicTacToe/singleplayer">Einzelspieler</a></li>
                <li><a href="/PMProjekt-TicTacToe/multiplayer">Mehrspieler</a></li>
                <li><a href="#" id="hintsign">?</a></li>
            </ul>
            </div>
        </div>
        <div id="content">
            <h1> Mehrspieler</h1>
            <div id="name-container">
	            <p id="player1-name">Player 1: ${param.player1}</p>
	            <p id="player2-name">Player 2: ${param.player2}</p>
	           	<button id="resetButton" onclick="resetGame();">Spiel zurücksetzen</button>
            </div>
            <canvas id="gameCanvas" width="600px" height="600px" style="border:0px solid #000000;"></canvas>
            <p id="gameMessage"></p>
        </div>
        <div id="footer">
            <ul>
                <li><a href="/PMProjekt-TicTacToe/impressum"> Impressum</a></li>
                <li><a href="/PMProjekt-TicTacToe/datenschutz"> Datenschutz</a></li>
                <li><a href="/PMProjekt-TicTacToe/faq">FAQ's</a></li>
            </ul>
        </div>
        <script>
        var url = "http://localhost:8080/PMProjekt-TicTacToe/multiplayer";

        addHint();
        initGame();
        </script>
    </body>
</html>
