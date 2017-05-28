package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import action.ChessAction;
import action.Move;
import chess.Alliance;
import chess.Piece;
import chess.PieceType;

public class ChessGoalState {

	public static final int CHECKMATE = 12;
	public static final int DRAW = 13;
	public static final int CHECK = 14;

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
					if (piece != null && piece.getAlliance() == board.getPlayer()) {
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
	 * @param board:
	 *            bàn cờ hiện tại
	 * 
	 *            action: các nước đi đã đi
	 * 
	 * @return kiem tra co hoa.
	 * 
	 *         các trường hợp cờ hòa:
	 * 
	 *         1. Hòa về lực lượng
	 * 
	 *         2. Hòa do hết nước đi (PAT)
	 * 
	 *         3. Hòa do bất biến 3 lần
	 * 
	 *         4. Hòa 50 nước
	 * 
	 *         5. Hòa do 2 bên thỏa thuận đồng ý hòa (cái này chắc làm bên giao
	 *         diện)
	 * 
	 */
	public static boolean checkDraw(ChessBoard board, ChessAction action) {
		// neu vua dang bi chieu thi false
		if (checkmate(board))
			return false;
		// thoa luat hoa luc luong
		if (notEnoughPower(board))
			return true;
		// thoa luat PAT
		if (stalemate(board))
			return true;
		// thoa luat 50 buoc di
		if (move50(action))
			return true;
		// thoa luat bat bien 3 lan
		if (immutable3(action))
			return true;
		return false;
	}

	/**
	 * 
	 * @return kiem tra vua co bi chieu hay khong
	 * @see rule.Rule.getAllLocationControl()
	 * 
	 */
	public static boolean checkmate(ChessBoard board) {
		Piece king = board.getKing().get(board.getPlayer());
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null && board.pieceBoard[i][j].getAlliance() != king.getAlliance()) {
					List<Location> list = board.pieceBoard[i][j].getRule().getAllLocationControl();
					if (list != null) {
						if (list.contains(king.getLocation()))
							return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param board
	 * @return 1. Hòa về lực lượng: Một ván cờ hòa khi không bên nào đủ lực
	 *         lượng để chiến thắng
	 * 
	 *         - Vua - Vua
	 * 
	 *         - Vua + Mã - Vua
	 * 
	 *         - Vua + Tượng - Vua
	 * 
	 */
	private static boolean notEnoughPower(ChessBoard board) {
		List<Piece> white = new ArrayList<>();
		List<Piece> black = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null) {
					if (board.pieceBoard[i][j].getAlliance() == Alliance.BLACK) {
						black.add(board.pieceBoard[i][j]);
					} else if (board.pieceBoard[i][j].getAlliance() == Alliance.WHITE) {
						white.add(board.pieceBoard[i][j]);
					}
				}
			}
		}
		/**
		 * nếu trên bàn cờ chỉ có 2 quân vua
		 */
		if (black.size() + white.size() == 2)
			return true;
		else if (black.size() <= 2 && white.size() <= 2) {
			List<Piece> tmp = new ArrayList<>();
			tmp.addAll(black);
			tmp.addAll(white);
			/**
			 * vì trên bàn cờ luôn luôn phải có 2 quân vua, cho nên chỉ cần kiểm
			 * tra xem quân còn lại có phải là quân cờ yếu hay ko
			 */
			int count = 0;
			for (Piece p : tmp) {
				if (p.getType() == PieceType.BISHOP || p.getType() == PieceType.KNIGHT)
					count++;
			}
			if (count == tmp.size() - 2)
				return true;
		}
		return false;
	}

	/**
	 * 2. Hòa do hết nước đi (PAT)
	 * 
	 * Hòa PAT xảy ra trong trường hợp khi bên đến lượt đi hết nước đi hợp lệ,
	 * nhưng Vua lại không bị chiếu. Đây là lỗi rất hay gặp trong thi đấu cờ
	 * vua, kể cả những vận động viên chuyên nghiệp.
	 * 
	 */
	private static boolean stalemate(ChessBoard board) {
		if (checkmate(board))
			return false;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.pieceBoard[i][j] != null) {
					if (board.pieceBoard[i][j].getAlliance() == board.getPlayer()) {
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
	 * 3. Luật 50 nước đi:
	 * 
	 * nếu trong suốt 50 nước liên tục của một ván cờ mà không có một nước bắt
	 * quân hay nước đi Tốt nào thì ván cờ đó hòa.
	 */

	private static boolean move50(ChessAction action) {
		Stack<Move> moves = action.getMoves();
		if (moves.size() >= 50) {
			for (int i = 0; i < 50; i++) {
				Move move = moves.pop();
				if (move != null) {
					if (move.getPrisoner() != null)
						return false;
					if (move.getPieceFrom().getType() == PieceType.PAWN)
						return false;
				} else
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 4. Bất biến 3 lần:
	 * 
	 * nếu một thế cờ được lặp đi lặp lại 3 lần và lượt đi thuộc về cùng một bên
	 * thì ván cờ đó hòa.
	 */

	private static boolean immutable3(ChessAction action) {

		return false;
	}

}
