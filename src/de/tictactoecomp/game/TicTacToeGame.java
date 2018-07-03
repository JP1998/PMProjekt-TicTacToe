package de.tictactoecomp.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.tictactoecomp.game.exception.IllegalMoveException;
import de.tictactoecomp.game.utils.StringProcessing;

/**
 * Diese Klasse repräsentiert eine Instanz eines Tic Tac Toe Spieles.
 * Diese beinhaltet zwei Spieler, die gegeneinander spielen, sowie einen
 * Spieler, der gerade am Zug ist, eine Aufnahme der Züge, die bereits
 * gemacht wurden, sowie einem Gewinner.
 * 
 * @author Jonny
 */
public class TicTacToeGame {

    /**
     * Der Spieler, der als erstes in dem Spiel am Zug ist.
     */
    private Player player1;
    /**
     * Der Spieler, der als zweites in dem Spiel am Zug ist.
     */
    private Player player2;
    /**
     * Der Spieler, der gerade am Zug ist. Dabei steht ein
     * Wert, der ganzzahlig durch zwei teilbar ist für den
     * ersten Spieler, und andere Werte für den zweiten Spieler.
     */
    private int currentPlayer;
    /**
     * Die Liste der Spielzüge, die in diesem Spiel gemacht wurden
     * in der Reihenfolge eingefügt, in der diese gemacht wurden.
     */
    private List<Move> moves;
    /**
     * Dieses Feld zeigt an, ob das Spiel bereits geendet hat.
     */
    private boolean gameEnded;
    /**
     * Der Spieler, der das Spiel am Ende des Spiels gewonnen hat.
     * Wird {@code null} sein, wenn das Spiel in einem Unentschieden
     * resultiert. Um zu sehen, ob das Spiel geendet hat muss
     * {@link TicTacToeGame#gameEnded} ausgewertet werden.
     */
    private Player winner;
    
    /**
     * Dieser Konstruktor erstellt ein Spiel mit den gegebenen
     * Spielern als Gegner. Dabei wird {@code pl1} als erstes
     * am Zug sein. 
     * 
     * @param pl1 der erste Spieler, der in dem Spiel antreten soll
     * @param pl2 der zweite Spieler, der antreten soll
     */
    public TicTacToeGame(Player pl1, Player pl2) {
        this.player1 = pl1;
        this.player2 = pl2;
        this.currentPlayer = 0;
        this.moves = new ArrayList<>();
        this.winner = null;
    }
    
    /**
     * Diese Methode lässt einen der Spieler seinen Zug machen.
     * Um sicherzustellen, dass der Spieler, der den Zug abgibt
     * auch am Zug ist, muss dieser seine ID ebenfalls übergeben.
     * Das Feld muss ein Wert von 1 - 9 sein, wobei bei einem
     * ungültigen Wert (falls das Feld bereits belegt ist, oder
     * kein gültiges Feld ist) wird eine {@link IllegalMoveException}
     * mit entsprechender Beschreibung geworfen. Die Nummerierung
     * der einzelnen Felder kann man dabei wie folgt ermitteln:
     * <table>
     *     <tr>
     *         <td></td>
     *         <td>1</td>
     *         <td>2</td>
     *         <td>3</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td style="border: 1px solid black; padding: 12px;">1</td>
     *         <td style="border: 1px solid black; padding: 12px;">2</td>
     *         <td style="border: 1px solid black; padding: 12px;">3</td>
     *     </tr>
     *     <tr>
     *         <td>2</td>
     *         <td style="border: 1px solid black; padding: 12px;">4</td>
     *         <td style="border: 1px solid black; padding: 12px;">5</td>
     *         <td style="border: 1px solid black; padding: 12px;">6</td>
     *     </tr>
     *     <tr>
     *         <td>3</td>
     *         <td style="border: 1px solid black; padding: 12px;">7</td>
     *         <td style="border: 1px solid black; padding: 12px;">8</td>
     *         <td style="border: 1px solid black; padding: 12px;">9</td>
     *     </tr>
     * </table>
     * ⇒ die Nummer {@code n} des Feldes mit der Zeile {@code z}
     *    und Spalte {@code s}, wobei beide 1-indiziert sind,
     *    kann wie folgt berechnet werden:
     *    
     *    {@code n = (z - 1) * 3 + s}
     * 
     * @param id die ID des Spielers, der den Zug abgibt
     * @param field das Feld, das der Spieler belegen möchte
     * @throws IllegalMoveException falls der Versuch des Zuges ungültig ist
     */
    public void receiveMove(long id, int field) throws IllegalMoveException {
        if(field < 1 || field > 9) {
            throw new IllegalMoveException(StringProcessing.format(
                    "Das Feld {0} existiert nicht!",
                    field
            ));
        }
        
        if(!gameEnded && getCurrentPlayer().getPlayerId() != id) {
            throw new IllegalMoveException(StringProcessing.format(
                    "{0} ist gerade am Zug!",
                    getCurrentPlayer().getName()
            ));
        }
        
        moves.add(new Move(getCurrentPlayer(), field));
        
        if(!evaluateGameState()) {
            currentPlayer++;
        }
        
        // TODO: Refresh states / ui of the connected clients
    }
    
    /**
     * Diese Methode bestimmt den Spieler,
     * der derzeitig am Zug ist.
     * 
     * @return den derzeitigen Spieler;
     * {@code null} falls das Spiel beendet ist
     */
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
    
    /**
     * Diese Methode ermittelt den derzeitigen Spielstatus,
     * falls das Spiel beendet ist werden die Attribute
     * {@link TicTacToeGame#winner} und {@link TicTacToeGame#gameEnded}
     * entsprechend gesetzt.
     * 
     * @return ob das Spiel beendet ist, oder nicht
     */
    private boolean evaluateGameState() {
        // Filtere alle Move-Objekte, die mit dem player1-Objekt assoziiert sind
        List<Move> pl1Moves = this.moves.stream()
                .filter(pl -> pl.getMoveMaker().getPlayerId() == player1.getPlayerId())
                .collect(Collectors.toCollection(ArrayList::new));
        
        // Filtere ale Move-Objekte, die mit dem player2-Objekt assoziiert sind
        List<Move> pl2Moves = this.moves.stream()
                .filter(pl -> pl.getMoveMaker().getPlayerId() == player2.getPlayerId())
                .collect(Collectors.toCollection(ArrayList::new));
        
        // falls der erste Spieler gewonnen hat setzen wir entsprechend die
        // Attribute, und geben true zurück, um anzuzeigen, dass das Spiel geendet hat
        if(containsWinningMoves(pl1Moves)) {
            gameEnded = true;
            winner = player1;
            
            return true;
        }
        
        // Das gleiche für Spieler 2
        if(containsWinningMoves(pl2Moves)) {
            gameEnded = true;
            winner = player2;
            
            return true;
        }
        
        // falls keiner der beiden gewonnen hat wissen wir,
        // ob das Spiel geendet hat, indem wir schauen,
        // ob es noch ein freies Feld gibt.
        return moves.size() == 9;
    }
    
    /**
     * Diese Methode ermittelt, ob die gegebene Liste von Move-Objekten
     * eine Kombination enthält, die zum Gewinn führen. Dabei wird
     * angenommen, dass alle Züge in der gegebenen Liste von demselben
     * Player-Objekt gemacht wurden.
     * 
     * @param movesToEval Die Liste von Moves, die zu evaluieren sind
     * @return ob die gegebene Liste eine gewinnende Kombination enthält
     */
    private boolean containsWinningMoves(List<Move> movesToEval) {
        // ein Array, das alle möglichen Gewinnkombinationen aufstellt
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
        
        // für alle der möglichen Gewinnmöglichkeiten diese auswerten
        // falls es diese gibt, können wir sagen, dass die Liste einen Gewinn enthält
        for(int i = 0; i < winningMovesToCheck.length; i++) {
            if(containsAllMoves(movesToEval, winningMovesToCheck[i])) {
                return true;
            }
        }
        
        // falls keine der Gewinnmöglichkeiten drinliegen können wir sagen,
        // dass kein Gewinn enthalten ist, da alle Möglichkeiten bereits abgefragt wurden
        return false;
    }
    
    /**
     * Diese Methode ermittelt, ob die gegebene Liste von Move-Objekten alle Züge,
     * die in dem int-Array gegeben sind enthält. Falls dies der Fall ist wird
     * {@code true} zurückgegeben; {@code false} anderenfalls.
     * 
     * @param movesToEval die Liste von Move-Objekten, die alle gegebenen Züge enthalten soll
     * @param movesToContain die Züge, die alle enthalten sein sollen
     * @return ob die Liste alle der gegebenen Züge enthält
     */
    private boolean containsAllMoves(List<Move> movesToEval, int[] movesToContain) {
        // wir gehen durch alle zu enthaltenden Züge
        for(int i = 0; i < movesToContain.length; i++) {
            // und suchen den Zug in der Liste
            boolean found = false;
            
            for(int j = 0; j < movesToEval.size() && !found; j++) {
                if(movesToEval.get(j).getSelectedField() == movesToContain[i]) {
                    found = true;
                }
            }
            
            // falls ein Zug nicht enthalten ist könne wir false zurück geben
            if(!found) {
                return false;
            }
        }
        
        // falls wir durch alle Züge sind, ohne dass ein Zug nicht gefunden wurde
        // können wir true zurück geben
        return true;
    }
}
