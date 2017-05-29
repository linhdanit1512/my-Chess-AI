package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.DecoButton;

public class Home extends JFrame implements ActionListener {
	private static final long serialVersionUID = 6886943074531948200L;
	JButton btnCom_Com, btnPlayer_Com, btnPlayer_Player;

	public Home() {
		setTitle("Memory Game");
		setLayout(null);
		setContentPane(new JLabel(new ImageIcon("image\\background.jpg")));

		JPanel pnInMain = new JPanel();
		pnInMain.add(createPane());
		pnInMain.setOpaque(false);
		add(pnInMain);
		pnInMain.setBounds(200, 60, 500, 600);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Home();
	}

	DecoButton deco = new DecoButton();

	public JPanel createPane() {
		Font font = new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, 22);
		Color color = new Color(0, 0, 0);

		ImageIcon icon = new ImageIcon(("image\\btn.png"));

		btnCom_Com = deco.createButton(icon, "Computer vs Computer", font, color);
		btnPlayer_Com = deco.createButton(icon, "Player vs Computer", font, color);
		btnPlayer_Player = deco.createButton(icon, "Player vs Player", font, color);

		btnCom_Com.addActionListener(this);
		btnPlayer_Com.addActionListener(this);
		btnPlayer_Player.addActionListener(this);

		JPanel body = new JPanel();
		body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
		body.setOpaque(false);

		body.add(btnPlayer_Player);
		body.add(btnPlayer_Com);
		body.add(btnCom_Com);

		return body;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCom_Com) {
			new Computer_Computer().run();
			this.setVisible(false);
		} else if (e.getSource() == btnPlayer_Com) {
			new Player_Computer();
			this.setVisible(false);
		} else if (e.getSource() == btnPlayer_Player) {
			new Player_Player();
			this.setVisible(false);
		}
	}
}
