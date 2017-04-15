package core;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import chess.Piece;

public class ChessGoalTest implements Observer {
	static ChessBoard board;
	public static int player = 0;

	public ChessGoalTest(ChessBoard board) {
		super();
		ChessGoalTest.board = board;
		ChessGoalTest.player = board.getPlayer();
	}

	/**
	 * 
	 * @param player
	 * @return kiem tra chieu bi
	 */
	public static boolean checkWin() {
		if (checkmate()) {
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
	public static boolean checkDraw() {
		if (checkmate())
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
	public static boolean checkmate() {
		Piece king = board.getKing().get(board.getPlayer());
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null && board.pieceBoard[i][j].getColor() != king.getColor()) {
					if (board.pieceBoard[i][j].getRule().checkContains(
							board.pieceBoard[i][j].getRule().getAllLocationControl(), king.getLocation()))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o != null) {
			if (o instanceof ChessBoard) {
				ChessGoalTest.board = (ChessBoard) o;
				ChessGoalTest.player = board.getPlayer();
			}
		}
	}
}
