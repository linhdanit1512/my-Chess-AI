package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import action.ChessAction;
import action.Move;
import core.ChessBoard;
import gui.ChessRecord;

public class RecordController implements ActionListener{
	private ChessRecord record;
	private ChessAction action;
	ChessBoard board;
	
	
	
	public RecordController(ChessRecord record, ChessAction action, ChessBoard board) {
		super();
		this.record = record;
		this.action = action;
		this.board = board;
	}
	
	void init(){
		record.btnRedo.addActionListener(this);
		record.btnUndo.addActionListener(this);
	}
	
	
	public ChessRecord getRecord() {
		return record;
	}
	public void setRecord(ChessRecord record) {
		this.record = record;
	}
	public ChessAction getAction() {
		return action;
	}
	public void setAction(ChessAction action) {
		this.action = action;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==record.btnRedo){
			Move move = action.redo();
			record.remove();
		}
	}
	
}
