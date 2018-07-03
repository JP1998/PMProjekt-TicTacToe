package de.tictactoecomp.game;

/**
 * Diese Klasse repräsentiert ein vergangenes Spiel.
 * Es beinhaltet die IDs der beiden Spieler, die
 * gegeneinander angetreten sind, sowie die ID des gewinnenden Spielers,
 * und den Zügen in der Reihenfolge, in der sie gespielt wurden.
 * 
 * @author Jonny
 */
public class GameRecord {

    /**
     * Diese Konstante entspricht einem unbekannten Spieler.
     * Diese kann genutzt werden, um ein Unentschieden anzuzeigen.
     */
    public static final int UNDEFINED_PLAYER = -1;
    /**
     * Diese Konstante entspricht einem Spieler mit künstlicher Intelligenz.
     */
    public static final int AI_PLAYER = -2;
    
    /**
     * Dieses Feld zeigt die ID des ersten Spielers des Spiels an.
     */
    private final long player1Id;
    /**
     * Dieses Feld zeigt die ID des zweiten Spielers des Spiels an.
     */
    private final long player2Id;
    /**
     * Dieses Feld zeigt die ID des gewinnenden Spielers an.
     */
    private final long winnerId;
    /**
     * Dieses Feld beinhaltet alle Z�ge, die in diesem Spiel gemacht
     * wurden, in der Reihenfolge, in der diese gemacht wurden.
     */
    private final int[] moves;

    /**
     * Dieser Konstruktor erzeugt ein GameRecord-Objekt mit den gegebenen Werten.
     * 
     * @param p1Id Die ID des ersten Spielers
     * @param p2Id Die ID des zweiten Spielers
     * @param wId Die ID des Gewinners
     * @param moves Die Züge, die in diesem Spiel gemacht wurden
     * @see GameRecord#UNDEFINED_PLAYER
     * @see GameRecord#AI_PLAYER
     */
    /* package-protected */ GameRecord(long p1Id, long p2Id, long wId, int[] moves) {
        this.player1Id = p1Id;
        this.player2Id = p2Id;
        this.winnerId = wId;
        this.moves = moves;
    }
    
    /**
     * Diese Methode gibt die ID des ersten Spielers in dem Spiel zurück.
     * 
     * @return Die ID des ersten Spielers
     * @see GameRecord#UNDEFINED_PLAYER
     * @see GameRecord#AI_PLAYER
     */
    public long getPlayer1Id() {
        return player1Id;
    }
    
    /**
     * Diese Methode gibt die ID des zweiten Spielers in dem Spiel zur�ck.
     * 
     * @return Die ID des zweiten Spielers
     * @see GameRecord#UNDEFINED_PLAYER
     * @see GameRecord#AI_PLAYER
     */
    public long getPlayer2Id() {
        return player2Id;
    }
    
    /**
     * Diese Methode gibt die ID des Spielers zurück, der dieses Spiel gewonnen hat.
     * 
     * @return Die ID des Gewinners
     * @see GameRecord#UNDEFINED_PLAYER
     * @see GameRecord#AI_PLAYER
     */
    public long getWinnerId() {
        return winnerId;
    }
    
    /**
     * Diese Methode gibt die Züge des Spiels zurück in der
     * Reihenfolge in der diese gemacht wurden.
     * 
     * @return Die Züge die in dem Spiel gemacht wurden
     */
    public int[] getMoves() {
        return moves;
    }
}
