package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import action.ChessAction;
import action.Move;
import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import core.Location;

public class Training {
	ChessBoard board;
	ChessAction action;
	int maxMove = 15;

	public Training(ChessAction action) {
		super();
		this.action = action;
		this.board = action.board;
	}

	public Move bestMove() {
		List<Move> list = dicision();
		if (list != null && list.size() > 0)
			return dicision().get(0);
		return null;
	}

	@SuppressWarnings("static-access")
	public List<Move> dicision() {
		List<Move> list = board.getAllMovesForCurrentPlayer(board.getPlayer());
		List<Move> result = new ArrayList<Move>();
		for (Move move : list) {
			Status status = new Status(board, move, action.count);
			// System.out.println(status.getString());
			int score = shouldMove(status);
			move.setScore(score);
		}
		list.sort(new Comparator<Move>() {

			@Override
			public int compare(Move m1, Move m2) {
				if (m1.getScore() > m2.getScore())
					return -1;
				else if (m1.getScore() < m2.getScore())
					return 1;
				else {
					if (m1.getPrisoner() != null && m2.getPrisoner() == null)
						return -1;
					else if (m1.getPrisoner() == null && m2.getPrisoner() != null)
						return 1;
					else if (m1.getPieceFrom().getScore() > m2.getPieceFrom().getScore()) {
						return 1;
					} else if (m1.getPieceFrom().getScore() < m2.getPieceFrom().getScore()) {
						return -1;
					}
					if (m1.getPieceFrom().getScore() > m2.getPieceFrom().getScore()) {
						return -1;
					} else if (m1.getPieceFrom().getScore() < m2.getPieceFrom().getScore()) {
						return 1;
					} else
						return 0;
				}
			}
		});

		int count = 0;
		while (count < maxMove && list.size() - 1 > count) {
			result.add(list.get(count));
			count++;
		}

		return result;
	}

	public int shouldMove(Status status) {
		if (status.isCheckMate()) {
			return Integer.MAX_VALUE;
		}
		int count = 0;
		/**
		 * mới bắt đầu game và gần vùng tung tâm
		 */
		if (status.isNearCenterThan() && status.isBegin())
			count += 3;

		/**
		 * ko phải vùng trung tâm, tàn cờ
		 */
		if (!status.isNearCenterThan() && status.isFinal())
			count -= 3;
		/**
		 * có ăn quân
		 */
		if (status.hasPrisoner()) {
			count += status.move.getPrisoner().getScore() + 20;
		}
		/**
		 * phá thế tấn công của địch
		 */
		if (status.hasEnemyAttack() && !status.hasAfterEnemyAttack()) {
			count += 200;
		}
		if (status.hasEnemyAttack() && status.hasAfterEnemyAttack()) {
			count -= 200;
		}
		/**
		 * ko có tấn công, ko bị ăn
		 */
		if (status.isSafe() && !status.hasEnemyAttack()) {
			count += 5;
		}

		if (status.listEnemyFrom != null && status.listEnemyFrom.size() > 0)
			count += status.piece.getScore();
		/**
		 * bị ăn
		 */
		if (!status.isSafe() && (status.listMyPiecesSameMove == null || status.listMyPiecesSameMove.isEmpty())) {
			count -= status.piece.getScore() * 1.5;
		}

		if (status.listEnemyTo != null && status.listMyPiecesSameMove != null
				&& status.listMyPiecesSameMove.size() > 1) {
			for (Piece p : status.listEnemyTo) {
				if (p.getScore() < status.piece.getScore())
					count += p.getScore() + 20;
			}
			count -= status.piece.getScore() * 2;
		}
		/**
		 * có thể chiếu vua và không bị ăn
		 */
		if (status.shouldMoveToCheck()) {
			count += 100;
		}
		/**
		 * bước sau không có quân tấn công vua
		 */
		if (!status.hasAfterEnemyAttack()) {
			count++;
		}

		if (status.isPawn()) {
			if (status.isNearCenterThan() && !status.isBegin())
				count -= 5;
			if (status.isNearCenter() && !status.isBegin())
				count -= 3;
			/**
			 * nước phong hậu
			 * 
			 * ko có địch, hoặc địch là vua hay hậu
			 */
			if (status.isPromotion()) {
				if (status.listEnemyTo == null || status.listEnemyTo.size() == 0) {
					count += 90 * 2;
				} else if (status.listEnemyTo.size() == 1 && status.listMyPiecesSameMove.size() > 1) {
					Piece p = status.listEnemyTo.get(0);
					if (p.getType() == PieceType.KING) {
						count += 200;
					} else if (p.getType() == PieceType.QUEEN) {
						count += 50;
					}
				}
			}
			if (status.isPassant()) {
				/*
				 * đã tính điểm ăn quân rồi
				 */
				count += 5;
			}
		}

		if (status.piece.getMove() == 0 && !status.isKing()) {
			count++;
		}

		if (status.isKing()) {
			if ((status.isNearCenter() || status.isNearCenterThan()) && !status.isFinal())
				count -= 30;
			if (status.isCastling()) {
				List<Location> list = status.rule.getAllLocationControl();
				for (Location l : list) {
					List<Piece> enemy = status.rule.getEnemyControlAtLocation(l, status.playerCurrent);
					if (enemy != null && enemy.size() > 0) {
						List<Piece> me = status.rule.getPieceControlAt(l);
						if (me.size() - enemy.size() >= enemy.size()) {
							count += 500;
						}
					}
				}
			} else if (status.piece.getMove() == 0)
				count -= 100000;
		}
		if (status.isBishop() || status.isKnight() || status.isQueen() || status.isRook()) {
			if (status.isNearCenter()) {
				count += 30;
			} else if (status.isNearCenterThan())
				count += 40;
			if (count() >= 4) {
				count += 40;
			}
		}
		return count;
	}

	public void print() {
		for (Move move : dicision()) {
			System.out.println(move.toStrings());
		}
	}

	@SuppressWarnings("static-access")
	public int count() {
		return action.count;
	}
}
