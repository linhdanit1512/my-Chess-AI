package game;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import controller.BoardController;
import core.ChessBoard;
import gui.Board;

public class Player_Player {
	public Player_Player() {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (Exception e) {
		}
		ChessBoard model = new ChessBoard();
		Board view = new Board(570);
		new BoardController(model, view, BoardController.PLAYER_PLAYER).init();
	}
}
