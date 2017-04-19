package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import action.ChessAction;
import chess.Piece;
import core.ChessBoard;
import core.ChessGoalTest;
import core.Location;
import core.Player;
import gui.Board;

public class BoardController implements MouseListener, MouseMotionListener {
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
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		lbl = new JLabel();
		view.add(lbl);
		view.pnBoard.addMouseListener(this);
		view.pnBoard.addMouseMotionListener(this);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (model.pieceBoard[i][j] != null) {
					view.btnBoard[i][j].setIcon(new ImageIcon(model.pieceBoard[i][j].getLinkImg()));
					view.btnBoard[i][j].addMouseListener(this);
					view.btnBoard[i][j].addMouseMotionListener(this);
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
	JLabel lbl;

	/**
	 * Mouse Listener
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * hien chi dan cac nuoc co the di khi di chuyen chuot qua quan co
	 * 
	 */

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
				view.btnBoard[piece.getLocation().getX()][piece.getLocation().getY()].setBorderPainted(true);
				view.btnBoard[piece.getLocation().getX()][piece.getLocation().getY()]
						.setBorder(BorderFactory.createEtchedBorder(3, Color.BLUE, Color.RED));

				List<Location> rule = getAllRule().get(piece);
				if (rule != null) {
					System.out.println(piece.getLinkImg() + "\n" + rule);
					for (Location l : rule) {
						view.btnBoard[l.getX()][l.getY()].setBorderPainted(true);
						view.btnBoard[l.getX()][l.getY()]
								.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.GREEN));
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
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				view.btnBoard[i][j].setBorder(null);
			}
		}
	}

	/**
	 * lay quan co khi nhan chuot
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (e.getSource() == view.btnBoard[i][j]) {
					if (view.btnBoard[i][j] != null)
						selectPiece = model.getPieceAt(new Location(i, j));
				}
			}
			System.out.println(selectPiece);
		}
	}

	/**
	 * Khi tha chuot ra
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// Point point = e.getPoint();
		// if (selectPiece != null) {
		// for (int i = 0; i < 8; i++) {
		// for (int j = 0; j < 8; j++) {
		// Rectangle r = view.btnBoard[i][j].getBounds();
		// if (r.contains(point)) {
		// view.btnBoard[i][j].setIcon(new ImageIcon(selectPiece.getLinkImg()));
		// view.btnBoard[selectPiece.getLocation().getX()][selectPiece.getLocation().getY()].setIcon(null);
		// model.setPieceAtLocation(new Location(i, j), selectPiece);
		// }view.pnBoard.validate();
		// }view.pnBoard.validate();
		// }
		// view.pnBoard.validate();
		// }

	}

	/**
	 * Mouse motion listener
	 */
	/**
	 * khi nhan quan co va keo re no
	 */

	@Override
	public void mouseDragged(MouseEvent e) {

		stop = e.getPoint();
		System.out.println("Stop " + stop.getX() + "-" + stop.getY());
		if (selectPiece != null) {
			int x = e.getX() - 520 / 8;
			int y = e.getY() - 250 / 8;
			int w = e.getY() + 520 / 8;
			int h = e.getX() + 520 / 8;
			lbl.setIcon(new ImageIcon(selectPiece.getLinkImg()));
			System.out.println("lbl" + lbl.getIcon().toString());
			lbl.setBounds(x, y, w, h);
			view.pnBoard.repaint();
			view.pnBoard.validate();
		}
	}

	/**
	 * khi con tro di chuyen vao trong component
	 */
	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
