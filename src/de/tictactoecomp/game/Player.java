package de.tictactoecomp.game;

/**
 * Diese Klasse dient als Abstrahierung eines Spielers, der an einem
 * Tic Tac Toe-Spiel teilnehmen kann. Diese Abstrahierung wird vorgenommen,
 * um auf einfache Art und Weise eine KI implementieren zu können.
 * 
 * @author Jonny
 */
public abstract class Player {
	
    /**
     * Die ID des Spielers
     */
    private long playerId;
    /**
     * Der Name des Spielers
     */
    private String name;
    /**
     * Das Spiel, in dem sich der Spieler momentan befindet
     */
    private TicTacToeGame currentGame;
    
    /**
     * Diese Methode gibt die ID des Spielers zurück.
     * 
     * @return die ID des Spielers
     */
    public long getPlayerId() {
        return playerId;
    }
    
    /**
     * Diese Methode gibt den Namen des Spielers zurück.
     * 
     * @return den Namen des Spielers
     */
    public String getName() {
        return name;
    }
    
    /**
     * Diese Methode kann genutzt werden, um
     * den Spieler einen Zug machen zu lassen.
     * Dazu kann die Methode {@link TicTacToeGame#receiveMove(long, int)}
     * auf dem derzeitigen Spiel des Spielers aufgerufen werden.
     */
    public abstract void makeMove();
    
}
