package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import action.Move;

public class Record extends JPanel {
	private static final long serialVersionUID = -3109209659650957403L;
	JLabel lblOrder, lblPlayer, lblMove;

	public Record(int order, String player, Move move) {
		super();
		lblOrder = new JLabel(order + "");
		lblPlayer = new JLabel(player);
		lblMove = new JLabel(move.toString());
	}

	public void setLblOrder(String lblOrder) {
		this.lblOrder.setText(lblOrder);
	}

	public void setLblPlayer(String lblPlayer) {
		this.lblPlayer .setText(lblPlayer);
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

}
