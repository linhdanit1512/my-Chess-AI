package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import action.Move;
import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import core.Location;
import core.Player;
import rule.Rule;

public class Status {
	ChessBoard board;
	Move move;
	Piece piece;
	Rule rule;
	List<Piece> listEnemyFrom;
	List<Piece> listEnemyTo;
	List<Piece> listMyPiecesSameMove;
	List<Piece> listMyPieceSafePieceFrom;
	Location from, to;
	int playerCurrent;
	int playerEnemy;
	Piece king;
	Piece enemyKing;
	int stt;

	public Status(ChessBoard board, Move move, int stt) {
		super();
		this.board = board;
		this.move = move;
		from = move.getFrom();
		to = move.getTo();
		this.piece = board.getPieceAt(from);
		this.rule = piece.getRule();
		listEnemyFrom = listEnemyPieceAtFrom();
		listEnemyFrom.sort(comp);
		listEnemyTo = listEnemyPieceAtTo();
		listEnemyTo.sort(comp);
		listMyPiecesSameMove = listPieceSameMove();
		if (listMyPiecesSameMove != null && listMyPiecesSameMove.size() > 0)
			listMyPiecesSameMove.sort(comp);
		listMyPieceSafePieceFrom = listMyPieceSafePieceFrom();
		if (listMyPieceSafePieceFrom != null && listMyPieceSafePieceFrom.size() > 0)
			listMyPieceSafePieceFrom.sort(comp);
		playerCurrent = board.getPlayer();
		playerEnemy = Player.changePlayer(playerCurrent);
		king = board.getKing().get(playerCurrent);
		enemyKing = board.getKing().get(playerEnemy);
		this.stt = stt;

	}

	/**
	 * giai đoạn cờ tàn
	 * 
	 * @return
	 */
	public boolean isFinal() {
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.isHasPiece(i, j)) {
					count++;
				}
			}
		}
		return count < 10;
	}

	/**
	 * có phải chỉ mới bắt đầu game hay không
	 * 
	 * @return
	 */
	public boolean isBegin() {
		return stt < 5;
	}

	/**
	 * nước này đi gần vùng trung tâm bàn cờ hay ko
	 * 
	 * @return
	 */
	public boolean isNearCenterThan() {
		return ((to.getX() == 3 || to.getX() == 4) && (to.getY() == 3 || to.getY() == 4));

	}

	/**
	 * vùng cận trung tâm
	 * 
	 * @return
	 */
	public boolean isNearCenter() {
		return ((to.getX() == 2 ^ to.getX() == 5) && (to.getY() > 1 && to.getY() < 6))
				|| ((to.getY() == 2 ^ to.getY() == 5) && (to.getX() > 1 && to.getX() < 6));
	}

	/**
	 * nếu có thể chiếu vua đối phương thì có nên đi hay không?
	 * 
	 * ý tưởng:
	 * 
	 * đầu tiên là phải chiếu vua
	 * 
	 * nếu chiếu thì lấy các ô từ vị trí To tới vua đối phương và lấy các quân
	 * cờ có thể tới các ô đó (chưa biết trường hợp mà mình đi quân khác chiếu
	 * vua, tạm bỏ qua)
	 * 
	 * kiểm tra xem mình có mấy quân có thể ăn quân tại ô mà quân địch có thể
	 * tới che và cả của địch nữa
	 * 
	 * nếu mà điểm mình thu được lời thì nên đi, còn không thì ko nên đi
	 * 
	 * @return
	 */
	public boolean shouldMoveToCheck() {
		if (hasCheckEnemyKingAfterMove()) {
			List<Piece> list = enemyCumbersomeCheckKing();
			list.sort(comp);
			if (list != null && !list.isEmpty()) {
				for (Piece p : list) {
					int score = 0;
					List<Piece> me = rule.getEnemyControlAtLocation(p.getLocation(), playerCurrent);
					me.sort(comp);
					List<Piece> enemy = rule.getEnemyControlAtLocation(p.getLocation(), playerEnemy);
					enemy.sort(comp);
					if (me != null && !me.isEmpty()) {
						if (enemy.size() <= me.size()) {
							for (int i = 0; i < enemy.size(); i++) {
								score += me.get(i).getScore();
								score -= enemy.get(i).getScore();
							}
						} else {
							for (int i = 0; i < me.size(); i++) {
								score += me.get(i).getScore();
								score -= enemy.get(i).getScore();
							}
						}
						if (score > 0)
							return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * Nếu như nó chiếu vua và có quân có thể chặn lại đường chiếu
	 * 
	 * @return
	 */
	public List<Piece> enemyCumbersomeCheckKing() {
		if (hasCheckEnemyKingAfterMove()) {
			List<Piece> result = new ArrayList<>();
			List<Location> list = rule.getDistanceLocation(to, enemyKing.getLocation());
			if (list != null && !list.isEmpty()) {
				for (Location loca : list) {
					List<Piece> tmp = rule.getEnemyControlAtLocation(loca, playerCurrent);
					if (tmp != null && !tmp.isEmpty()) {
						result.addAll(tmp);
					}
				}
			}
			return result;
		}
		return null;
	}

	public boolean hasEnemyCumbersomeCheckKing() {
		List<Piece> l = enemyCumbersomeCheckKing();
		return l != null && !l.isEmpty();
	}

	public boolean hasCheckEnemyKingAfterMove() {
		return (rule.checkCross(to, enemyKing.getLocation()) || rule.checkHorizontal(to, enemyKing.getLocation())
				|| rule.checkVertical(to, enemyKing.getLocation()));
	}

	/**
	 * Nếu như đến ô đó không bị ăn lại
	 * 
	 * @param move
	 * @return
	 */
	public boolean isSafe() {
		return (listEnemyTo == null || listEnemyTo.isEmpty());
	}

	/**
	 * nó đến ô kia và che cho vua
	 * 
	 * (khi vua bị chiếu)
	 * 
	 * @return
	 */
	public boolean isBeetweenKingAndEnemy() {
		Piece l = rule.checkBeetween(to, king, playerCurrent);
		return l != null;
	}

	/**
	 * sau khi thực hiện move thì nó bị tấn công hay không?
	 * 
	 * @return
	 */
	public boolean hasAfterEnemyAttack() {
		if (checkPositionFromVsKing()) {
			if (listEnemyTo != null && !listEnemyTo.isEmpty()) {
				if (listMyPiecesSameMove != null && !listMyPiecesSameMove.isEmpty()) {
					if (listMyPiecesSameMove.contains(king)) {
						/**
						 * nếu mà nó có nhiều hơn mình từ 2 con trở lên thì chắc
						 * chắn là nó sẽ có thể chiếu vua
						 * 
						 */
						if (listEnemyTo.size() - listMyPiecesSameMove.size() - 1 > 1)
							return true;
					} else {
						int myscore = 0;
						int enescore = 0;
						int size = Math.min(listEnemyTo.size(), listMyPiecesSameMove.size());
						for (int i = 0; i < size; i++) {
							myscore += listMyPiecesSameMove.get(i).getScore();
							enescore += listEnemyTo.get(i).getScore();
						}
						if (myscore > enescore)
							return true;
					}
				} else {
					if (listEnemyTo.size() > 1)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @return quân đối phương đang có thể tấn công vua <br>
	 *         (chưa thực hiện move)
	 * 
	 */
	public boolean hasEnemyAttack() {
		if (checkPositionFromVsKing()) {
			if (listEnemyFrom != null && !listEnemyFrom.isEmpty()) {
				if (listMyPieceSafePieceFrom != null && !listMyPieceSafePieceFrom.isEmpty()) {
					if (listMyPieceSafePieceFrom.contains(king)) {
						/**
						 * nếu mà nó có nhiều hơn mình từ 2 con trở lên thì chắc
						 * chắn là nó sẽ có thể chiếu vua
						 * 
						 */
						if (listEnemyFrom.size() - listMyPieceSafePieceFrom.size() - 1 > 1)
							return true;
					} else {
						int myscore = 0;
						int enescore = 0;
						int size = Math.min(listEnemyFrom.size(), listMyPieceSafePieceFrom.size());
						for (int i = 0; i < size; i++) {
							myscore += listMyPieceSafePieceFrom.get(i).getScore();
							enescore += listEnemyFrom.get(i).getScore();
						}
						if (myscore > enescore)
							return true;
					}
				} else {
					if (listEnemyFrom.size() > 1)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * cái vị trí mà nó đang đứng có liên quan ngang dọc chéo gì ko, trường hợp
	 * quân mã thì chịu, thêm vào chắc 1 nùi
	 * 
	 * @return
	 */
	public boolean checkPositionFromVsKing() {
		return (rule.checkCross(from, king.getLocation()) || rule.checkHorizontal(from, king.getLocation())
				|| rule.checkVertical(from, king.getLocation()));
	}

	public List<Piece> listMyPieceSafePieceFrom() {
		List<Piece> list = rule.getPieceControlAt(from);
		if (list != null && list.size() > 0) {
			List<Piece> result = new ArrayList<>();
			for (Piece p : list) {
				if (p.getAlliance() == playerCurrent)
					result.add(p);
			}
			return result;
		}
		return null;
	}

	public int checkEnemyControlLocationNearKing() {
		int result = 0;
		List<Location> list = rule.getAllLocationControl();
		for (Location l : list) {
			List<Piece> lst = rule.getEnemyControlAtLocation(l, playerCurrent);
			if (lst != null && lst.size() > 0) {
				result += lst.size();
			}
		}
		return result;
	}

	/**
	 * 
	 * @return quân địch có thể tới ăn quân ở ô đích.
	 */
	public List<Piece> listEnemyPieceAtTo() {
		List<Piece> list = rule.getEnemyControlAtLocation(to, playerCurrent);
		return list;
	}

	public List<Piece> listEnemyPieceAtFrom() {
		List<Piece> list = rule.getEnemyControlAtLocation(from, playerCurrent);
		return list;
	}

	/**
	 * 
	 * @return các quân cờ khác cũng có thể tới ô này
	 */
	public List<Piece> listPieceSameMove() {
		List<Piece> list = rule.getPieceControlAt(to);
		if (list != null && list.size() > 0) {
			List<Piece> result = new ArrayList<>();
			for (Piece p : list) {
				if (p.getAlliance() == playerCurrent)
					result.add(p);
			}
			return result;
		}
		return null;
	}

	/**
	 * 
	 * @return điểm dự kiến cho nước đi
	 */
//	public int score() {
//		Evaluate eval = new Evaluate(board);
//		return eval.score(to);
//	}

	/**
	 * 
	 * @return ăn được quân
	 */
	public boolean hasPrisoner() {
		return (move.getPrisoner() != null);
	}

	/**
	 * phong hậu
	 * 
	 * @return
	 */
	public boolean isPromotion() {
		return move.isPromotion();
	}

	/**
	 * bắt chốt qua đường
	 * 
	 * @return
	 */
	public boolean isPassant() {
		return move.isPassant();
	}

	public boolean isPawn() {
		return piece.getType() == PieceType.PAWN;
	}

	public boolean isBishop() {
		return piece.getType() == PieceType.BISHOP;
	}

	public boolean isRook() {
		return piece.getType() == PieceType.ROOK;
	}

	public boolean isKnight() {
		return piece.getType() == PieceType.KNIGHT;
	}

	public boolean isQueen() {
		return piece.getType() == PieceType.QUEEN;
	}

	public boolean isKing() {
		return piece.getType() == PieceType.KING;
	}

	public boolean isCheckMate() {
		return move.isChessmate();
	}

	public boolean isDraw() {
		return move.isDraw();
	}

	/**
	 * nhập thành
	 * 
	 * @return
	 */
	public boolean isCastling() {
		return move.isCastlingKing() || move.isCastlingQueen();
	}

	public String getString() {
		StringBuffer s = new StringBuffer();
		s.append("Tuple t" + stt + " = new Tuple();\n");
		s.append("t" + stt);
		s.append(".add(" + isPassant());
		s.append(").add(" + isPromotion());
		s.append(").add(" + isBegin());
		s.append(").add(" + isFinal());
		s.append(").add(" + isNearCenterThan());
		s.append(").add(" + isCastling());
		s.append(").add(" + isSafe());
		s.append(").add(" + isBeetweenKingAndEnemy());
		s.append(").add(" + hasPrisoner());
		s.append(").add(" + checkPositionFromVsKing());
		s.append(").add(" + hasEnemyAttack());
		s.append(").add(" + hasAfterEnemyAttack());
		s.append(").add(" + hasCheckEnemyKingAfterMove());
		s.append(").add(" + hasEnemyCumbersomeCheckKing());
		s.append(").add(" + shouldMoveToCheck());
		s.append(").add(" + isPawn());
		s.append(").add(" + isBishop());
		s.append(").add(" + isRook());
		s.append(").add(" + isKnight());
		s.append(").add(" + isQueen());
		s.append(").add(" + isKing());
		s.append(");");

		return s.toString();
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