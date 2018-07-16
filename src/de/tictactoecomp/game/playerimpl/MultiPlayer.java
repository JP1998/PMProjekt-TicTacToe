package de.tictactoecomp.game.playerimpl;

import de.tictactoecomp.game.Player;
import de.tictactoecomp.game.utils.StringProcessing;

public class MultiPlayer extends Player {

    private int lastErrorCode;
    
    public MultiPlayer(String name, String id) {
        super(name,id);
        this.lastErrorCode = 0;
    }
    
    @Override
    public void makeMove(int field) {
        this.lastErrorCode = currentGame.receiveMove(this.playerId, field);
    }
    
    public int getLastErrorCode() {
        return lastErrorCode;
    }

    @Override
    public String getStatusJSON() {
        return StringProcessing.format(
                JSON_TEMPLATE, 
                System.lineSeparator(),
                hasTurn(),
                createFieldsArray(),
                getMessage(),
                currentGame.getCurrentGameState().isFinished()
        );
    }
}
