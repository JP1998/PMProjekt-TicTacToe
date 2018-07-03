package de.tictactoecomp.game.exception;

/**
 * Diese Exception wird genutzt um einen Spieler, der einen
 * ungültigen Zug macht anzuzeigen. Dies kann durch verschiedene
 * Gründe ausgelöst werden, wie bspw. dem Versuch ein Feld
 * auszuwählen, das bereits besetzt ist, oder dem Versuch einen
 * Zug zu machen, während der Spieler gar nicht am Zug ist.
 * 
 * @author Jonny
 */
public class IllegalMoveException extends Exception {

    public IllegalMoveException(String message) {
        super(message);
    }
    
}
