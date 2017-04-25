package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import core.Record;

public class RecordRendered extends JPanel implements ListCellRenderer<Record> {
	private static final long serialVersionUID = -1211678324992453691L;
	JLabel lblOrder, lblPlayer, lblMove;

	public RecordRendered() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(250, 30));
		setBackground(Color.WHITE);
		int height = 25;
		lblOrder = new JLabel();
		lblOrder.setPreferredSize(new Dimension(height, 50));

		lblPlayer = new JLabel();
		lblPlayer.setPreferredSize(new Dimension(height, 80));

		lblMove = new JLabel();
		lblMove.setPreferredSize(new Dimension(height, 120));

		add(lblOrder);
		add(lblPlayer);
		add(lblMove);
	}

	public void setLblOrder(String lblOrder) {
		this.lblOrder.setText(lblOrder);
	}

	public void setLblPlayer(String lblPlayer) {
		this.lblPlayer.setText(lblPlayer);
	}

	public void setLblMove(String lblMove) {
		this.lblMove.setText(lblMove);
	}

	public void setLblOrder(JLabel lblOrder) {
		this.lblOrder = lblOrder;
	}

	public void setLblPlayer(JLabel lblPlayer) {
		this.lblPlayer = lblPlayer;
	}

	public void setLblMove(JLabel lblMove) {
		this.lblMove = lblMove;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Record> list, Record record, int index,
			boolean isSelected, boolean cellHasFocus) {
		lblOrder.setText(record.getOrder()+".");
		lblPlayer.setText(record.getPlayer());
		lblMove.setText(record.getMove().toString());
		return this;
	}

}
