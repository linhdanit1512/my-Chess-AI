package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

import action.ChessAction;
import action.Move;
import ai.AlphaBeta;
import chess.Alliance;
import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import core.ChessGoalState;
import core.Location;
import core.Player;
import core.Sound;
import gui.Board;
import gui.PromotionMessage;
import rule.QueenRule;

public class BoardController implements ActionListener {
	public static final int PLAYER_PLAYER = 1000;
	public static final int PLAYER_COMPUTER = 2000;
	public static final int COMPUTER_COMPUTER = 3000;
	private ChessBoard model;
	private Board view;
	private ChessAction action;
	RecordController record;
	AlphaBeta search;
	int depth = 5;
	int type;

	public BoardController(ChessBoard model, Board view, int type) {
		super();
		this.model = model;
		this.view = view;
		this.type = type;
		action = new ChessAction(model, view);
		this.record = new RecordController(this);
		this.search = new AlphaBeta();
		init();
	}

	void init() {
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

	Location to, from;
	Sound sound = new Sound();
	boolean isEndGame;
	private Piece pricePromotion;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isEndGame) {
			if (type == PLAYER_COMPUTER) {
				player_computer(e);
			} else if (type == PLAYER_PLAYER) {
				player_player(e);
			}
		}
	}

	public void player_player(ActionEvent e) {
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

	public void player_computer(ActionEvent e) {
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
					Move moveCom = search.alphabeta(model, model.getPlayer(), depth);
					click(moveCom.getPieceFrom());
					move(moveCom.getTo());
					return;
				}
			}
		}
	}

	public void computer_computer() {
		while (true) {
			try {
				Move m1 = search.alphabeta(model, model.getPlayer(), 5);
				click(m1.getPieceFrom());
				Thread.sleep(500);
				move(m1.getTo());

				Move m2 = search.alphabeta(model, model.getPlayer(), 5);
				click(m2.getPieceFrom());
				Thread.sleep(500);
				move(m2.getTo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void click(Piece piece) {
		if (piece != null) {
			if (piece.getAlliance() == model.getPlayer()) {
				view.resetBorderIgnore(piece.getLocation());
				from = piece.getLocation();
				if (to != null) {
					view.btnBoard[to.getX()][to.getY()].setBorder(null);
					to = null;
				}
				view.btnBoard[from.getX()][from.getY()].setBorderPainted(true);
				view.btnBoard[from.getX()][from.getY()]
						.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.CYAN, Color.RED));
				if (model.getPieceAt(from) != null)
					for (Location l : model.getPieceAt(from).getRule().getRealLocationCanMove()) {
						view.btnBoard[l.getX()][l.getY()].setBorderPainted(true);
						view.btnBoard[l.getX()][l.getY()]
								.setBorder(BorderFactory.createEtchedBorder(2, Color.BLUE, Color.GREEN));
					}
				return;
			}
		}
	}

	private boolean check = false;

	public void move(Location to) {
		if (from == null)
			return;
		if (from.equals(to)) {
			to = null;
			return;
		}
		System.out.println("check: "+ check);
		Move move = new Move(from, to, model.getPieceAt(from), model.getPieceAt(to));
		PromotionMessage pro = new PromotionMessage(this, move);
		if (move.isPromotion()) {
			if (check == false) {
				pro.setVisible(true);
				view.setEnabled(false);
			} else {
				view.setEnabled(true);
				if (pricePromotion != null)
					move.setPiecePromotion(pricePromotion);
				else{
					move.setPiecePromotion(promotionPiece(move));
				}
				Move m = action.execute(move);
				if (m != null) {
					System.out.println(model.getPremove());
					from = null;
					int check = check();
					if (check == ChessGoalState.CHECK) {
						move.setChess(true);
					} else if (check == ChessGoalState.CHECKMATE) {
						move.setChessmate(true);
					} else if (check == ChessGoalState.DRAW) {
						move.setDraw(true);
					}
					view.validate();
					model.printBoard();
				} else {
					to = null;
					return;
				}
			}
		} else {
			Move m = action.execute(move);
			if (m != null) {
				System.out.println(model.getPremove());
				from = null;
				int check = check();
				if (check == ChessGoalState.CHECK) {
					move.setChess(true);
				} else if (check == ChessGoalState.CHECKMATE) {
					move.setChessmate(true);
				} else if (check == ChessGoalState.DRAW) {
					move.setDraw(true);
				}
				view.validate();
				model.printBoard();
			} else {
				to = null;
				return;
			}
		}
	}

	private Piece promotionPiece(Move move) {
		Piece p = new Piece(move.getTo(), PieceType.QUEEN, 'Q', new QueenRule(model, move.getTo()), Alliance.WHITE, 90,
				"whitequeen.png");
		p.setMove(0);
		return p;
	}

	public int check() {
		// kiá»ƒm tra vua cÃ³ bá»‹ chiáº¿u hay ko
		if (ChessGoalState.checkmate(model)) {
			Piece king = model.getKing().get(getPlayer());
			// láº¥y tá»�a Ä‘á»™ cá»§a vua
			Location l = king.getLocation();
			List<Piece> tmp = new ArrayList<>();
			List<Piece> enemy = king.getRule().getEnemyControlAtLocation(l, king.getAlliance());
			if (enemy != null && !enemy.isEmpty())
				tmp.addAll(enemy);
			tmp.add(king);

			for (Piece p : tmp) {
				view.btnBoard[p.getLocation().getX()][p.getLocation().getY()]
						.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.RED, Color.ORANGE));
			}

			// kiem tra chieu bi
			if (ChessGoalState.checkWin(model)) {
				isEndGame = true;
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
				return ChessGoalState.CHECKMATE;
			}
			sound.playSound("sound\\check.wav");
			return ChessGoalState.CHECK;

		} else if (ChessGoalState.checkDraw(model, action)) {
			isEndGame = true;
			sound.playSound("sound\\draw.wav");
			JOptionPane.showMessageDialog(null, "You draw with computer", "End game message",
					JOptionPane.INFORMATION_MESSAGE, null);
			return ChessGoalState.DRAW;
		}
		return 0;
	}

	public int getPlayer() {
		return model.getPlayer();
	}

	public ChessBoard getModel() {
		return model;
	}

	public Board getView() {
		return view;
	}

	public ChessAction getAction() {
		return action;
	}

	public RecordController getRecord() {
		return record;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Location getTo() {
		return to;
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public Location getFrom() {
		return from;
	}

	public void setFrom(Location from) {
		this.from = from;
	}

	public Piece getPricePromotion() {
		return pricePromotion;
	}

	public void setPricePromotion(Piece pricePromotion) {
		this.pricePromotion = pricePromotion;
	}

	public boolean isEndGame() {
		return isEndGame;
	}

}
