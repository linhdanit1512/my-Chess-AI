package action;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import chess.ColorPiece;
import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import core.Location;
import rule.Castling;

public class ChessAction implements Observer {
	private Stack<Move> moves = new Stack<Move>();
	Observable ob;
	public ChessBoard board;
	private int count = 0;

	public ChessAction(Observable ob) {
		super();
		this.ob = ob;
		ob.addObserver(this);
		if (ob instanceof ChessBoard)
			this.board = (ChessBoard) ob;
	}

	/**
	 * 
	 * @param move:
	 *            nuoc di
	 * @return:
	 * 
	 */
	public boolean push(Move move) {
		if (move == null)
			return false;
		if (move.getFrom().equals(move.getTo()))
			return false;
		if (null == move.getPieceFrom())
			move.setPieceFrom(board.getPieceAt(move.getFrom()));
		if (move.getPrisoner() == null)
			move.setPrisoner(board.getPieceAt(move.getTo()));
		if (move.getPieceFrom().getColor() == board.getPlayer())
			if (move.getPieceFrom().getRule().getRealLocationCanMove().contains(move.getTo())) {
				count++;
				if (castling(move))
					return true;
				moves.push(move);
				board.setPieceAtLocation(move.getTo(), move.getPieceFrom());
				board.getPieceAt(move.getTo()).updateMove();
				return true;
			}

		return false;
	}

	public boolean castling(Move move) {
		if (move != null)
			if (move.getPieceFrom().getType().equals(PieceType.KING)) {
				if (move.getPieceFrom().getRule().getRule() != null) {
					Castling castling = (Castling) move.getPieceFrom().getRule().getRule();
					List<Location> list = castling.castling(move.getPieceFrom());
					if (list != null && !list.isEmpty()) {
						if (list.contains(move.getTo())) {
							moves.push(move);
							board.setPieceAtLocation(move.getTo(), move.getPieceFrom());
							board.getPieceAt(move.getTo()).updateMove();
							int x = move.getTo().getX();
							int y = move.getTo().getY();
							if (y == 2) {
								board.setPieceAtLocation(new Location(x, 3), board.pieceBoard[x][0]);
								board.getPieceAt(new Location(x, 3)).updateMove();
								return true;
							}

							if (y == 6) {
								board.setPieceAtLocation(new Location(x, 5), board.pieceBoard[x][7]);
								board.getPieceAt(new Location(x, 5)).updateMove();
								return true;
							}
						}
					}
				}
			}
		return false;
	}

	public Move peek() {
		return moves.peek();
	}

	Stack<Move> undo = new Stack<Move>();

	public Move undo() {
		if (undo.size() <= 6) {
			Move move = moves.peek();
			undo.push(move);
			Piece pieceFrom = move.getPieceFrom();
			Piece prisoner = move.getPrisoner();
			Location preLoca = move.getFrom();
			Location lo = move.getTo();
			board.setPieceAtLocation(preLoca, pieceFrom);
			board.getPieceAt(preLoca).updateUndoMove();
			board.setPieceAtLocation(lo, prisoner);
			count--;
			return moves.pop();
		}
		return null;
	}

	public Move redo() {
		if (undo.isEmpty())
			return null;
		else {
			Move move = undo.peek();
			push(move);
			return undo.pop();
		}
	}

	public String toPrint(Move move) {
		if (move == null)
			return null;
		if (move.getPieceFrom() == null || move.getTo() == null)
			return null;
		if (move.getPieceFrom().getLocation().equals(move.getTo()))
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append(move.getPieceFrom().getAcronym());
		List<Piece> listTMP = move.getPieceFrom().getRule().getEnemyControlAtLocation(move.getTo(), ColorPiece.BOTH);
		if (listTMP.size() == 0) {
		} else if (listTMP.size() == 1) {
		} else if (listTMP.size() > 1) {
			sb.append(move.getPieceFrom().getLocation().getYString());
		}
		if (board.getPieceAt(move.getTo()) != null) {
			sb.append("x");

		} else {

		}
		sb.append(board.getPieceAt(move.getTo()).getLocation().toWordString());
		return sb.toString();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == null)
			return;
		if (o instanceof ChessBoard) {
			this.ob = o;
			this.board = (ChessBoard) ob;
		}
	}

	public Stack<Move> getMoves() {
		return moves;
	}

	public void setMoves(Stack<Move> moves) {
		this.moves = moves;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
