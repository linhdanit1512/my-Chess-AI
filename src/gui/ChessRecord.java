package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.Record;

public class ChessRecord extends JPanel {
	private static final long serialVersionUID = -942014659208561589L;
	private JPanel pnRecord, pnRecordContent;
	private JList<Record> jlist;
	DefaultListModel<Record> model;
	public JButton btnRedo, btnUndo;
	DecoButton deco = new DecoButton();

	public ChessRecord() {
		setSize(new Dimension(270, 400));
		createRecordPane();
	}

	public void createRecordPane() {

		pnRecord = new JPanel();
		pnRecord.setLayout(new BorderLayout());
		pnRecord.setMinimumSize(new Dimension(250, getHeight()));
		pnRecord.setPreferredSize(new Dimension(250, getHeight()));

		pnRecordContent = new JPanel();
		model = new DefaultListModel<Record>();
		jlist = new JList<Record>(model);
		jlist.setFixedCellHeight(30);
		jlist.setAutoscrolls(true);
		jlist.setCellRenderer(new RecordRendered());
		JScrollPane scroll = new JScrollPane(jlist);
		scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
		pnRecordContent.add(scroll, BorderLayout.CENTER);

		JPanel pnRedoUndo = new JPanel();
		btnRedo = new JButton(deco.resizeImage(40, 60, "image\\redo.png"));
		btnRedo.setToolTipText("Redo");
		btnUndo = new JButton(deco.resizeImage(40, 60, "image\\undo.png"));
		btnUndo.setToolTipText("Undo");
		pnRedoUndo.add(btnUndo);
		pnRedoUndo.add(btnRedo);

		pnRecord.add(pnRecordContent, BorderLayout.CENTER);
		pnRecord.add(pnRedoUndo, BorderLayout.SOUTH);

		add(pnRecord, BorderLayout.EAST);

	}

	public void add(Record record) {
		if (model == null)
			model = new DefaultListModel<>();
		model.addElement(record);
		jlist.setModel(model);
	}

	public void remove() {
		if (model == null || model.size() == 0)
			return;
		int index = model.getSize() - 1;
		model.remove(index);
		jlist.setModel(model);
	}

	public JList<Record> getJlist() {
		return jlist;
	}

	public void setJlist(JList<Record> jlist) {
		this.jlist = jlist;
	}

}
