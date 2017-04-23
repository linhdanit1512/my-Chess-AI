package demo;

import action.ChessAction;
import action.Move;
import chess.ColorPiece;
import core.ChessBoard;
import core.Location;

public class testNhapThanh {
	public static void main(String[] args) {

		ChessBoard board = new ChessBoard();
		ChessAction action = new ChessAction(board);
		System.out.println(action.push(new Move(board.pieceBoard[6][4], new Location(5, 4))));
		board.printBoard();

		board.setPlayer(ColorPiece.BLACK);
		System.out.println(action.push(new Move(board.pieceBoard[1][4], new Location(2, 4))));
		board.printBoard();

		board.setPlayer(ColorPiece.WHITE);
		System.out.println(action.push(new Move(board.pieceBoard[7][5], new Location(5, 3))));
		board.printBoard();
		
		board.setPlayer(ColorPiece.BLACK);
		System.out.println(action.push(new Move(board.pieceBoard[1][3], new Location(3, 3))));
		board.printBoard();
		
		board.setPlayer(ColorPiece.WHITE);
		System.out.println(action.push(new Move(board.pieceBoard[7][6], new Location(5, 5))));
		board.printBoard();
		
		board.setPlayer(ColorPiece.BLACK);
		System.out.println(action.push(new Move(board.pieceBoard[0][5], new Location(2, 3))));
		board.printBoard();
		
		board.setPlayer(ColorPiece.WHITE);
		System.out.println(action.push(new Move(board.pieceBoard[7][4], new Location(7, 6))));
		board.printBoard();
		
	}
}