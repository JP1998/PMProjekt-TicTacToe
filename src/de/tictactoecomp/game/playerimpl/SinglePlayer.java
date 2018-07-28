package de.tictactoecomp.game.playerimpl;

import de.tictactoecomp.game.Player;
import de.tictactoecomp.game.utils.StringProcessing;

public class SinglePlayer extends Player {
    
    public SinglePlayer(String id, String name) {
        super(id, name);
    }
    
    @Override
    public void makeMove(int field) {
        // einen Spielzug machen
        int error = currentGame.receiveMove(this.playerId, field);
        
        // falls Zug valide, und Spiel nicht zu ende:
        if(error == 0 && !currentGame.isGameEnded()) {
            // lass die KI einen Zug machen
            currentGame.getOpponent(this).makeMove(-1);
        }
    }
    
    @Override
    public String getStatusJSON() {
        return StringProcessing.format(
                JSON_TEMPLATE, 
                System.lineSeparator(),
                hasTurn(),
                createFieldsArray(),
                getMessage(),
                currentGame.getCurrentGameState().isFinished(),
                (currentGame.getCurrentPlayer() != null)? currentGame.getCurrentPlayer().getName() : ""
        );
    }
}
