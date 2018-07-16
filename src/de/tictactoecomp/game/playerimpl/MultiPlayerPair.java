package de.tictactoecomp.game.playerimpl;

import de.tictactoecomp.game.Player;
import de.tictactoecomp.game.TicTacToeGame;

public class MultiPlayerPair {

    private MultiPlayer player1;
    private MultiPlayer player2;
    
    private TicTacToeGame assignedGame;
    
    private int currentPlayer;
    
    public MultiPlayerPair(String id, String name1, String name2) {
        this.player1 = new MultiPlayer(name1, id + "1");
        this.player2 = new MultiPlayer(name2, id + "2");
        
        this.assignedGame = new TicTacToeGame(player1, player2);
        
        this.currentPlayer = 0;
    }

    public void makeMove(int field) {
        getCurrentPlayer().makeMove(field);
        
        if(getCurrentPlayer().getLastErrorCode() == 0) {
            this.currentPlayer = (this.currentPlayer + 1) % 2;
        }
    }
    
    private MultiPlayer getCurrentPlayer() {
        if(currentPlayer % 2 == 0) {
            return this.player1;
        } else {
            return this.player2;
        }
    }

    public String getStatusJSON() {
        return getCurrentPlayer().getStatusJSON();
    }
}
