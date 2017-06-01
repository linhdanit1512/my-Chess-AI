package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import core.Location;
import rule.Rule;

public class Evaluate {
	int[][] matrix = new int[8][8];
	ChessBoard board;
	int canMove = 1;
	int relative = 4;
	int relaKing = 8;

	public Evaluate(ChessBoard board) {
		super();
		this.board = board;
	}

	public int score(Location location) {
		int score = 0;
		Piece piece = board.getPieceAt(location);
		if (piece != null) {
			if (piece.getAlliance() != board.getPlayer()) {
				List<Piece> l = piece.getRule().getEnemyControlAtLocation(piece.getLocation(), board.getPlayer());
				Collections.sort(l, comp);
				if (l != null && l.size() > 0) {
					if (l.size() == 1 && l.get(0).getType() == PieceType.KING) {
						score += piece.getScore();
					} else {
						score += piece.getScore();
						score -= l.get(0).getScore();
					}
				} else {
					score += piece.getScore();
				}
			}
		} else {
			score += 1;
		}
		score += relativeScore(board, location, board.getPlayer());
		return score;
	}

	public int sumEval() {
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int c = score(new Location(i, j));
				sum += c;
			}
		}
		return sum;
	}

	public int relativeScore(ChessBoard board, Location location, int alliance) {
		int evalua = 0;
		Rule rule = board.getKing().get(alliance).getRule();
		List<Piece> list = rule.getPieceControlAt(location);
		List<Piece> l1 = new ArrayList<>();
		List<Piece> l2 = new ArrayList<>();
		for (Piece piece : list) {
			if (piece.getAlliance() == alliance) {
				l1.add(piece);
			} else {
				l2.add(piece);
			}
		}

		Collections.sort(l1, comp);
		Collections.sort(l2, comp);
		if (l1.size() < l2.size()) {
			/**
			 * neu nhu la luot cua minh thi quan ben minh bi an het nhung quan
			 * doi phuong thi ko bi an het
			 */
			if (board.getPlayer() == alliance) {
				for (int i = 0; i < l1.size(); i++) {
					evalua -= l1.get(i).getScore();
					evalua += l2.get(i).getScore();
				}
			}
			/**
			 * neu nhu la luot cua doi phuong thi no di truoc an luon quan cua
			 * minh roi, noi thi hoi khó hiểu nhưng cứ thử
			 * 
			 * ví dụ như có 2 quân mình và 2 quân địch có thể ăn nhau, thằng kia
			 * đi trước nó ăn thì mình chỉ còn 1 quân ăn lại, do đó nó sẽ còn 1
			 * quân ko bị ăn, nên ô đó nếu đi tới thì điểm nó sẽ dở hơn (chỉ tốt
			 * hơn khi mình ko lỗ nhờ ăn được quân quan trọng thôi
			 * 
			 */
			else {
				if (l1.size() > 0) {
					for (int i = 0; i < l1.size() - 1; i++) {
						evalua -= l1.get(i).getScore();
						evalua += l2.get(i).getScore();
					}
					for (int i = l1.size() - 1; i < l2.size(); i++) {
						evalua -= l2.get(i).getScore();
					}
				}
			}
		}
		/**
		 * tương tự như trường hợp ở trên nhưng nó ngược lại
		 */
		else if (l1.size() > l2.size()) {
			if (board.getPlayer() == alliance) {
				if (l2.size() > 0) {
					for (int i = 0; i < l2.size() - 1; i++) {
						evalua -= l1.get(i).getScore();
						evalua += l2.get(i).getScore();
					}
					for (int i = l2.size() - 1; i < l2.size(); i++) {
						evalua += l2.get(i).getScore();
					}
				}
			} else {
				for (int i = 0; i < l2.size(); i++) {
					evalua -= l1.get(i).getScore();
					evalua += l2.get(i).getScore();
				}
			}
		}
		/**
		 * nếu bằng nhau thì thằng nào ăn trước nó sẽ còn lại 1 quân (nếu nó ăn)
		 */
		else {
			for (int i = 0; i < l2.size(); i++) {
				evalua -= l1.get(i).getScore();
				evalua += l2.get(i).getScore();
			}
			if (l2.size() > 0) {
				if (board.getPlayer() == alliance) {

					evalua += l1.get(l1.size() - 1).getScore();
				} else {
					evalua -= l2.get(l2.size() - 1).getScore();
				}
			}
		}
		return evalua;
	}

	Map<Piece, List<Location>> getAllLocationCanMove() {
		Map<Piece, List<Location>> map = new HashMap<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.isHasPiece(i, j)) {
					Location location = new Location(i, j);
					Piece piece = board.getPieceAt(location);
					if (piece.getAlliance() == board.getPieceAt(location).getAlliance()) {
						List<Location> list = piece.getRule().getRealLocationCanMove();
						if (list != null && !list.isEmpty()) {
							map.put(piece, list);
						}
					}
				}
			}
		}
		return map;
	}

	Comparator<Piece> comp = new Comparator<Piece>() {

		@Override
		public int compare(Piece p1, Piece p2) {
			int s1 = p1.getScore();
			int s2 = p2.getScore();
			if (s1 > s2)
				return 1;
			else if (s1 < s2)
				return -1;
			return 0;
		}
	};
}
