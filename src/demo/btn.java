package demo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class btn extends JFrame implements ActionListener {

	JButton btn;

	public btn() {
		JPanel pn = new JPanel();
		btn = new JButton(new ImageIcon("image\\blackking.png"));
		btn.addActionListener(this);
		pn.add(btn);
		add(pn);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	int count = 0;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn) {
			if (count == 0) {
				btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
				count = 1;
				System.out.println(count);
				return;
			} else if (count == 1) {
				btn.setBorder(null);
				count = 0;
				System.out.println(count);
				return;
			}
		}
	}

	public static void main(String[] args) {
		new btn();
	}

}
