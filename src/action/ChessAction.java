package action;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

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
	 * @param move: nuoc di
	 * @return: quan co an duoc.
	 * 
	 * null: khong an quan
	 */
	public Piece move(Move move) {
		if (move.getFrom().getLocation() == move.getTo())
			return null;
		if (move.getFrom().getRule().getRealLocationCanMove().contains(move.getTo())) {
			moves.push(move);
			return board.getPieceAt(move.getTo());
		}

		return null;
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
		
		if(board.getPieceAt(move.getTo())!=null){
			
		}else{
			
		}
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
