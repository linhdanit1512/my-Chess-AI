package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import action.ChessAction;
import action.Move;
import chess.Piece;
import core.ChessBoard;
import core.ChessGoalTest;
import core.Location;
import core.Player;
import gui.Board;

public class BoardController implements MouseListener {
	ChessBoard model;
	Board view;
	ChessAction action;

	public BoardController(ChessBoard model, Board view) {
		super();
		this.model = model;
		this.view = view;
		init();
	}

	public static void main(String[] args) {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (Exception e) {
		}
		ChessBoard model = new ChessBoard();
		Board view = new Board();
		new BoardController(model, view);
	}

	void init() {
		action = new ChessAction(model);
		@SuppressWarnings("unused")
		ChessGoalTest goaltest = new ChessGoalTest(model);
		view.pnBoard.addMouseListener(this);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (model.pieceBoard[i][j] != null) {
					view.btnBoard[i][j].setIcon(new ImageIcon("image\\" + model.pieceBoard[i][j].getLinkImg()));
					view.btnBoard[i][j].addMouseListener(this);
				}
			}
		}
	}

	private List<Piece> listPiece = new ArrayList<>();
	Map<Piece, List<Location>> mapResult;

	public Map<Piece, List<Location>> getAllRule() {
		if (mapResult == null || mapResult.size() == 0) {
			System.out.println("aaaaaaaaaaaaaaaaaaa");
			mapResult = new HashMap<Piece, List<Location>>();
			if (listPiece == null || listPiece.size() == 0) {
				listPiece = new ArrayList<>();
				if (model.getPlayer() == Player.PLAYER || model.getPlayer() == Player.COMPUTER2) {
					listPiece = model.listWhiteAlliance;
				} else if (model.getPlayer() == Player.PLAYER2 || model.getPlayer() == Player.COMPUTER) {
					listPiece = model.listBlackAlliance;
				}
			}

			for (Piece piece : listPiece) {
				if (piece != null) {
					mapResult.put(piece, piece.getRule().getRealLocationCanMove());
				}
			}
		}
		return mapResult;
	}

	Point start, stop;
	Piece selectPiece;
	Location destination;
	

	/**
	 * Mouse Listener
	 */
	int count = 0;

	@Override
	public void mouseClicked(MouseEvent e) {
		if (count == 0) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (e.getSource() == view.btnBoard[i][j]) {
						if (view.btnBoard[i][j] != null) {
							selectPiece = model.getPieceAt(new Location(i, j));
							view.btnBoard[i][j].setBorderPainted(true);
							view.btnBoard[i][j].setBorder(BorderFactory.createEtchedBorder(5, Color.BLUE, Color.RED));

							for (Location l : getAllRule().get(selectPiece)) {
								view.btnBoard[l.getX()][l.getY()].setBorderPainted(true);
								view.btnBoard[l.getX()][l.getY()]
										.setBorder(BorderFactory.createEtchedBorder(2, Color.BLUE, Color.GREEN));
							}
							System.out.println(selectPiece);
							count = 1;
							return;
						}
					}
				}
			}
			return;
		} else if (count == 1) {
			if (selectPiece != null) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (e.getSource() == view.btnBoard[i][j]) {
							view.btnBoard[i][j].setBorderPainted(true);
							view.btnBoard[i][j].setBorder(BorderFactory.createEtchedBorder(5, Color.BLUE, Color.RED));
							view.btnBoard[i][j].setIcon(new ImageIcon("image\\" + selectPiece.getLinkImg()));
							view.btnBoard[selectPiece.getLocation().getX()][selectPiece.getLocation().getY()]
									.setIcon(null);
							action.put(new Move(selectPiece, new Location(i,j)));
							view.pnBoard.repaint();
							view.repaint();
							
							count = 0;
							return;
						}
					}
				}
			}
		}
	}

	/**
	 * hien chi dan cac nuoc co the di khi di chuyen chuot qua quan co
	 * 
	 */

	List<JButton> btnEntered = new ArrayList<JButton>();

	@Override
	public void mouseEntered(MouseEvent e) {
		if (listPiece == null || listPiece.size() == 0) {
			listPiece = new ArrayList<>();
			if (model.getPlayer() == Player.PLAYER || model.getPlayer() == Player.COMPUTER2) {
				listPiece = model.listWhiteAlliance;
			} else if (model.getPlayer() == Player.PLAYER2 || model.getPlayer() == Player.COMPUTER) {
				listPiece = model.listBlackAlliance;
			}
		}

		for (Piece piece : listPiece) {

			if (e.getSource() == view.btnBoard[piece.getLocation().getX()][piece.getLocation().getY()]) {
				btnEntered.add(view.btnBoard[piece.getLocation().getX()][piece.getLocation().getY()]);
				view.btnBoard[piece.getLocation().getX()][piece.getLocation().getY()].setBorderPainted(true);
				view.btnBoard[piece.getLocation().getX()][piece.getLocation().getY()]
						.setBorder(BorderFactory.createEtchedBorder(3, Color.BLUE, Color.RED));

				List<Location> rule = getAllRule().get(piece);
				if (rule != null) {
					System.out.println(piece.getLinkImg() + "\n" + rule);
					for (Location l : rule) {
						btnEntered.add(view.btnBoard[l.getX()][l.getY()]);
						view.btnBoard[l.getX()][l.getY()].setBorderPainted(true);
						view.btnBoard[l.getX()][l.getY()]
								.setBorder(BorderFactory.createEtchedBorder(2, Color.BLUE, Color.GREEN));
					}
				}
			}
		}
	}

	/**
	 * tro lai thuong khi chuot ra khoi quan co
	 * 
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if (btnEntered != null && !btnEntered.isEmpty()) {
			for (JButton btn : btnEntered) {
				btn.setBorderPainted(false);
				btn.setBorder(null);
			}
		}
		btnEntered.clear();
	}

	/**
	 * lay quan co khi nhan chuot
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Khi tha chuot ra
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
