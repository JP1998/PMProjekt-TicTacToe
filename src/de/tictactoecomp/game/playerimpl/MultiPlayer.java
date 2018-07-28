package de.tictactoecomp.game.playerimpl;

import de.tictactoecomp.game.Player;
import de.tictactoecomp.game.utils.StringProcessing;

public class MultiPlayer extends Player {

    /**
     * Der letzte Fehlercode, der dazu genutzt wird, um in
     * {@link MultiPlayerPair} zu ermitteln, ob ein Zug gültig war
     */
    private int lastErrorCode;
    
    public MultiPlayer(String id, String name) {
        super(id, name);
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
                currentGame.getCurrentGameState().isFinished(),
                (currentGame.getCurrentPlayer() != null)? currentGame.getCurrentPlayer().getName() : ""
        );
    }
}
