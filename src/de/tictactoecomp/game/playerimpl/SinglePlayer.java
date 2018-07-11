package de.tictactoecomp.game.playerimpl;

import de.tictactoecomp.game.Player;
import de.tictactoecomp.game.utils.StringProcessing;

public class SinglePlayer extends Player {
    
    public SinglePlayer(String id, String name) {
        super(id, name);
    }
    
    @Override
    public void makeMove(int field) {
        currentGame.receiveMove(this.playerId, field);
        
        if(!currentGame.isGameEnded()) {
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
                getMessage()
        );
    }
}
