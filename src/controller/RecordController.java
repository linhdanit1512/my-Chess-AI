package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import action.ChessAction;
import action.Move;
import core.ChessBoard;
import core.Player;
import core.Record;
import gui.Board;

public class RecordController implements ActionListener, KeyListener {
	private ChessAction action;
	private ChessBoard board;
	private Board view;

	public RecordController(BoardController boardcontrol) {
		super();
		this.action = boardcontrol.getAction();
		this.board = boardcontrol.getModel();
		this.view = boardcontrol.getView();
		init();
	}

	void init() {
		view.pnRecord.btnRedo.addActionListener(this);
		view.pnRecord.btnRedo.addKeyListener(this);
		view.pnRecord.btnUndo.addActionListener(this);
		view.pnRecord.btnUndo.addKeyListener(this);
	}

	public void addRecord(Move move) {
		if (move != null) {
			String player = "";
			if (board.getPlayer() == Player.COMPUTER) {
				player = "COMPUTER:";
			} else if (board.getPlayer() == Player.PLAYER) {
				player = "YOU:";
			}
			view.pnRecord.add(new Record(ChessAction.count, player, move));
			view.repaint();
		}
	}

	public void removeRecord(Move move) {
		if (move != null) {
			view.pnRecord.remove();
			view.repaint();
		}
	}

	void undo() {
		removeRecord(action.undo());
	}

	void redo() {
		addRecord(action.redo());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.pnRecord.btnRedo) {
			System.out.println("redo");
			action.redo();
			return;
		}
		if (e.getSource() == view.pnRecord.btnUndo) {
			System.out.println("undo");
			action.undo();
			return;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown()) {
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				action.undo();
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_Y) {
				action.redo();
				return;
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public ChessAction getAction() {
		return action;
	}

	public ChessBoard getBoard() {
		return board;
	}

	public Board getView() {
		return view;
	}

}
