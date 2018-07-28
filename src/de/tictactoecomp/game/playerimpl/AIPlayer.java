package de.tictactoecomp.game.playerimpl;

import java.util.ArrayList;
import java.util.List;

import de.tictactoecomp.game.Move;
import de.tictactoecomp.game.Player;

public class AIPlayer extends Player {
    
    private AITree usedAITree;
    
    public AIPlayer() {
        super("-1", "AI");
        usedAITree = new AITree();
    }

    @Override
    public void makeMove(int field) {
        // den letzten Zug ermitteln, und dem Baum diesen mitteilen
        int[] moves = currentGame.getCurrentGameState().getFields();
        
        if(moves.length > 0) {
            usedAITree.informOfMove(moves[moves.length - 1]);
        }
        
        // den bestmöglichen Zug für die KI ermitteln, und dem Spiel mitteilen
        currentGame.receiveMove(this.playerId, usedAITree.getNextMove());
    }
    
    @Override
    public String getStatusJSON() {
        // Da das JSON für die KI nicht benötig ist können wir null zurück geben
        return null;
    }
    
    private static class AITree {
        
        /**
         * Der Wurzeltknoten eines Spielbaumes.
         * Dieser wird als statisch und konstant deklariert, um a) Speicherresourcen
         * zu sparen, und b) Zeit beim Erstellen eines KI-Baumes zu sparen.
         */
        private static final AINode staticBaseNode = new AINode();
        
        /**
         * Der derzeitige Wurzelknoten des Baumes.
         * Dieser wird mit dem Spiel vorgerückt werden.
         */
        private AINode currentGameMoveNode;
        
        public AITree() {
            this.currentGameMoveNode = staticBaseNode;
        }
        
        /**
         * Diese Methode ermittelt den nächsten Zug, der von der KI, die diesen
         * Baum besitzt gemacht werden muss.
         * 
         * @return der bestmögliche Zug für die KI
         */
        public int getNextMove() {
            int nextMove = currentGameMoveNode.getNextMove();
            currentGameMoveNode = currentGameMoveNode.findChoiceNode(nextMove);
            return nextMove;
        }
        
        /**
         * Diese Methode muss aufgerufen wird, sobald ein Zug von einem anderen
         * Spieler getan wird, als die KI, die diesen Baum besitzt.
         *
         * @param field das Feld, das von dem anderen Spieler besetzt wurde
         */
        public void informOfMove(int field) {
            currentGameMoveNode = currentGameMoveNode.findChoiceNode(field);
        }
    }
    
    private static class AINode {
        
        /**
         * Das Feld, das dieser Knoten spielt
         */
        private int fieldChoice;
        /**
         * Dieses Array beinhaltet alle Züge, die im Zustand des Spiels
         * bei diesem Knoten gespielt wurden, in der Reihenfolge, in der
         * sie gespielt wurden.
         */
        private Integer[] fieldHistory;
        
        /**
         * Diese Liste beinhaltet alle Kinderknoten. Jeder Kindknoten ist
         * ein valider Zug.
         */
        private List<AINode> childNodes;
        /**
         * Der Wert des Knotens, der genutzt wird, um den statistisch besten
         * Zug in jeder Situation ermitteln zu können.
         */
        private int value;
        
        public AINode() {
            this(new ArrayList<>());
        }
        
        private AINode(List<Integer> fields) {
            this.fieldChoice = (fields.size() == 0)? -1 : fields.get(fields.size() - 1);
            this.fieldHistory = new Integer[fields.size()];
            this.fieldHistory = fields.toArray(this.fieldHistory);
            
            this.childNodes = new ArrayList<>();
            
            int winner = calculateWinner(splitFieldHistory(fields));
            if(winner == 0) {
                for(int i = 1; i <= 9; i++) {
                    if(!moveContained(this.fieldHistory, i)) {
                        fields.add(i);
                        this.childNodes.add(new AINode(fields));
                        fields.remove((Integer) i);
                    }
                }
                
                this.value = calculateValue();
            } else {
                this.value = winner * (10 - fields.size());
            }
        }
        
        /**
         * Diese Methode gibt Ihnen das Feld,
         * das von diesem Knoten (virtuell) gespielt wird.
         * 
         * @return das Feld, das von diesem Knoten gespielt wird
         */
        public int getFieldChoice() {
            return fieldChoice;
        }
        
        /**
         * Diese Methode liefert Ihnen den Wert dieses Knotens.
         * 
         * @return der Wert dieses Knotens
         */
        public int getValue() {
            return value;
        }
        
        /**
         * Diese Methode gibt das Feld zurück, das von dem Kindknoten
         * mit dem höchsten Wert gespielt wird.
         * Daher ist dies statistisch gesehen der bestmögliche Zug, wenn
         * die Annahme getroffen wird, dass die KI der zweite Spieler ist.
         * 
         * @return das Feld, das statistisch gesehen der optimale Zug ist
         */
        public int getNextMove() {
            int maxValue = Integer.MIN_VALUE;
            
            for(int i = 0; i < this.childNodes.size(); i++) {
                if(maxValue < this.childNodes.get(i).getValue()) {
                    maxValue = this.childNodes.get(i).getValue();
                }
            }
        
            if(maxValue == Integer.MIN_VALUE) {
                return -1;
            } else {
                return findChildByValue(maxValue).getFieldChoice();
            }
        }
        
        /**
         * Diese Methode findet den Kindknoten, der den gegebenen Wert hat.
         * Falls dieser Kindknoten nicht existiert gibt diese Methode {@code null}
         * zurück.
         *
         * @param val Der Wert, den der gefundene Knoten haben soll
         * @return der Knoten, der Kind dieses Knotens ist, und den gegebenen Wert hat
         */
        private AINode findChildByValue(int val) {
            for(int i = 0; i < this.childNodes.size(); i++) {
                if(this.childNodes.get(i).getValue() == val) {
                    return this.childNodes.get(i);
                }
            }
            return null;
        }
        
        /**
         * Diese Methode findet den Kindknoten, der das gegebene Feld spielt.
         * Falls dieser Kindknoten nicht existiert gibt diese Methode {@code null}
         * zurück.
         * 
         * @param move Das Feld, das von dem gefundenen Knoten gespielt werden soll
         * @return der Knoten, der Kind dieses Knotens ist, und das gegebene Feld spielt
         */
        public AINode findChoiceNode(int move) {
            for(int i = 0; i < this.childNodes.size(); i++) {
                if(this.childNodes.get(i).getFieldChoice() == move) {
                    return this.childNodes.get(i);
                }
            }
            return null;
        }
        
        /**
         * Diese Methode ermittelt die Summe der Werte der Kindknoten dieses Knotens.
         *
         * @return der Wert dieses Knotens
         */
        private int calculateValue() {
            int result = 0;
            
            for(int i = 0; i < childNodes.size(); i++) {
                result += childNodes.get(i).getValue();
            }
            
            return result;
        }
        
        /**
         * Diese Methode ermittelt, ob das gegebene Array das gegebene Element beinhaltet.
         * 
         * @param moves das Array, in dem gesucht werden soll
         * @param move das Element, nach dem gesucht werden soll
         * @return ob das gegebene Element in dem Array enthalten ist
         */
        public static boolean moveContained(Integer[] moves, int move) {
            for(int i = 0; i < moves.length; i++) {
                if(moves[i] == move) {
                    return true;
                }
            }
            return false;
        }
        
        /**
         * Diese Methode teilt die gegebene Liste, die alle Züge des Spiels in der
         * Reihenfolge ihrer zeitlichen  Betätigung enthält, in ein zwei-dimensionales
         * Array, das für beide Spieler jeweils alle betätigten Züge in derselben
         * Reihenfolge enthält. Die erste Dimension repräsentiert hierbei den Spieler,
         * und die zweite für die Züge. D.h. dass {@code result[0][1]} ist der zweite
         * Zug von Spieler 1.
         * 
         * @param fields die Liste mit Zügen, die auf die zwei Spieler aufzuteilen ist
         * @return das Array mit den Zügen auf die beiden Spieler aufgeteilt
         */
        public static int[][] splitFieldHistory(List<Integer> fields) {
            int[][] result = new int[2][];

            result[0] = new int[fields.size() / 2 + fields.size() % 2];
            result[1] = new int[fields.size() / 2];
            
            for(int i = 0; i < fields.size(); i++) {
                result[i % 2][i / 2] = fields.get(i);
            }
            
            return result;
        }
        
        /**
         * Diese Methode ermittelt den Gewinner aus dem gegebenen zwei-dimensionalen int-Array.
         * Dabei steht die erste Dimension für den Spieler, und die zweite für den Zug, den
         * der jeweilige Spieler getätigt hat. Daher repräsentiert {@code movesToEval[1][0]}
         * den ersten Zug, den der zweite Spieler getätigt hat.
         * 
         * @param movesToEval die Züge, die auszuwerten sind, auf die beiden Spieler aufgeteilt
         * @return 1, falls der erste Spieler gewonnen hat, -1 falls der zweite Spieler gewonnen hat
         *         0, falls keiner der beiden Spieler gewonnen hat
         */
        public static int calculateWinner(int[][] movesToEval) {
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
                if(containsAllMoves(movesToEval[0], winningMovesToCheck[i])) {
                    return -1;
                } else if(containsAllMoves(movesToEval[1], winningMovesToCheck[i])) {
                    return 1;
                }
            }
            
            // falls keine der Gewinnmöglichkeiten drinliegen können wir sagen,
            // dass kein Gewinn enthalten ist, da alle Möglichkeiten bereits abgefragt wurden
            return 0;
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
        public static boolean containsAllMoves(int[] movesToEval, int[] movesToContain) {
            // wir gehen durch alle zu enthaltenden Züge
            for(int i = 0; i < movesToContain.length; i++) {
                // und suchen den Zug in der Liste
                boolean found = false;
                
                for(int j = 0; j < movesToEval.length && !found; j++) {
                    if(movesToEval[j] == movesToContain[i]) {
                        found = true;
                    }
                }
                
                // falls ein Zug nicht enthalten ist können wir false zurück geben
                if(!found) {
                    return false;
                }
            }
            
            // falls wir durch alle Züge sind, ohne dass ein Zug nicht gefunden wurde
            // können wir true zurück geben
            return true;
        }
    }
}
