package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DecoButton {

	public JButton paintButton() {
		JButton btn = new JButton();
		btn.setBorder(null);
		btn.setHorizontalAlignment(JLabel.CENTER);
		btn.setVerticalAlignment(JLabel.CENTER);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		return btn;

	}

	public JButton paintButton(String icon) {
		JButton btn = new JButton(new ImageIcon(icon));
		btn.setBorder(null);
		btn.setHorizontalAlignment(JLabel.CENTER);
		btn.setVerticalAlignment(JLabel.CENTER);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		return btn;

	}

	public JButton createButton() {
		JButton btn = new JButton();
		btn.setHorizontalAlignment(JLabel.CENTER);
		btn.setVerticalAlignment(JLabel.CENTER);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		return btn;
	}
	
	public JButton createButton(String icon) {
		JButton btn = new JButton(new ImageIcon(icon));
		btn.setHorizontalAlignment(JLabel.CENTER);
		btn.setVerticalAlignment(JLabel.CENTER);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		return btn;
	}

	public ImageIcon resizeImage(int h, int w, String fileName) {
		try {
			if (fileName != null) {

				BufferedImage image = ImageIO.read(new File(fileName));

				int ix = image.getWidth();
				int iy = image.getHeight();
				int dx = 0, dy = 0;

				if (h / w > ix / iy) {
					dy = w;
					dx = dy * ix / iy;
				} else {
					dx = h;
					dy = dx * iy / ix;
				}

				ImageIcon icon = new ImageIcon(image.getScaledInstance(dx, dy, Image.SCALE_SMOOTH));
				return icon;
			} else
				return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
