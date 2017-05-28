package core;

import chess.Alliance;

public class Player {

	public static final int PLAYER = Alliance.WHITE;
	public static final int COMPUTER = Alliance.BLACK;
	public static final int PLAYER2 = Alliance.BLACK;
	public static final int COMPUTER2 = Alliance.WHITE;

	public static int changePlayer(int player) {
		if (player == PLAYER || player == COMPUTER2) {
			return PLAYER2;
		} else
			return PLAYER;
	}
}
