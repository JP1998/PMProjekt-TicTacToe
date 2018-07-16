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
        
        // TODO: Somehow detect errors and make AI not take any turn!
        // FIXME: Currently also called when errors occur (like a taken field is selected)
        // FIXME: which causes the game to lock up.
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
