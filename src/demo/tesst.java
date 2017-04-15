package demo;

import chess.ColorPiece;
import core.ChessBoard;
import core.ChessGoalTest;
import core.Location;

public class tesst {
	public static void main(String[] args) {
		// Piece[][] pie = new Piece[8][8];

		ChessBoard board = new ChessBoard();
		board.setPlayer(ColorPiece.BLACK);
		board.setPieceAtLocation(new Location(2,5), board.pieceBoard[7][3]);
		board.removePiece(new Location("D1"));
		
		board.setPieceAtLocation(new Location(5,4), board.pieceBoard[7][0]);
		board.removePiece(new Location("A1"));
		ChessGoalTest goal = new ChessGoalTest(board);
		board.printBoard();
		double x = System.currentTimeMillis();
		System.out.println("check: " + goal.checkmate());
		System.out.println("checkmate: " + goal.checkWin());
		System.out.println("real: " + board.pieceBoard[0][4].getRule().getRealLocationCanMove());
		double y = System.currentTimeMillis();
		System.out.println(y - x + " ms =================\n\n");

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null) {
					System.out
							.println(board.pieceBoard[i][j].getLinkImg() + " - " + board.pieceBoard[i][j].getLocation()
									+ " - " + board.pieceBoard[i][j].getRule().getRealLocationCanMove());
				}
			}
		}
	}
}
