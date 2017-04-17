package controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import action.ChessAction;
import chess.Piece;
import core.ChessBoard;
import core.ChessGoalTest;
import core.Location;
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
		ChessBoard model = new ChessBoard();
		Board view = new Board(model);
		new BoardController(model, view);
	}

	void init() {
		action = new ChessAction(model);
		ChessGoalTest goaltest = new ChessGoalTest(model);
		lbl = new JLabel();
		view.add(lbl);
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				view.btnBoard[i][j].addMouseListener(this);
				view.btnBoard[i][j].addMouseMotionListener(this);
			}
		}
	}

	Point start, stop;
	Piece selectPiece;
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
		// List<Piece> listPiece = new ArrayList<>();
		// if (model.getPlayer() == Player.PLAYER || model.getPlayer() ==
		// Player.COMPUTER2) {
		// listPiece = model.listWhiteAlliance;
		// } else if (model.getPlayer() == Player.PLAYER2 || model.getPlayer()
		// == Player.COMPUTER) {
		// listPiece = model.listBlackAlliance;
		// }
		// for (Piece piece : listPiece) {
		// if (e.getSource() ==
		// view.btnBoard[piece.getLocation().getX()][piece.getLocation().getY()])
		// {
		// List<Location> rule = piece.getRule().getRealLocationCanMove();
		// if (rule != null) {
		// System.out.println(rule);
		// for (Location l : rule) {
		// view.btnBoard[l.getX()][l.getY()]
		// .setBorder(BorderFactory.createEtchedBorder(2, Color.BLUE,
		// Color.GREEN));
		// }view.pnBoard. validate();
		// }view.pnBoard.validate();
		// }
		// view.pnBoard.validate();
		// }
	}

	/**
	 * tro lai thuong khi chuot ra khoi quan co
	 * 
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// List<Piece> listPiece = new ArrayList<>();
		// if (model.getPlayer() == Player.PLAYER || model.getPlayer() ==
		// Player.COMPUTER2) {
		// listPiece = model.listWhiteAlliance;
		// } else if (model.getPlayer() == Player.PLAYER2 || model.getPlayer()
		// == Player.COMPUTER) {
		// listPiece = model.listBlackAlliance;
		// }
		// for (Piece piece : listPiece) {
		// if (e.getSource() ==
		// view.btnBoard[piece.getLocation().getX()][piece.getLocation().getY()])
		// {
		// List<Location> rule = piece.getRule().getRealLocationCanMove();
		// if (rule != null) {
		// for (Location l : rule) {
		// view.btnBoard[l.getX()][l.getY()].setBorder(null);
		// }view.pnBoard.validate();
		// }view.pnBoard.validate();
		// }
		// view.pnBoard.validate();
		// }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int wBtn = view.pnBoard.getWidth() / 8;
		int hBtn = view.pnBoard.getHeight() / 8;
		start = view.getMousePosition();
		System.out.println("start →"+start);
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {

				if (start.getX() >= wBtn * i && start.getX() < wBtn * (i + 1) && start.getY() >= hBtn * j
						&& start.getY() < hBtn * (j + 1)) {
					selectPiece = model.getPieceAt(new Location(i, j));
				}

			}
		}
		System.out.println(selectPiece);
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
		System.out.println("Stop "+stop.getX() + "-" + stop.getY());
		if (selectPiece != null) {
			int x = e.getX() - 520 / 8;
			int y = e.getY() - 250 / 8;
			int w = e.getY() + 520 / 8;
			int h = e.getX() + 520 / 8;
			lbl.setIcon(new ImageIcon(selectPiece.getLinkImg()));
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
