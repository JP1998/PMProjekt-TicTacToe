package de.tictactoecomp.game;

public class Move {

    private final int selectedField;
    private final Player moveMaker;
    
    public Move(Player pl, int selected) {
        this.selectedField = selected;
        this.moveMaker = pl;
    }
    
    public int getSelectedField() {
        return selectedField;
    }
    
    public Player getMoveMaker() {
        return moveMaker;
    }
}
