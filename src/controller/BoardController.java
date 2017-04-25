package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import action.ChessAction;
import action.Move;
import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import core.Location;
import core.Player;
import core.Record;
import core.Sound;
import gui.Board;
import rule.Castling;

public class BoardController implements MouseListener, ActionListener {
	ChessBoard model;
	Board view;
	ChessAction action;
	int player;
	RecordController record;

	public BoardController(ChessBoard model, Board view) {
		super();
		this.model = model;
		this.view = view;
		this.record = new RecordController(this);
		player = model.getPlayer();
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
		view.pnBoard.addMouseListener(this);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (model.pieceBoard[i][j] != null) {
					view.btnBoard[i][j].setIcon(new ImageIcon("image\\" + model.pieceBoard[i][j].getLinkImg()));
				}
				// view.btnBoard[i][j].addMouseListener(this);
				view.btnBoard[i][j].addActionListener(this);
			}
		}
	}

	private List<Piece> listPiece = new ArrayList<>();
	Map<Piece, List<Location>> mapResult = new HashMap<Piece, List<Location>>();

	public Map<Piece, List<Location>> getAllRule() {
		if (mapResult == null) {
			mapResult = new HashMap<Piece, List<Location>>();
		}
		if (mapResult.size() == 0) {
			if (listPiece == null) {
				listPiece = new ArrayList<>();
			}
			if (listPiece.size() == 0) {
				if (model.getPlayer() == Player.PLAYER || model.getPlayer() == Player.COMPUTER2) {
					listPiece = model.listWhiteAlliance;
				} else if (model.getPlayer() == Player.PLAYER2 || model.getPlayer() == Player.COMPUTER) {
					listPiece = model.listBlackAlliance;
				}
			}

			for (Piece piece : listPiece) {
				if (piece != null) {
					if (piece.getRule().getRealLocationCanMove() != null
							&& !piece.getRule().getRealLocationCanMove().isEmpty())
						mapResult.put(piece, piece.getRule().getRealLocationCanMove());
				}
			}
		}
		return mapResult;
	}

	public void clearMapRule() {
		listPiece = new ArrayList<>();
		mapResult = new HashMap<Piece, List<Location>>();
	}

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

	Location to, from;
	List<Location> listTMPMove = new ArrayList<Location>();
	Sound sound = new Sound();
	int count = 0;

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (view.btnBoard[i][j] == e.getSource()) {
					/**
					 * chon quan co lan dau hoac chon quan co khac
					 */
					if (from == null || (model.getPieceAt(new Location(i, j)) != null
							&& model.getPieceAt(new Location(i, j)).getColor() == model.getPlayer())) {

						if (view.btnBoard[i][j] != null && model.pieceBoard[i][j] != null) {
							if (model.pieceBoard[i][j].getColor() == model.getPlayer()) {
								Piece piece = model.pieceBoard[i][j];
								click(piece);
								return;
							}
						}
					}

					if (from != null && to == null) {
						to = new Location(i, j);
						move(to);
					}
				}
			}
		}
	}

	public void click(Piece piece) {
		for (Location l : listTMPMove) {
			view.btnBoard[l.getX()][l.getY()].setBorder(null);
			view.btnBoard[l.getX()][l.getY()].setBorderPainted(false);
		}
		listTMPMove.clear();
		if (piece != null) {
			if (piece.getColor() == model.getPlayer()) {
				from = piece.getLocation();
				if (to != null) {
					view.btnBoard[to.getX()][to.getY()].setBorder(null);
					to = null;
				}
				listTMPMove.add(from);
				view.btnBoard[from.getX()][from.getY()].setBorderPainted(true);
				view.btnBoard[from.getX()][from.getY()]
						.setBorder(BorderFactory.createEtchedBorder(5, Color.BLUE, Color.RED));
				if (getAllRule().get(model.getPieceAt(from)) != null)
					for (Location l : getAllRule().get(model.getPieceAt(from))) {
						listTMPMove.add(l);
						view.btnBoard[l.getX()][l.getY()].setBorderPainted(true);
						view.btnBoard[l.getX()][l.getY()]
								.setBorder(BorderFactory.createEtchedBorder(2, Color.BLUE, Color.GREEN));
					}
				return;
			}
		}
	}

	public void move(Location to) {
		if (from.equals(to)) {

			for (Location l : listTMPMove) {
				view.btnBoard[l.getX()][l.getY()].setBorder(null);
				view.btnBoard[l.getX()][l.getY()].setBorderPainted(false);
			}
			listTMPMove.clear();
			from = null;
			to = null;
			return;
		}
		Move move = new Move(model.getPieceAt(from), to);

		if (isCastling(move)) {
			int x = move.getTo().getX();
			int y = move.getTo().getY();
			if (y == 2) {
				view.btnBoard[x][3].setIcon(new ImageIcon("image\\" + model.pieceBoard[x][0].getLinkImg()));
				view.btnBoard[x][0].setIcon(null);
			}

			if (y == 6) {
				view.btnBoard[x][5].setIcon(new ImageIcon("image\\" + model.pieceBoard[x][7].getLinkImg()));
				view.btnBoard[x][7].setIcon(null);
			}
			view.btnBoard[from.getX()][from.getY()].setIcon(null);
		}
		// them vao stack nuoc di nay
		if (action.push(move)) {
			sound.playSound("sound\\Move.WAV");

			for (Location l : listTMPMove) {
				view.btnBoard[l.getX()][l.getY()].setBorder(null);
				view.btnBoard[l.getX()][l.getY()].setBorderPainted(false);
			}
			view.btnBoard[to.getX()][to.getY()].setBorderPainted(true);
			view.btnBoard[to.getX()][to.getY()].setBorder(BorderFactory.createEtchedBorder(5, Color.BLUE, Color.RED));
			view.btnBoard[to.getX()][to.getY()]
					.setIcon(new ImageIcon("image\\" + action.peek().getFrom().getLinkImg()));

			// view.btnBoard[from.getX()][from.getY()].setBorderPainted(false);
			view.btnBoard[from.getX()][from.getY()].setIcon(null);

			String players = "";
			if (player == Player.COMPUTER) {
				players = "COMPUTER:";
			} else if (player == Player.PLAYER) {
				players = "YOU:";
			}

			record.view.pnRecord.add(new Record(action.getCount(), players, move));

			if (model.getPlayer() == Player.PLAYER || model.getPlayer() == Player.COMPUTER2)
				model.setPlayer(Player.PLAYER2);
			else if (model.getPlayer() == Player.PLAYER2 || model.getPlayer() == Player.COMPUTER)
				model.setPlayer(Player.PLAYER);
			System.out.println("System.out.println(action.push(new Move(board.pieceBoard[" + from.getX() + "]["
					+ from.getY() + "], new Location(" + to.getX() + ", " + to.getY() + "))));");
			model.setMeasurements(model.getPlayer(), model.pieceBoard);
			listTMPMove.clear();
			clearMapRule();
			from = null;
			view.validate();
			model.printBoard();
		} else {
			to = null;
			return;
		}
	}

	public boolean isCastling(Move move) {
		if (move != null) {
			if (move.getFrom() != null && move.getFrom().getMove() > 0)
				return false;
			if (move.getFrom().getType().equals(PieceType.KING)) {
				if (move.getFrom().getRule().getRule() != null) {
					Castling castling = (Castling) move.getFrom().getRule().getRule();
					List<Location> list = castling.castling(move.getFrom());
					if (list != null && !list.isEmpty()) {
						if (list.contains(move.getTo())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
