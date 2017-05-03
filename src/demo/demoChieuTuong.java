package demo;

import action.ChessAction;
import action.Move;
import chess.Alliance;
import core.ChessBoard;
import core.Location;

public class demoChieuTuong {
	public static void main(String[] args) {
		ChessBoard board = new ChessBoard();
		ChessAction action = new ChessAction(board);
		System.out.println(action.push(new Move(new Location(6, 3), new Location(4, 3))));
		board.printBoard();

		board.setPlayer(Alliance.BLACK);
		System.out.println(action.push(new Move(new Location(1, 3), new Location(3, 3))));
		board.printBoard();

		board.setPlayer(Alliance.WHITE);
		System.out.println(action.push(new Move(new Location(6, 2), new Location(5, 2))));
		board.printBoard();

		board.setPlayer(Alliance.BLACK);
		System.out.println(action.push(new Move(new Location(1, 4), new Location(3, 4))));
		board.printBoard();

		board.setPlayer(Alliance.WHITE);
		System.out.println(action.push(new Move(new Location(7, 3), new Location(4, 0))));
		board.printBoard();

		board.setPlayer(Alliance.BLACK);
		System.out.println("All "+board.pieceBoard[0][0].getRule().getAllListLocationCanMoveWhenCheckmate());
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null)
					System.out.println(
							board.pieceBoard[i][j].getLinkImg() + " " + board.pieceBoard[i][j].getLocation().toString()
									+ "\t" + board.pieceBoard[i][j].getRule().getRealLocationCanMove());
			}
		}
	}
}
