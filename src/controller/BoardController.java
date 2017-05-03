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
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import action.ChessAction;
import action.Move;
import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import core.ChessGoalTest;
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
		action = new ChessAction(model);
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
		Board view = new Board(700);
		new BoardController(model, view);
	}

	void init() {
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
							&& model.getPieceAt(new Location(i, j)).getAlliance() == model.getPlayer())) {

						if (view.btnBoard[i][j] != null && model.pieceBoard[i][j] != null) {
							if (model.pieceBoard[i][j].getAlliance() == model.getPlayer()) {
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
		if (piece != null) {
			view.resetBorderIgnore(piece.getLocation());
			if (piece.getAlliance() == model.getPlayer()) {
				from = piece.getLocation();
				if (to != null) {
					view.btnBoard[to.getX()][to.getY()].setBorder(null);
					to = null;
				}
				view.btnBoard[from.getX()][from.getY()].setBorderPainted(true);
				view.btnBoard[from.getX()][from.getY()]
						.setBorder(BorderFactory.createEtchedBorder(5, Color.BLUE, Color.RED));
				if (getAllRule().get(model.getPieceAt(from)) != null)
					for (Location l : getAllRule().get(model.getPieceAt(from))) {
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
			to = null;
			return;
		}

		Move move = new Move(from, to, model.getPieceAt(from), model.getPieceAt(to));

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
			sound.playSound("sound\\Capture.WAV");
		}
		// them vao stack nuoc di nay
		if (action.push(move)) {
			if (move.getPrisoner() == null)
				sound.playSound("sound\\Move.WAV");
			else
				sound.playSound("sound\\eat.wav");

			// reset lại border cho các ô của bàn cờ
			view.resetBorderIgnore(to);
			view.btnBoard[to.getX()][to.getY()]
					.setIcon(new ImageIcon("image\\" + action.peek().getPieceFrom().getLinkImg()));

			// loại icon của ô from vừa đi
			view.btnBoard[from.getX()][from.getY()].setIcon(null);
			String players = player();

			// thêm 1 bước đi vào trong kì phổ
			record.view.pnRecord.add(new Record(action.getCount(), players, move));

			// set lại lượt chơi cho người chơi và máy
			if (model.getPlayer() == Player.PLAYER || model.getPlayer() == Player.COMPUTER2) {
				model.setPlayer(Player.COMPUTER);
			} else if (model.getPlayer() == Player.PLAYER2 || model.getPlayer() == Player.COMPUTER)
				model.setPlayer(Player.PLAYER);
			System.out.println("System.out.println(action.push(new Move(new Location(" + from.getX() + "," + from.getY()
					+ "), new Location(" + to.getX() + ", " + to.getY() + "))));");
			setPlayer();

			// thông báo cập nhật tới các Observer
			model.setMeasurements(model.getPlayer(), model.pieceBoard);

			// xóa thông tin map.
			clearMapRule();
			from = null;
			check();
			view.validate();
//			for(Map.Entry<Piece, List<Location>> map : getAllRule().entrySet()){
//				System.out.print(map.getKey().getLinkImg());
//				for(Location l : map.getValue()){
//					System.out.print(l.toString()+"\t");
//				}
//				System.out.println();
//			}
			model.printBoard();

		} else {
			to = null;
			return;
		}
	}

	public boolean isCastling(Move move) {
		if (move != null) {
			if (move.getPieceFrom() != null && move.getPieceFrom().getMove() > 0)
				return false;
			if (move.getPieceFrom().getType().equals(PieceType.KING)) {
				if (move.getPieceFrom().getRule().getRule() != null) {
					Castling castling = (Castling) move.getPieceFrom().getRule().getRule();
					List<Location> list = castling.castling(move.getPieceFrom());
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

	public int check() {
		// kiểm tra vua có bị chiếu hay ko
		if (ChessGoalTest.checkmate(model)) {
			Piece king = model.getKing().get(getPlayer());
			// lấy tọa độ của vua
			Location l = king.getLocation();
			List<Piece> tmp = king.getRule().getEnemyControlAtLocation(l, king.getAlliance());
			tmp.add(king);

			for (Piece p : tmp) {
				view.btnBoard[p.getLocation().getX()][p.getLocation().getY()]
						.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.RED, Color.ORANGE));
			}

			// kiem tra chieu bi
			if (ChessGoalTest.checkWin(model)) {
				String message = "";
				if (model.getPlayer() == Player.PLAYER) {
					sound.playSound("sound\\lost.wav");
					message = "You lost";
				} else {
					sound.playSound("sound\\win.wav");
					message = "You  win";
				}
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						view.btnBoard[i][j].removeActionListener(this);
					}
				}
				view.pnRecord.btnRedo.removeActionListener(record);
				view.pnRecord.btnUndo.removeActionListener(record);
				JOptionPane.showMessageDialog(null, message, "End game message", JOptionPane.INFORMATION_MESSAGE, null);
				return 1;
			}
			 sound.playSound("sound\\check.wav");
			return 3;

		} else if (ChessGoalTest.checkDraw(model, action)) {
			sound.playSound("sound\\draw.wav");
			JOptionPane.showMessageDialog(null, "You draw with computer", "End game message",
					JOptionPane.INFORMATION_MESSAGE, null);
			return 2;
		}
		return 0;
	}

	public String player() {
		String players = "";
		if (model.getPlayer() == Player.COMPUTER) {
			players = "COMPUTER:";
		} else if (model.getPlayer() == Player.PLAYER) {
			players = "YOU:";
		}
		return players;
	}

	public int getPlayer() {
		return model.getPlayer();
	}

	public void setPlayer() {
		this.player = model.getPlayer();
	}

}
