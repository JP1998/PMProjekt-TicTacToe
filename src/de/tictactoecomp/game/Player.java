package de.tictactoecomp.game;

public abstract class Player {
	
	private long playerId;
	private String name;
	
	private TicTacToeGame currentGame;
	
	public long getPlayerId() {
		return playerId;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract int makeMove();

}
