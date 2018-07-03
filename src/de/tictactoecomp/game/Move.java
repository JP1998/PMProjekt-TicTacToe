package de.tictactoecomp.game;

/**
 * Diese Klasse repr�sentiert einen Spielzug.
 * Ein Spielzug beinhaltet ein ausgewähltes Feld,
 * sowie einen Spieler, der das Feld in diesem Zug
 * ausgewählt hat.
 * 
 * @author Jonny
 */
public class Move {

    /**
     * Das Feld, das mit diesem Zug ausgewählt wurde.
     */
    private final int selectedField;
    /**
     * Der Spieler von dem dieser Zug gemacht wurde.
     */
    private final Player moveMaker;
    
    /**
     * Dieser Konstruktor erstellt ein Move-Objekt mit dem gegebenen
     * Spieler-Objekt als der Spieler, der den Zug gemacht hat, und
     * dem gegebenen Feld als das ausgewählte Feld.
     * Das gegebene Feld muss in dem Intervall [1; 9] liegen, und wird
     * als noch nicht belegt angenommen.
     * 
     * @param pl       Der Spieler, der den Zug gemacht hat
     * @param selected Das Feld das ausgew�lt wurde.
     */
    public Move(Player pl, int selected) {
        this.selectedField = selected;
        this.moveMaker = pl;
    }
    
    /**
     * Diese Methode gibt das Feld, das mit diesem
     * Zug ausgewählt wurde zurück.
     * 
     * @return das mit diesem Zug ausgewählte Feld
     */
    public int getSelectedField() {
        return selectedField;
    }
    
    /**
     * Diese Methode gibt den Spieler, der diesen
     * Zug gemacht hat zurück.
     * 
     * @return den Spieler, der diesen Zug gemacht hat
     */
    public Player getMoveMaker() {
        return moveMaker;
    }
}
