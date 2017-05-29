package game;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import controller.BoardController;
import core.ChessBoard;
import gui.Board;

public class Player_Computer implements Runnable {
	ChessBoard model;
	Board view;
	BoardController control;

	public Player_Computer() {
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
		while(true){
			if(!control.isEndGame()){
				
			}
		}
	}

}
