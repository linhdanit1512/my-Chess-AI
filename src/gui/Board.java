package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import chess.Piece;
import core.ChessBoard;

public class Board extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3032926387546045087L;
	public JPanel pnPlayer1, pnPlayer2, pnBoard, pnRecord;
	ChessBoard board;
	public JButton btnBoard[][];
	Piece[][] pieces;
	JButton btnNew, btnSave, btnOpen, btnRedo, btnUndo;
	CreateButton create = new CreateButton();

	public Board(ChessBoard board) {
		this.board = board;
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
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

		pnBoard = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1844582857812420107L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(create.resizeImage(520, 520, "image\\chessboard.png").getImage(), 0, 0, null);
				repaint();
			}
		};
		Dimension dboard = new Dimension(520, 520);
		pnBoard.setLayout(new GridLayout(8, 8));
		pnBoard.setPreferredSize(dboard);
		pnBoard.setMaximumSize(dboard);
		pnBoard.setMinimumSize(dboard);
		pnBoard.setSize(dboard);

		pieces = board.pieceBoard;

		btnBoard = new JButton[8][8];
		Dimension d = new Dimension(520 / 8, 520 / 8);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				btnBoard[i][j] = create.paintButton();
				if (pieces[i][j] != null) {
					btnBoard[i][j].setIcon(new ImageIcon("image\\" + pieces[i][j].getLinkImg()));
				}
				btnBoard[i][j].setPreferredSize(d);
				btnBoard[i][j].setMinimumSize(d);
				pnBoard.add(btnBoard[i][j]);
			}
		}
		pnRecord = new JPanel();
		pnRecord.setLayout(new BorderLayout());
		pnRecord.setMinimumSize(new Dimension(250, getHeight()));
		pnRecord.setPreferredSize(new Dimension(250, getHeight()));

		JPanel pnOption = new JPanel();
		btnNew = create.paintButton();
		btnNew.setBorder(new LineBorder(Color.ORANGE, 1));
		btnNew.setIcon(new ImageIcon("image\\hovernew.png"));
		btnNew.setRolloverIcon(new ImageIcon("image\\new.png"));
		btnSave = create.paintButton();
		btnSave.setIcon(new ImageIcon("image\\hoversave.png"));
		btnSave.setRolloverIcon(new ImageIcon("image\\save.png"));
		btnOpen = create.paintButton();
		btnOpen.setRolloverIcon(new ImageIcon("image\\open.png"));
		btnOpen.setIcon(new ImageIcon("image\\hoveropen.png"));
		pnOption.add(btnNew);
		pnOption.add(btnSave);
		pnOption.add(btnOpen);

		JPanel pnRedoUndo = new JPanel();
		btnRedo = new JButton(new ImageIcon("image\\redo.png"));

		btnUndo = new JButton(new ImageIcon("image\\undo.png"));
		pnRedoUndo.add(btnUndo);
		pnRedoUndo.add(btnRedo);

		pnRecord.add(pnRedoUndo, BorderLayout.SOUTH);
		pnRecord.add(pnOption, BorderLayout.NORTH);

		add(pnPrisoner, BorderLayout.WEST);
		add(pnBoard, BorderLayout.CENTER);
		add(pnRecord, BorderLayout.EAST);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Board(new ChessBoard());
	}

}
