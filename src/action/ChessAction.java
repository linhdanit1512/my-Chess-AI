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

	public static void main(String[] args) {
		ChessBoard chess = new ChessBoard();
		ChessAction ch = new ChessAction(chess);
		System.out.println(ch.push(new Move(chess.getPieceAt(new Location(6, 4)), new Location(5, 4))));
	}

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
		if (move.getFrom().getLocation().equals(move.getTo()))
			return false;
		if (move.getFrom().getColor() == board.getPlayer())
			if (move.getFrom().getRule().getRealLocationCanMove().contains(move.getTo())) {
				if (castling(move))
					return true;
				move.setPrisoner(board.getPieceAt(move.getTo()));
				moves.push(move);
				board.setPieceAtLocation(move.getTo(), move.getFrom());
				board.getPieceAt(move.getTo()).updateMove();
				return true;
			}

		return false;
	}

	public boolean castling(Move move) {
		if (move != null)
			if (move.getFrom().getType().equals(PieceType.KING)) {
				if (move.getFrom().getRule().getRule() != null) {
					Castling castling = (Castling) move.getFrom().getRule().getRule();
					List<Location> list = castling.castling(move.getFrom());
					if (list != null && !list.isEmpty()) {
						if (list.contains(move.getTo())) {
							moves.push(move);
							board.setPieceAtLocation(move.getTo(), move.getFrom());
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

	public Move redo() {
		if (undo.size() <= 6) {
			Move move = moves.peek();
			undo.push(move);
			Piece pieceFrom = move.getFrom();
			Piece pieceTo = move.getPrisoner();
			Location lo = move.getTo();
			Location preLoca = pieceFrom.getLocation();
			board.pieceBoard[preLoca.getX()][preLoca.getY()] = board.pieceBoard[lo.getX()][lo.getY()];

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
