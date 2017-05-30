package action;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import core.ChessBoard;
import core.Player;
import gui.Board;

public class ChessAction implements Observer {
	private Stack<Move> moves = new Stack<Move>();
	Observable ob;
	public ChessBoard board;
	public Board view;
	public static int count = 1;

	public ChessAction(Observable ob, Board view) {
		super();
		this.ob = ob;
		this.view = view;
		ob.addObserver(this);
		if (ob instanceof ChessBoard)
			this.board = (ChessBoard) ob;
	}

	/**
	 * Thực hiện nước đi
	 * 
	 * @param move
	 * @return
	 */
	public Move execute(Move move) {
		if (board.makeMove(move)) {
			System.out.println("Action " + move.toStrings());
			if (view.makeMove(move, board.getPlayer())) {
				count++;
				moves.push(move);
				board.setPlayer(Player.changePlayer(board.getPlayer()));
				board.setMeasurements(board.getPlayer(), board.pieceBoard, move);
				board.printBoard();
				return move;
			}
		}
		return null;
	}

	/**
	 * Quay lại nước trước đó
	 * 
	 * @param move
	 * @return
	 */
	public Move restore() {
		System.out.println("moves size" + moves.size());
		Move move = pop();
		if (move != null) {
			board.unMakeMove(move, peek());
			view.unMakeMove(move, peek());
			count--;
			undo.push(move);
			board.setPlayer(Player.changePlayer(board.getPlayer()));
			board.setMeasurements(board.getPlayer(), board.pieceBoard, peek());
			board.printBoard();
			return move;
		}
		return null;
	}

	Stack<Move> undo = new Stack<Move>();

	public Move undo() {
		if (undo.size() >= 10)
			return null;
		else {
			return restore();
		}
	}

	public Move redo() {
		if (undo.isEmpty())
			return null;
		else {
			Move move = undo.pop();
			return execute(move);
		}
	}

	public Move pop() {
		if (moves.isEmpty())
			return null;
		else
			return moves.pop();
	}

	public Move peek() {
		if (moves.isEmpty())
			return null;
		return moves.peek();
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
