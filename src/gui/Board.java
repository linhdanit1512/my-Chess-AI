package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import action.ChessAction;
import action.Move;
import controller.BoardController;
import core.ChessBoard;
import core.Location;
import core.Player;
import core.Record;
import core.Sound;

public class Board extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3032726387546045087L;
	public JPanel pnBoard;
	public ChessRecord pnRecord;
	PrisonerPane pnPrisoner;
	public JButton btnBoard[][];
	JButton btnRedo, btnUndo;
	DecoButton create = new DecoButton();
	Sound sound = new Sound();
	private Move premove;

	public Board(int height) {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		createMenuBar();
		createBoard(height);
		createPlayerPane();
		createRecordPane();

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createPlayerPane() {
		pnPrisoner = new PrisonerPane("Player", "Computer");
		add(pnPrisoner, BorderLayout.WEST);
	}

	JMenuItem itemNew = new JMenuItem("New game");
	JMenuItem itemSave = new JMenuItem("Save");
	JMenuItem itemOpen = new JMenuItem("Open");
	JMenuItem itemExit = new JMenuItem("Exit");
	JMenuItem itemHelp = new JMenuItem("Help");
	JMenuItem itemIntro = new JMenuItem("Introduction");

	private void createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu menuOption = new JMenu("Option");
		JMenu menuHelp = new JMenu("Help");

		itemNew.setMnemonic('N');
		itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

		itemSave.setMnemonic('S');
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		itemOpen.setMnemonic('O');
		itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

		itemExit.setMnemonic('E');
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));

		itemHelp.setMnemonic('H');
		itemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

		itemIntro.setMnemonic('I');
		itemIntro.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		itemNew.addActionListener(this);
		itemOpen.addActionListener(this);
		itemSave.addActionListener(this);
		itemExit.addActionListener(this);
		itemHelp.addActionListener(this);
		itemIntro.addActionListener(this);

		menuOption.add(itemNew);
		menuOption.addSeparator();
		menuOption.add(itemSave);
		menuOption.addSeparator();
		menuOption.add(itemOpen);
		menuOption.addSeparator();
		menuOption.add(itemExit);

		menuHelp.add(itemHelp);
		menuHelp.addSeparator();
		menuHelp.add(itemIntro);

		menubar.add(menuOption);
		menubar.add(menuHelp);
		setJMenuBar(menubar);
	}

	private void createBoard(int height) {
		pnBoard = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1844582857812420107L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(create.resizeImage(height, height, "image\\chessboard2.png").getImage(), 0, 0, null);
				repaint();
			}
		};
		Dimension dboard = new Dimension(height, height);
		pnBoard.setBorder(new EmptyBorder(height / 34, height / 34, height / 34, height / 34));
		pnBoard.setLayout(new GridLayout(8, 8));
		pnBoard.setPreferredSize(dboard);
		pnBoard.setMaximumSize(dboard);
		pnBoard.setMinimumSize(dboard);
		pnBoard.setSize(dboard);

		btnBoard = new JButton[8][8];
		Dimension d = new Dimension(height / 8, height / 8);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				btnBoard[i][j] = create.paintButton();
				btnBoard[i][j].setPreferredSize(d);
				btnBoard[i][j].setMaximumSize(d);
				pnBoard.add(btnBoard[i][j]);
			}
		}
		getContentPane().add(pnBoard, BorderLayout.CENTER);
	}

	private void createRecordPane() {
		pnRecord = new ChessRecord();
		getContentPane().add(pnRecord, BorderLayout.EAST);
	}

	public void resetBorderIgnore(Location l) {
		for (int i = 0; i < btnBoard.length; i++) {
			for (int j = 0; j < btnBoard[0].length; j++) {
				btnBoard[i][j].setBorder(null);
			}
		}
		btnBoard[l.getX()][l.getY()].setBorderPainted(true);
		btnBoard[l.getX()][l.getY()].setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLUE, Color.RED),
				BorderFactory.createRaisedBevelBorder()));
	}

	public void resetBorder() {
		for (int i = 0; i < btnBoard.length; i++) {
			for (int j = 0; j < btnBoard[0].length; j++) {
				btnBoard[i][j].setBorder(null);
			}
		}
	}

	public boolean makeMove(Move move, int player) {
		if (move != null) {
			if (move.isPromotion()) {
				System.out.println("view isPromotion");
				promotion(move);
			} else if (move.isCastlingKing() || move.isCastlingQueen()) {
				System.out.println("view isCastling");
				castling(move);
			} else if (move.isPassant()) {
				System.out.println("view isPassant");
				passant(move);
			} else if (!move.isCastlingKing() && !move.isCastlingQueen() && !move.isPassant() && !move.isPromotion()) {
				System.out.println("view isNormal");
				normal(move);
			}
			resetBorderIgnore(move.getTo());
			// thêm 1 bước đi vào trong kì phổ
			pnRecord.add(new Record(ChessAction.count, player(player), move));
			pnPrisoner.updatePrisoner(move, 1);
			setPremove(move);
			validate();
			return true;
		}
		return false;
	}

	public boolean unMakeMove(Move move, Move premove) {
		if (move != null) {
			if (move.isPromotion()) {
				undoPromotion(move);
			} else if (move.isCastlingKing() ^ move.isCastlingQueen()) {
				undoCastling(move);
			} else if (move.passant(premove)) {
				undoPassant(move);
			} else if (!move.isCastlingKing() && !move.isCastlingQueen() && !move.isPassant() && !move.isPromotion()) {
				undoNormal(move);
			}
			resetBorderIgnore(premove.getTo());
			setPremove(premove);
			// xóa một nước đi trong kì phổ
			pnRecord.remove();
			pnPrisoner.updatePrisoner(move, -1);
			return true;
		}
		return false;
	}

	private void undoPromotion(Move move) {
		getPiece(move.getTo()).setIcon(null);
		getPiece(move.getFrom()).setIcon(new ImageIcon("image\\" + move.getPieceFrom()));
	}

	private void undoNormal(Move move) {
		getPiece(move.getFrom()).setIcon(new ImageIcon("image\\" + move.getPieceFrom().getLinkImg()));
		if (move.getPrisoner() != null) {
			getPiece(move.getTo()).setIcon(new ImageIcon("image\\" + move.getPrisoner().getLinkImg()));
		} else {
			getPiece(move.getTo()).setIcon(null);
		}
	}

	private void undoPassant(Move move) {
		getPiece(premove.getTo()).setIcon(new ImageIcon("image\\" + move.getPrisoner().getLinkImg()));
		getPiece(move.getFrom()).setIcon(new ImageIcon("image\\" + move.getPieceFrom().getLinkImg()));
		getPiece(move.getTo()).setIcon(null);
	}

	private void undoCastling(Move move) {
		getPiece(move.getFrom()).setIcon(new ImageIcon("image\\" + move.getPieceFrom().getLinkImg()));
		getPiece(move.getTo()).setIcon(null);
		int x = move.getFrom().getX();
		if (move.isCastlingQueen()) {
			getPiece(new Location(x, 0)).setIcon(btnBoard[x][3].getIcon());
			getPiece(new Location(x, 3)).setIcon(null);
		}
		if (move.isCastlingKing()) {
			getPiece(new Location(x, 7)).setIcon(btnBoard[x][5].getIcon());
			getPiece(new Location(x, 5)).setIcon(null);
		}
	}

	private void normal(Move move) {
		getPiece(move.getTo()).setIcon(new ImageIcon("image\\" + move.getPieceFrom().getLinkImg()));
		getPiece(move.getFrom()).setIcon(null);
		if (move.getPrisoner() == null)
			sound.playSound("sound\\Move.WAV");
		else
			sound.playSound("sound\\eat.wav");
	}

	private void promotion(Move move) {
		if (move != null && move.getPiecePromotion() != null) {
			getPiece(move.getTo()).setIcon(new ImageIcon("image\\" + move.getPiecePromotion().getLinkImg()));
			getPiece(move.getFrom()).setIcon(null);
			sound.playSound("sound\\Move.WAV");
		}
	}

	private void castling(Move move) {
		move.getPieceFrom().getRule().setRule(null);
		int x = move.getTo().getX();
		if (move.isCastlingQueen()) {
			btnBoard[x][3].setIcon(btnBoard[x][0].getIcon());
			btnBoard[x][0].setIcon(null);
		}

		else if (move.isCastlingKing()) {
			btnBoard[x][5].setIcon(btnBoard[x][7].getIcon());
			btnBoard[x][7].setIcon(null);
		}
		getPiece(move.getFrom()).setIcon(null);
		getPiece(move.getTo()).setIcon(new ImageIcon("image\\" + move.getPieceFrom().getLinkImg()));
		sound.playSound("sound\\Capture.WAV");
	}

	private void passant(Move move) {
		if (move != null && move.isPassant() && move.getPrisoner() != null) {
			getPiece(move.getTo()).setIcon(new ImageIcon("image\\" + move.getPieceFrom().getLinkImg()));
			getPiece(move.getFrom()).setIcon(null);
			getPiece(move.getPrisoner().getLocation()).setIcon(null);
			sound.playSound("sound\\eat.wav");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemNew) {

			String[] option = { "New Game", "Cancel" };
			int choice = JOptionPane.showOptionDialog(null, "Are you really want to play?", "New Game",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
			if (choice == JOptionPane.YES_OPTION) {
				new BoardController(new ChessBoard(), new Board(600));
				return;
			}
		}
		if (e.getSource() == itemOpen) {

		}
		if (e.getSource() == itemSave) {

		}
		if (e.getSource() == itemExit) {
			String[] option = { "Close", "Cancel" };
			int choice = JOptionPane.showOptionDialog(null, "Are you really want to play?", "New Game",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, option, option[0]);
			if (choice == JOptionPane.YES_OPTION) {
				System.exit(0);
				return;
			}
		}
		if (e.getSource() == itemHelp) {

		}
		if (e.getSource() == itemIntro) {

		}
	}

	public String player(int player) {
		String players = "";
		if (player == Player.COMPUTER) {
			players = "COMPUTER:";
		} else if (player == Player.PLAYER) {
			players = "YOU:";
		}
		return players;
	}

	public JButton getPiece(Location location) {
		return btnBoard[location.getX()][location.getY()];
	}

	public Move getPremove() {
		return premove;
	}

	public void setPremove(Move premove) {
		this.premove = premove;
	}

}
