package demo;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class demo extends JFrame implements MouseMotionListener, MouseListener {

	Point start, stop;
	JLabel lbl;

	JPanel pn;

	public demo() {
		setLayout(null);
		// pn = new JPanel();
		lbl = new JLabel(new ImageIcon("image\\whiteking.png"));
		add(lbl);
		addMouseListener(this);
		addMouseMotionListener(this);
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new demo();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		stop = e.getPoint();
		System.out.println("stop " + stop);
		int x = (int) (stop.getX());
		int y = (int) (stop.getY());
		int w = lbl.getIcon().getIconWidth();
		int h = lbl.getIcon().getIconHeight();
		lbl.setBounds(new Rectangle(x-w/2, y-h, w, h));
		repaint();
		// add(pn);
		validate();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		start = e.getPoint();
		System.out.println("start: " + start);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
