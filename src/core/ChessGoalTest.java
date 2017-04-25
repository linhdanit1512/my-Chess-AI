package core;

import java.util.List;

import chess.Piece;

public class ChessGoalTest {

	public ChessGoalTest() {
		super();
	}

	/**
	 * 
	 * @param player
	 * @return kiem tra chieu bi
	 */
	public static boolean checkWin(ChessBoard board) {
		if (checkmate(board)) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					Piece piece = board.pieceBoard[i][j];
					if (piece != null && piece.getColor() == board.getPlayer()) {
						List<Location> listTMP = piece.getRule().getRealLocationCanMove();
						if (listTMP != null && !listTMP.isEmpty()) {
							return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param player
	 * @return kiem tra co hoa
	 */
	public static boolean checkDraw(ChessBoard board) {
		if (checkmate(board))
			return false;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null) {
					if (board.pieceBoard[i][j].getColor() == board.getPlayer()) {
						List<Location> tmp = board.pieceBoard[i][j].getRule().getRealLocationCanMove();
						if (tmp != null && !tmp.isEmpty()) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param player
	 * @return kiem tra vua co bi chieu hay khong
	 * @see rule.Rule.getAllLocationControl(Location)
	 * 
	 */
	public static boolean checkmate(ChessBoard board) {
		Piece king = board.getKing().get(board.getPlayer());
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null && board.pieceBoard[i][j].getColor() != king.getColor()) {
					if (board.pieceBoard[i][j].getRule().getAllLocationControl().contains(king.getLocation()))
						return true;
				}
			}
		}
		return false;
	}
}
