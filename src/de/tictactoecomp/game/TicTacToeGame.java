package de.tictactoecomp.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicTacToeGame {

    private Player player1;
    private Player player2;
    
    private int currentPlayer;
        
    private List<Move> moves;
    
    private boolean gameEnded;
    private Player winner;
    
    public TicTacToeGame(Player pl1, Player pl2) {
        this.player1 = pl1;
        this.player2 = pl2;
        this.currentPlayer = 0;
        this.moves = new ArrayList<>();
        this.winner = null;
    }
    
    public void receiveMove(long id, int field) {
        if(!gameEnded && getCurrentPlayer().getPlayerId() != id) {
            throw new IllegalMonitorStateException("It is currently not your turn!");
        }
        
        moves.add(new Move(getCurrentPlayer(), field));
        
        if(!evaluateGameState()) {
            currentPlayer++;
        }
        
        // TODO: Refresh states / ui of the connected clients
    }
    
    private Player getCurrentPlayer() {
        if(gameEnded) {
            return null;
        }
        
        if(currentPlayer % 2 == 0) {
            return player1;
        } else {
            return player2;
        }
    }
    
    private boolean evaluateGameState() {
        if(moves.size() == 9) {
            return true;
        } else {
            List<Move> pl1Moves = this.moves.stream()
                    .filter(pl -> pl.getMoveMaker().getPlayerId() == player1.getPlayerId())
                    .collect(Collectors.toCollection(ArrayList::new));
            
            List<Move> pl2Moves = this.moves.stream()
                    .filter(pl -> pl.getMoveMaker().getPlayerId() == player2.getPlayerId())
                    .collect(Collectors.toCollection(ArrayList::new));
            
            if(containsWinningMoves(pl1Moves)) {
                gameEnded = true;
                winner = player1;
                
                return true;
            }
            
            if(containsWinningMoves(pl2Moves)) {
                gameEnded = true;
                winner = player2;
                
                return true;
            }
            
            return false;
        }
    }
    
    private boolean containsWinningMoves(List<Move> movesToEval) {
        int[][] winningMovesToCheck = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 },
                { 1, 4, 7 },
                { 2, 5, 8 },
                { 3, 6, 9 },
                { 1, 5, 9 },
                { 3, 5, 7 }
        };
        
        for(int i = 0; i < winningMovesToCheck.length; i++) {
            if(containsWinningMove(movesToEval, winningMovesToCheck[i])) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean containsWinningMove(List<Move> movesToEval, int[] winningMoves) {
        for(int i = 0; i < winningMoves.length; i++) {
            boolean found = false;
            
            for(int j = 0; j < movesToEval.size() && !found; j++) {
                if(movesToEval.get(j).getSelectedField() == winningMoves[i]) {
                    found = true;
                }
            }
            
            if(!found) {
                return false;
            }
        }
        
        return true;
    }
}
