package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;

import action.ChessAction;
import action.Move;
import core.ChessBoard;
import core.Player;
import core.Record;
import gui.Board;

public class RecordController implements ActionListener, KeyListener {
	private ChessAction action;
	ChessBoard board;
	Board view;

	public RecordController(BoardController boardcontrol) {
		super();
		this.action = boardcontrol.action;
		this.board = boardcontrol.model;
		this.view = boardcontrol.view;
	}

	void init() {
		view.pnRecord.btnRedo.addActionListener(this);
		view.pnRecord.btnRedo.addKeyListener(this);
		view.pnRecord.btnUndo.addActionListener(this);
		view.pnRecord.btnUndo.addKeyListener(this);
	}

	synchronized void undo() {
		Move move = action.undo();
		if (move != null) {
			view.undo(move);
			Move preMove = action.peek();
			view.pnRecord.remove();
			view.btnBoard[preMove.getTo().getX()][preMove.getTo().getY()]
					.setBorder(BorderFactory.createEtchedBorder(5, Color.BLUE, Color.RED));
			if (board.getPlayer() == Player.COMPUTER) {
				board.setPlayer(Player.PLAYER);
			} else if (board.getPlayer() == Player.PLAYER) {
				board.setPlayer(Player.COMPUTER);
			}
			view.repaint();
		}
	}

	synchronized void redo() {
		Move move = action.redo();
		if (move != null) {
			String player = "";
			if (board.getPlayer() == Player.COMPUTER) {
				board.setPlayer(Player.PLAYER);
				player = "COMPUTER:";
			} else if (board.getPlayer() == Player.PLAYER) {
				board.setPlayer(Player.COMPUTER);
				player = "YOU:";
			}
			view.pnRecord.add(new Record(action.getCount(), player, move));
			view.redo(move);
			view.btnBoard[move.getTo().getX()][move.getTo().getY()]
					.setBorder(BorderFactory.createEtchedBorder(5, Color.BLUE, Color.RED));
			view.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.pnRecord.btnRedo) {
			redo();
			return;
		}
		if (e.getSource() == view.pnRecord.btnUndo) {
			undo();
			return;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown()) {
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				undo();
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_Y) {
				redo();
				return;
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
