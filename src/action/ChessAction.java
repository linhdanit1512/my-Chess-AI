package action;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import chess.ColorPiece;
import chess.Piece;
import core.ChessBoard;

public class ChessAction implements Observer {
	private Stack<Move> moves = new Stack<Move>();
	Observable ob;
	ChessBoard board;

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
	public boolean move(Move move) {
		if (move == null)
			return false;
		if (move.getFrom().getLocation() == move.getTo())
			return false;
		if (move.getFrom().getRule().getRealLocationCanMove().contains(move.getTo())) {
			moves.push(move);
			board.setPieceAtLocation(move.getTo(), move.getFrom());
			board.removePiece(move.getFrom().getLocation());
			return true;
		}

		return false;
	}

	Stack<Move> undo = new Stack<Move>();

	public Move redo() {
		if (undo.size() <= 6) {
			undo.push(moves.peek());
			return moves.pop();
		}
		return null;
	}

	public Move undo() {
		if (undo.isEmpty())
			return null;
		else {
			moves.push(undo.peek());
			return undo.pop();
		}
	}

	public String toPrint(Move move) {
		if (move == null)
			return null;
		if (move.getFrom() == null || move.getTo() == null)
			return null;
		if (move.getFrom().getLocation().equals(move.getTo()))
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append(move.getFrom().getAcronym());
		List<Piece> listTMP = move.getFrom().getRule().getEnemyControlAtLocation(move.getTo(), ColorPiece.BOTH);
		if (listTMP.size() == 0) {
		} else if (listTMP.size() == 1) {
		} else if (listTMP.size() > 1) {
			sb.append(move.getFrom().getLocation().getYString());
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

}
