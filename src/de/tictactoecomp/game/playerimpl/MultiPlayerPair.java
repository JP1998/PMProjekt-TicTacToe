package de.tictactoecomp.game.playerimpl;

import de.tictactoecomp.game.Player;
import de.tictactoecomp.game.TicTacToeGame;

/**
 * Diese Klasse managt die Spieler, die in einem lokalen
 * Multiplayer gegeneinander spielen.
 */
public class MultiPlayerPair {

    /**
     * Der erste Spieler des Spiels
     */
    private MultiPlayer player1;
    /**
     * Der zweite Spieler des Spiels
     */
    private MultiPlayer player2;
    
    /**
     * Das Spiel, in dem die beiden gegebenen
     * Spieler gegeneinander spielen.
     */
    private TicTacToeGame assignedGame;
    
    /**
     * Ein Zähler, der ermittelt welcher Spieler an der Reihe ist.
     */
    private int currentPlayer;
    
    public MultiPlayerPair(String id, String name1, String name2) {
        this.player1 = new MultiPlayer(id + "1", name1);
        this.player2 = new MultiPlayer(id + "2", name2);
        
        this.assignedGame = new TicTacToeGame(player1, player2);
        
        this.currentPlayer = 0;
    }

    /**
     * Diese Methode versucht für den Spieler, der derzeitig am Zug ist das
     * gegebene Feld zu besetzen.
     * 
     * @param field das Feld, das der derzeitige Spieler besetzen möchte
     */
    public void makeMove(int field) {
        getCurrentPlayer().makeMove(field);
        
        if(getCurrentPlayer().getLastErrorCode() == 0) {
            this.currentPlayer = (this.currentPlayer + 1) % 2;
        }
    }
    
    /**
     * Diese Methode ermittelt den Spieler, der derzeitig am Zug ist.
     * 
     * @return den Spieler, der am Zug ist
     */
    private MultiPlayer getCurrentPlayer() {
        if(currentPlayer % 2 == 0) {
            return this.player1;
        } else {
            return this.player2;
        }
    }

    /**
     * Diese Methode ermittelt den Zustand des Spiels in Form von JSON.
     * 
     * @return das JSON das den Zustand des Spiels repräsentiert
     */
    public String getStatusJSON() {
        return getCurrentPlayer().getStatusJSON();
    }
    
    /**
     * @return den Namen des ersten Spielers
     */
    public String getPlayer1Name() {
        return this.player1.getName();
    }
    
    /**
     * @return den Namen des zweiten Spielers
     */
    public String getPlayer2Name() {
        return this.player2.getName();
    }
}
