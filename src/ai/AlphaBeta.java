package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import action.ChessAction;
import action.Move;
import core.ChessBoard;
import core.Player;

public class AlphaBeta {
	public List<Node> rootsChildrenScore = new ArrayList<>();
	private Stack<Move> moves = new Stack<Move>();

	private int depth = -1;
	private Move bestMove = null;

	Training trans = null;
	public Move getBestMove(ChessAction action, ChessBoard board, int depth) {
		this.depth = depth;
		trans = new Training(action);
		bestMove = null;
		float startTime = System.nanoTime();
		alphaBetaMax(board, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);

		float timeHandle = (System.nanoTime() - startTime) / 1000000;
		System.out.println(bestMove.toStrings());
		System.out.println("Move time handle: " + timeHandle / 1000 + " s");

		return bestMove;
	}

	private int alphaBetaMax(ChessBoard board, int alpha, int beta, int depthLeft) {
		Evaluate eval = new Evaluate(board);
		// System.out.println("Max");
		if (depthLeft == 0) {
			return eval.sumEval();
		}

		List<Move> allMove = trans.dicision();

		for (Move n : allMove) {
			execute(board, n);
			// System.out.println("Move excute: " + n.getMove().toStrings());
			// System.out.println("--------Board excute-----");
			// board.printBoard();
			int score = alphaBetaMin(board, alpha, beta, depthLeft - 1);
			undo(board);
			// System.out.println((undo() == null));
			// System.out.println("--------Board undo-----");
			// board.printBoard();
			n.setScore(score);

			if (depthLeft == depth) {
				if (bestMove != null) {
					if (n.getScore() > bestMove.getScore()) {
						bestMove = n;
					}
				} else {
					bestMove = n;
				}
			}
			if (score >= beta) {
				return beta;
			}
			if (score > alpha) {
				alpha = score;
			}
		}
		return alpha;
	}

	private int alphaBetaMin(ChessBoard board, int alpha, int beta, int depthLeft) {
		Evaluate eval = new Evaluate(board);
		// System.out.println("Min");
		if (depthLeft == 0) {
			return eval.sumEval();
		}

		List<Move> allMove = trans.dicision();

		for (Move n : allMove) {
			execute(board, n);
			// System.out.println("Move excute: " + n.getMove().toStrings());
			// System.out.println("--------Board excute-----");
			// board.printBoard();
			int score = alphaBetaMax(board, alpha, beta, depthLeft - 1);
			undo(board);
			// System.out.println("--------Board undo-----");
			// board.printBoard();
			n.setScore(score);

			if (score <= alpha) {
				return alpha;
			}
			if (score < beta) {
				beta = score;
			}
		}
		return beta;
	}

	public boolean execute(ChessBoard board, Move move) {
		if (board.makeMove(move)) {
			// System.out.println("Action " + move.toStrings());
			moves.push(move);
			board.setPlayer(Player.changePlayer(board.getPlayer()));
			board.setMeasurements(board.getPlayer(), board.pieceBoard, move);
			return true;
		}
		return false;
	}

	public Move restore(ChessBoard board) {
		Move move = pop();
		if (move != null) {
			// System.out.println("ko null");
			board.unMakeMove(move, peek());
			undo.push(move);
			board.setPlayer(Player.changePlayer(board.getPlayer()));
			board.setMeasurements(board.getPlayer(), board.pieceBoard, peek());
			return move;
		}
		return null;
	}

	Stack<Move> undo = new Stack<Move>();

	public Move undo(ChessBoard board) {
		return restore(board);

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

}
