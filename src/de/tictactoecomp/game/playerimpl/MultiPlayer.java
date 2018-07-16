package de.tictactoecomp.game.playerimpl;

import de.tictactoecomp.game.Player;
import de.tictactoecomp.game.utils.StringProcessing;

public class MultiPlayer extends Player {

    public MultiPlayer(String name, String id) {
        super(name,id);
    }
    
    @Override
    public void makeMove(int field) {
        currentGame.receiveMove(this.playerId, field);
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
