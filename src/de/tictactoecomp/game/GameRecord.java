package de.tictactoecomp.game;

public class GameRecord {
	
	private final long player1Id;
	private final long player2Id;
	private final int[] moves;

	/* package-protected */ GameRecord(long p1Id, long p2Id, int[] moves) {
		this.player1Id = p1Id;
		this.player2Id = p2Id;
		this.moves = moves;
	}
	
	public long getPlayer1Id() {
		return player1Id;
	}
	
	public long getPlayer2Id() {
		return player2Id;
	}
	
	public int[] getMoves() {
		return moves;
	}
}
