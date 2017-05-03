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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import action.Move;
import chess.Piece;
import chess.PieceType;
import controller.BoardController;
import core.ChessBoard;
import core.Location;

public class Board extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3032726387546045087L;
	public JPanel pnPlayer1, pnPlayer2, pnBoard;
	public ChessRecord pnRecord;
	public JButton btnBoard[][];
	JButton btnRedo, btnUndo;
	DecoButton create = new DecoButton();
	JLayeredPane layerPop;

	public JLabel lblPieceDragged;

	public Board(int height) {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		createMenuBar();
		createBoard(height);
		createPlayerPane();
		createRecordPane();
		createLayeredPane();

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	JMenuItem itemNew = new JMenuItem("New game");
	JMenuItem itemSave = new JMenuItem("Save");
	JMenuItem itemOpen = new JMenuItem("Open");
	JMenuItem itemExit = new JMenuItem("Exit");
	JMenuItem itemHelp = new JMenuItem("Help");
	JMenuItem itemIntro = new JMenuItem("Introduction");

	public void createMenuBar() {
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

	public void createBoard(int height) {
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

	public void createLayeredPane() {
		layerPop = super.getLayeredPane();
		layerPop.setLayer(this, JLayeredPane.POPUP_LAYER);
		layerPop.setLayout(null);
		// layerPop.setOpaque(false);
		lblPieceDragged = new JLabel();
		layerPop.add(lblPieceDragged);
		layerPop.setVisible(true);
	}

	public void createPlayerPane() {
		// chua cac quan co tu binh
		JPanel pnPrisoner = new JPanel();
		pnPrisoner.setLayout(new BoxLayout(pnPrisoner, BoxLayout.Y_AXIS));
		pnPrisoner.setMinimumSize(new Dimension(170, getHeight()));
		pnPrisoner.setPreferredSize(new Dimension(170, getHeight()));

		JLabel lblCom = new JLabel("COMPUTER");
		pnPlayer1 = new JPanel();

		pnPlayer2 = new JPanel();
		JLabel lblPeo = new JLabel("YOUR NAME");

		pnPrisoner.add(lblCom);
		pnPrisoner.add(pnPlayer1);
		pnPrisoner.add(pnPlayer2);
		pnPrisoner.add(lblPeo);
		getContentPane().add(pnPrisoner, BorderLayout.WEST);
	}

	public void resetBorderIgnore(Location l) {
		for (int i = 0; i < btnBoard.length; i++) {
			for (int j = 0; j < btnBoard[0].length; j++) {
				btnBoard[i][j].setBorder(null);
			}
		}
		btnBoard[l.getX()][l.getY()].setBorderPainted(true);
		btnBoard[l.getX()][l.getY()].setBorder(BorderFactory.createEtchedBorder(5, Color.BLUE, Color.RED));
	}

	public void resetBorder() {
		for (int i = 0; i < btnBoard.length; i++) {
			for (int j = 0; j < btnBoard[0].length; j++) {
				btnBoard[i][j].setBorder(null);
			}
		}
	}

	public void createRecordPane() {
		pnRecord = new ChessRecord();
		getContentPane().add(pnRecord, BorderLayout.EAST);
	}

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	public void redo(Move move) {
		Piece pieceFrom = move.getPieceFrom();
		Location to = move.getTo();
		Location from = move.getFrom();
		btnBoard[from.getX()][from.getY()].setIcon(null);
		btnBoard[to.getX()][to.getY()].setIcon(new ImageIcon("image\\" + pieceFrom.getLinkImg()));

	}

	public void undo(Move move) {
		Piece pieceFrom = move.getPieceFrom();
		Piece prisoner = move.getPrisoner();
		Location to = move.getTo();
		Location from = move.getFrom();
		btnBoard[from.getX()][from.getY()].setIcon(new ImageIcon("image\\" + pieceFrom.getLinkImg()));
		if (prisoner != null)
			btnBoard[to.getX()][to.getY()].setIcon(new ImageIcon("image\\" + prisoner.getLinkImg()));
		else
			btnBoard[to.getX()][to.getY()].setIcon(null);

		if (pieceFrom.getType() == PieceType.KING) {
			if (from.getX() - to.getX() == 0 && Math.abs(from.getY() - to.getY()) == 2) {
				int x = move.getTo().getX();
				int y = move.getTo().getY();
				if (y == 2) {
					btnBoard[x][0].setIcon(btnBoard[x][3].getIcon());
					btnBoard[x][3].setIcon(null);
				} else if (y == 6) {
					btnBoard[x][7].setIcon(btnBoard[x][5].getIcon());
					btnBoard[x][5].setIcon(null);
				}
			}
		}
	}

	public void setIcon(String icon) {
		lblPieceDragged.setIcon(new ImageIcon(icon));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemNew) {

			String[] option = { "New Game", "Cancel" };
			int choice = JOptionPane.showOptionDialog(null, "Are you really want to play?", "New Game",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
			if (choice == JOptionPane.YES_OPTION) {
				new BoardController(new ChessBoard(), new Board(570));
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

	public JLayeredPane getLayeredPane() {
		return this.layerPop;
	}
}
