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
	public static final int PLAYER_PLAYER = 1000;
	public static final int PLAYER_COMPUTER = 2000;
	public static final int COMPUTER_COMPUTER = 3000;

	private static final long serialVersionUID = 6886943074531948200L;
	JButton btnCom_Com, btnPlayer_Com, btnPlayer_Player;

	public Home() {
		setTitle("Chess Funny");
		setLayout(null);
		setContentPane(new JLabel(new ImageIcon("image\\background.jpg")));

		JPanel pnInMain = new JPanel();
		pnInMain.add(createPane());
		pnInMain.setOpaque(false);
		add(pnInMain);
		pnInMain.setBounds(120, 40, 400, 600);
		setSize(650, 450);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Home();
	}

	DecoButton deco = new DecoButton();

	public JPanel createPane() {
		Font font = new Font("Agency FB", Font.BOLD | Font.ITALIC, 22);
		Color color = new Color(0, 0, 0);

		ImageIcon icon = new ImageIcon("image\\btn.png");
		ImageIcon rollicon = new ImageIcon("image\\btnhover.png");

		 btnCom_Com = deco.createButton(icon, "Computer vs Computer", font,
		 color);
		btnPlayer_Com = deco.createButton(icon, "Player vs Computer", font, color);
		btnPlayer_Player = deco.createButton(icon, "Player vs Player", font, color);

		 btnCom_Com.setRolloverIcon(rollicon);
		btnPlayer_Com.setRolloverIcon(rollicon);
		btnPlayer_Player.setRolloverIcon(rollicon);

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
			this.setVisible(false);
			new Computer_Computer();
		} else
		if (e.getSource() == btnPlayer_Com) {
			this.setVisible(false);
			new Player_Computer();
		} else if (e.getSource() == btnPlayer_Player) {
			this.setVisible(false);
			new Player_Player();
		}
	}
}
