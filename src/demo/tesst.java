package demo;

import java.util.List;
import java.util.Map;

import action.ChessAction;
import action.Move;
import chess.ColorPiece;
import chess.Piece;
import core.ChessBoard;
import core.Location;

public class tesst {
	public static void main(String[] args) {
		// Piece[][] pie = new Piece[8][8];

		ChessBoard board = new ChessBoard();
		ChessAction action = new ChessAction(board);
		System.out.println(action.push(new Move(new Location(6, 4), new Location(4, 4))));
		board.printBoard();

		board.setPlayer(ColorPiece.BLACK);
		System.out.println(action.push(new Move(new Location(1, 3), new Location(2, 3))));
		board.printBoard();

		board.setPlayer(ColorPiece.WHITE);
		System.out.println(action.push(new Move(new Location(7, 5), new Location(3, 1))));
		board.printBoard();

		board.setPlayer(ColorPiece.BLACK);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null)
					System.out.println(board.pieceBoard[i][j].getLinkImg() + "  -  "
							+ board.pieceBoard[i][j].getRule().getRealLocationCanMove());
			}
		}
		System.out.println("==================================");
		System.out.println(
				board.pieceBoard[1][1].getRule().getEnemyControlAtLocation(new Location(1, 3), ColorPiece.WHITE));
		for (Map.Entry<Piece, List<Location>> p : board.pieceBoard[0][4].getRule()
				.getAllListLocationCanMoveWhenCheckmate().entrySet()) {
			System.out.println("key" + p.getKey());
			System.out.println(p.getValue().toString());
		}

		System.out.println(action.push(new Move(new Location(0, 2), new Location(1, 3))));
		board.printBoard();
	}
}
