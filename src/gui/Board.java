package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Board extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3032926387546045087L;
	public JPanel pnPlayer1, pnPlayer2, pnBoard, pnRecord;
	public JButton btnBoard[][];
	JButton btnNew, btnSave, btnOpen, btnRedo, btnUndo;
	CreateButton create = new CreateButton();

	public JLabel lblPieceDragged;

	public Board() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		createMenuBar();
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

		btnBoard = new JButton[8][8];
		// Dimension d = new Dimension(520 / 8, 520 / 8);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				btnBoard[i][j] = create.paintButton();
				btnBoard[i][j].setFocusPainted(true);
				// btnBoard[i][j].setPreferredSize(d);
				// btnBoard[i][j].setMinimumSize(d);
				pnBoard.add(btnBoard[i][j]);
			}
		}
		pnRecord = new JPanel();
		pnRecord.setLayout(new BorderLayout());
		pnRecord.setMinimumSize(new Dimension(250, getHeight()));
		pnRecord.setPreferredSize(new Dimension(250, getHeight()));

		// JPanel pnOption = new JPanel();
		// btnNew = create.paintButton();
		// btnNew.setBorder(new LineBorder(Color.ORANGE, 1));
		// btnNew.setIcon(new ImageIcon("image\\hovernew.png"));
		// btnNew.setRolloverIcon(new ImageIcon("image\\new.png"));
		// btnSave = create.paintButton();
		// btnSave.setIcon(new ImageIcon("image\\hoversave.png"));
		// btnSave.setRolloverIcon(new ImageIcon("image\\save.png"));
		// btnOpen = create.paintButton();
		// btnOpen.setRolloverIcon(new ImageIcon("image\\open.png"));
		// btnOpen.setIcon(new ImageIcon("image\\hoveropen.png"));
		// pnOption.add(btnNew);
		// pnOption.add(btnSave);
		// pnOption.add(btnOpen);

		JPanel pnRedoUndo = new JPanel();
		btnRedo = new JButton(new ImageIcon("image\\redo.png"));

		btnUndo = new JButton(new ImageIcon("image\\undo.png"));
		pnRedoUndo.add(btnUndo);
		pnRedoUndo.add(btnRedo);

		pnRecord.add(pnRedoUndo, BorderLayout.SOUTH);
		// pnRecord.add(pnOption, BorderLayout.NORTH);

		getContentPane().add(pnPrisoner, BorderLayout.WEST);
		getContentPane().add(pnBoard, BorderLayout.CENTER);
		getContentPane().add(pnRecord, BorderLayout.EAST);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setResizable(false);
		// setLocationRelativeTo(null);
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

	public void setIcon(String icon) {
		lblPieceDragged.setIcon(new ImageIcon(icon));
	}

	public static void main(String[] args) {
		new Board();
	}

}
