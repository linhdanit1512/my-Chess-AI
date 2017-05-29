package game;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import action.Move;
import ai.AlphaBeta;
import controller.BoardController;
import core.ChessBoard;
import gui.Board;

public class Computer_Computer implements Runnable {
	ChessBoard model;
	Board view;
	BoardController control;

	public Computer_Computer() {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (Exception e) {
		}
		model = new ChessBoard();
		view = new Board(600);
		control = new BoardController(model, view);
	}

	@Override
	public void run() {
		AlphaBeta search = new AlphaBeta();
		while (true) {
			try {
				Move m1 = search.alphabeta(model, model.getPlayer(), 5);
				control.click(m1.getPieceFrom());
				Thread.sleep(500);
				control.move(m1.getTo());
				
				Move m2 = search.alphabeta(model, model.getPlayer(), 5);
				control.click(m2.getPieceFrom());
				Thread.sleep(500);
				control.move(m2.getTo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
