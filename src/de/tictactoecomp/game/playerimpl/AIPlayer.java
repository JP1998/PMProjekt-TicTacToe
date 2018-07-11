package de.tictactoecomp.game.playerimpl;

import de.tictactoecomp.game.Player;

public class AIPlayer extends Player {
    
    public AIPlayer() {
        super("-1", "AI");
    }

    @Override
    public void makeMove(int field) {
        // TODO: Implement some AI logic
    }
    
    @Override
    public String getStatusJSON() {
        return null;
    }
}
