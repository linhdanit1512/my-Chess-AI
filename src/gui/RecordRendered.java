package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import core.Record;

public class RecordRendered extends JPanel implements ListCellRenderer<Record> {
	private static final long serialVersionUID = -1211678324992453691L;
	JLabel lblOrder, lblPlayer, lblMove;
	JPanel pn1, pn2, pn3;

	public RecordRendered() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(310, 25));
		setBackground(Color.WHITE);
		int height = 25;
		pn1 = new JPanel();
		pn2 = new JPanel();
		pn3 = new JPanel();
		pn1.setLayout(new FlowLayout(FlowLayout.LEFT));
		pn2.setLayout(new FlowLayout(FlowLayout.LEFT));
		pn3.setLayout(new FlowLayout(FlowLayout.LEFT));
		lblOrder = new JLabel();
		lblOrder.setHorizontalTextPosition(SwingConstants.LEFT);

		lblPlayer = new JLabel();
		lblPlayer.setHorizontalTextPosition(SwingConstants.LEFT);

		lblMove = new JLabel();
		lblMove.setHorizontalTextPosition(SwingConstants.LEFT);

		lblOrder.setOpaque(true);
		lblPlayer.setOpaque(true);
		lblMove.setOpaque(true);
		
		pn1.setOpaque(true);
		pn2.setOpaque(true);
		pn3.setOpaque(true);

		pn1.setPreferredSize(new Dimension(30, height));
		pn2.setPreferredSize(new Dimension(75, height));
		pn3.setPreferredSize(new Dimension(205, height));
		pn1.add(lblOrder);
		pn2.add(lblPlayer);
		pn3.add(lblMove);

		add(pn1);
		add(pn2);
		add(pn3);
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
		lblOrder.setText(record.getOrder() + ".");
		lblPlayer.setText(record.getPlayer());
		lblMove.setText(record.getMove().toFullString());

		// when select item
		if (isSelected) {
			Color color = new Color(252, 203, 56);
			lblOrder.setBackground(color);
			lblPlayer.setBackground(color);
			lblMove.setBackground(color);
			pn1.setBackground(color);
			pn2.setBackground(color);
			pn3.setBackground(color);
			setBackground(color);
		} else { // when don't select
			Color color = Color.WHITE;
			lblOrder.setBackground(color);
			lblPlayer.setBackground(color);
			lblMove.setBackground(color);
			pn1.setBackground(color);
			pn2.setBackground(color);
			pn3.setBackground(color);
			setBackground(color);
		}

		return this;
	}

}
