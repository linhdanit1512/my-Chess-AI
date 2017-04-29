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
		int height = 35;
		lblOrder = new JLabel();
		lblOrder.setPreferredSize(new Dimension(50, height));

		lblPlayer = new JLabel();
		lblPlayer.setPreferredSize(new Dimension(80, height));

		lblMove = new JLabel();
		lblMove.setPreferredSize(new Dimension(120, height));
		
		lblOrder.setOpaque(true);
    	lblPlayer.setOpaque(true);
    	lblMove.setOpaque(true);
    	
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
		lblOrder.setText(record.getOrder() + ".");
		lblPlayer.setText(record.getPlayer());
		lblMove.setText(record.getMove().toString());
		

		
		 // when select item
	    if (isSelected) {
	    	Color color = new Color(252,203,56);
	    	lblOrder.setBackground(color);
	    	lblPlayer.setBackground(color);
	    	lblMove.setBackground(color);
	        setBackground(color);
	    } else { // when don't select
	    	Color color = Color.WHITE;
	    	lblOrder.setBackground(color);
	    	lblPlayer.setBackground(color);
	    	lblMove.setBackground(color);
	        setBackground(color);
	    }
		
		return this;
	}

}
