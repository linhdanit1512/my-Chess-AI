package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ChessRecord extends JPanel {
	private static final long serialVersionUID = -942014659208561589L;
	private JPanel pnRecord, pnRecordContent;
	private JList<Record> jlist;
	DefaultListModel<Record> model;
	public JButton btnRedo, btnUndo;
	DecoButton deco = new DecoButton();

	public ChessRecord() {
		setMaximumSize(new Dimension(250, 1000));
		createRecordPane();
	}

	public void createRecordPane() {

		pnRecord = new JPanel();
		pnRecord.setLayout(new BorderLayout());
		pnRecord.setMinimumSize(new Dimension(250, getHeight()));
		pnRecord.setPreferredSize(new Dimension(250, getHeight()));

		pnRecordContent = new JPanel();
		DefaultListModel<Record> model = new DefaultListModel<>();
		jlist = new JList<Record>(model);
		JScrollPane scroll = new JScrollPane(jlist);
		pnRecordContent.add(scroll, BorderLayout.CENTER);

		JPanel pnRedoUndo = new JPanel();
		btnRedo = new JButton(deco.resizeImage(40, 60, "image\\redo.png"));

		btnUndo = new JButton(deco.resizeImage(40, 60, "image\\undo.png"));
		pnRedoUndo.add(btnUndo);
		pnRedoUndo.add(btnRedo);

		pnRecord.add(pnRecordContent, BorderLayout.CENTER);
		pnRecord.add(pnRedoUndo, BorderLayout.SOUTH);

		add(pnRecord, BorderLayout.EAST);

	}

	public void addList(Record record) {
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
