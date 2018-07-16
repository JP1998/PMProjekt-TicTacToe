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
        int[] moves = currentGame.getCurrentGameState().getFields();
        
        if(moves.length > 0) {
            usedAITree.informOfMove(moves[moves.length - 1]);
        }
        
        currentGame.receiveMove(this.playerId, usedAITree.getNextMove());
    }
    
    @Override
    public String getStatusJSON() {
        return null;
    }
    
    private static class AITree {
        
        private static final AINode staticBaseNode = new AINode();
        
        private AINode currentGameMoveNode;
        
        public AITree() {
            this.currentGameMoveNode = staticBaseNode;
        }
        
        public int getNextMove() {
            int nextMove = currentGameMoveNode.getNextMove();
            currentGameMoveNode = currentGameMoveNode.findChoiceNode(nextMove);
            return nextMove;
        }
        
        public void informOfMove(int field) {
            currentGameMoveNode = currentGameMoveNode.findChoiceNode(field);
        }
    }
    
    private static class AINode {
        
        private int fieldChoice;
        private Integer[] fieldHistory;
        
        private List<AINode> childNodes;
        private int value;
        
        public AINode() {
            this(new ArrayList<>());
        }
        
        public AINode(List<Integer> fields) {
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
        
        public int getFieldChoice() {
            return fieldChoice;
        }
        
        public int getValue() {
            return value;
        }
        
        public int getNextMove() {
            int minValue = Integer.MAX_VALUE;
            
            for(int i = 0; i < this.childNodes.size(); i++) {
                if(minValue > this.childNodes.get(i).getValue()) {
                    minValue = this.childNodes.get(i).getValue();
                }
            }
            
            if(minValue == Integer.MAX_VALUE) {
                return -1;
            } else {
                return findChildByValue(minValue).getFieldChoice();
            }
        }
        
        private AINode findChildByValue(int val) {
            for(int i = 0; i < this.childNodes.size(); i++) {
                if(this.childNodes.get(i).getValue() == val) {
                    return this.childNodes.get(i);
                }
            }
            return null;
        }
        
        public AINode findChoiceNode(int move) {
            for(int i = 0; i < this.childNodes.size(); i++) {
                if(this.childNodes.get(i).getFieldChoice() == move) {
                    return this.childNodes.get(i);
                }
            }
            return null;
        }
        
        private int calculateValue() {
            int result = 0;
            
            for(int i = 0; i < childNodes.size(); i++) {
                result += childNodes.get(i).getValue();
            }
            
            return result;
        }
        
        public static boolean moveContained(Integer[] moves, int move) {
            for(int i = 0; i < moves.length; i++) {
                if(moves[i] == move) {
                    return true;
                }
            }
            return false;
        }
        
        public static int[][] splitFieldHistory(List<Integer> fields) {
            int[][] result = new int[2][];

            result[0] = new int[fields.size() / 2 + fields.size() % 2];
            result[1] = new int[fields.size() / 2];
            
            for(int i = 0; i < fields.size(); i++) {
                result[i % 2][i / 2] = fields.get(i);
            }
            
            return result;
        }
        
        public static int calculateWinner(int[][] movesToEval) {
            // ein Array, das alle m�glichen Gewinnkombinationen aufstellt
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
}
