package demo;

import action.Move;
import controller.BoardController;
import core.ChessBoard;
import core.Location;
import gui.Board;

public class testGoal {
	public static void main(String[] args) {
		Board view = new Board(600);
		ChessBoard model = new ChessBoard();
		BoardController control = new BoardController(model, view);

		Move move1 = new Move(new Location(7, 1), new Location(5, 0), model.getPieceAt(new Location(7, 1)), null);
		Move move2 = new Move(new Location(0, 1), new Location(2, 0), model.getPieceAt(new Location(0, 1)), null);
		Move move3 = new Move(new Location(5, 0), new Location(7, 1), model.getPieceAt(new Location(5, 0)), null);
		Move move4 = new Move(new Location(2, 0), new Location(0, 1), model.getPieceAt(new Location(2, 0)), null);

		while (true) {
			try {
				Thread.sleep(500);
				control.click(move1.getPieceFrom());
				Thread.sleep(500);
				long x = System.currentTimeMillis();
				control.move(move1.getTo());
				System.out.println("1111111111-----------"+(System.currentTimeMillis() - x )+ "s");

				Thread.sleep(500);
				control.click(move2.getPieceFrom());
				Thread.sleep(500);
				long x2 = System.currentTimeMillis();
				control.move(move2.getTo());
				System.out.println("2222222222222222-----------"+(System.currentTimeMillis() - x2 )+ "s");
				
				Thread.sleep(500);
				control.click(move3.getPieceFrom());
				Thread.sleep(500);
				control.move(move3.getTo());

				Thread.sleep(500);
				control.click(move4.getPieceFrom());
				Thread.sleep(500);
				control.move(move4.getTo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
