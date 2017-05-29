package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import action.Move;
import chess.Alliance;
import chess.Piece;

public class PrisonerPane extends JPanel {
	private static final long serialVersionUID = -882521929467493359L;
	private JPanel pnPlayerBlack, pnPlayerWhite;
	JLabel lblPlayer1, lblPlayer2;
	short blackR, blackN, blackB, blackQ, blackP, whiteR, whiteN, whiteQ, whiteP, whiteB;
	JLabel lblBlackR, lblBlackN, lblBlackB, lblBlackQ, lblBlackP, lblWhiteR, lblWhiteN, lblWhiteQ, lblWhiteP, lblWhiteB;

	public PrisonerPane(String player1, String player2) {
		super();
		setLayout(new GridLayout(1, 2));
		setFont(new Font("Arial", Font.BOLD, 15));
		createPlayerPane(player1, player2);
		setVisible(true);
	}

	JPanel pnPrisoner = new JPanel();
	DecoButton deco = new DecoButton();

	private void createPlayerPane(String player1, String player2) {
		JLabel lblBlackRook, lblBlackKnight, lblBlackBishop, lblBlackPawn, lblBlackQueen, lblWhiteRook, lblWhiteKnight,
				lblWhiteBishop, lblWhitePawn, lblWhiteQueen;
		// chua cac quan co tu binh
		pnPrisoner.setLayout(new BoxLayout(pnPrisoner, BoxLayout.Y_AXIS));
		pnPrisoner.setMinimumSize(new Dimension(170, 300));
		pnPrisoner.setPreferredSize(new Dimension(170, 300));

		lblPlayer2 = new JLabel(player2.toUpperCase(), JLabel.LEFT);
		lblPlayer1 = new JLabel(player1.toUpperCase(), JLabel.LEFT);

		pnPlayerBlack = new JPanel();

		pnPlayerBlack.setLayout(new GridLayout(5, 2));

		int w = 45, h = 45;
		lblWhiteRook = new JLabel(deco.resizeImage(w, h, "image/whiterook.png"));
		lblWhiteKnight = new JLabel(deco.resizeImage(w, h, "image/whiteknight.png"));
		lblWhiteBishop = new JLabel(deco.resizeImage(w, h, "image/whitebishop.png"));
		lblWhitePawn = new JLabel(deco.resizeImage(w, h, "image/whitepawn.png"));
		lblWhiteQueen = new JLabel(deco.resizeImage(w, h, "image/whitequeen.png"));

		Font font = new Font("Arial", Font.BOLD, 20);
		Color color = new Color(131, 160, 10);

		lblWhiteR = new JLabel("", JLabel.RIGHT);
		lblWhiteN = new JLabel("", JLabel.RIGHT);
		lblWhiteQ = new JLabel("", JLabel.RIGHT);
		lblWhiteP = new JLabel("", JLabel.RIGHT);
		lblWhiteB = new JLabel("", JLabel.RIGHT);
		lblWhiteR.setAlignmentY(JComponent.RIGHT_ALIGNMENT);
		lblWhiteN.setAlignmentY(JComponent.RIGHT_ALIGNMENT);
		lblWhiteQ.setAlignmentY(JComponent.RIGHT_ALIGNMENT);
		lblWhiteB.setAlignmentY(JComponent.RIGHT_ALIGNMENT);
		lblWhiteP.setAlignmentY(JComponent.RIGHT_ALIGNMENT);

		lblWhiteR.setForeground(color);
		lblWhiteN.setForeground(color);
		lblWhiteQ.setForeground(color);
		lblWhiteP.setForeground(color);
		lblWhiteB.setForeground(color);

		lblWhiteR.setFont(font);
		lblWhiteN.setFont(font);
		lblWhiteQ.setFont(font);
		lblWhiteP.setFont(font);
		lblWhiteB.setFont(font);

		pnPlayerBlack.add(lblWhiteQ);
		pnPlayerBlack.add(lblWhiteQueen);
		pnPlayerBlack.add(lblWhiteR);
		pnPlayerBlack.add(lblWhiteRook);
		pnPlayerBlack.add(lblWhiteN);
		pnPlayerBlack.add(lblWhiteKnight);
		pnPlayerBlack.add(lblWhiteB);
		pnPlayerBlack.add(lblWhiteBishop);
		pnPlayerBlack.add(lblWhiteP);
		pnPlayerBlack.add(lblWhitePawn);

		pnPlayerWhite = new JPanel();
		pnPlayerWhite.setLayout(new GridLayout(5, 2));

		lblBlackRook = new JLabel(deco.resizeImage(w, h, "image/blackrook.png"));
		lblBlackKnight = new JLabel(deco.resizeImage(w, h, "image/blackknight.png"));
		lblBlackBishop = new JLabel(deco.resizeImage(w, h, "image/blackbishop.png"));
		lblBlackPawn = new JLabel(deco.resizeImage(w, h, "image/blackpawn.png"));
		lblBlackQueen = new JLabel(deco.resizeImage(w, h, "image/blackqueen.png"));

		lblBlackR = new JLabel("", JLabel.RIGHT);
		lblBlackN = new JLabel("", JLabel.RIGHT);
		lblBlackB = new JLabel("", JLabel.RIGHT);
		lblBlackQ = new JLabel("", JLabel.RIGHT);
		lblBlackP = new JLabel("", JLabel.RIGHT);

		lblBlackR.setForeground(color);
		lblBlackN.setForeground(color);
		lblBlackQ.setForeground(color);
		lblBlackP.setForeground(color);
		lblBlackB.setForeground(color);
		lblBlackR.setFont(font);
		lblBlackN.setFont(font);
		lblBlackQ.setFont(font);
		lblBlackP.setFont(font);
		lblBlackB.setFont(font);

		lblBlackR.setAlignmentY(JComponent.RIGHT_ALIGNMENT);
		lblBlackN.setAlignmentY(JComponent.RIGHT_ALIGNMENT);
		lblBlackQ.setAlignmentY(JComponent.RIGHT_ALIGNMENT);
		lblBlackB.setAlignmentY(JComponent.RIGHT_ALIGNMENT);
		lblBlackP.setAlignmentY(JComponent.RIGHT_ALIGNMENT);

		pnPlayerWhite.add(lblBlackQ);
		pnPlayerWhite.add(lblBlackQueen);
		pnPlayerWhite.add(lblBlackR);
		pnPlayerWhite.add(lblBlackRook);
		pnPlayerWhite.add(lblBlackN);
		pnPlayerWhite.add(lblBlackKnight);
		pnPlayerWhite.add(lblBlackB);
		pnPlayerWhite.add(lblBlackBishop);
		pnPlayerWhite.add(lblBlackP);
		pnPlayerWhite.add(lblBlackPawn);

		pnPrisoner.add(lblPlayer2);
		pnPrisoner.add(pnPlayerBlack);
		pnPrisoner.add(pnPlayerWhite);
		pnPrisoner.add(lblPlayer1);
		add(pnPrisoner, BorderLayout.WEST);
	}

	public void updatePrisoner(Move move, int quantity) {
		if (move != null) {
			Piece piece = move.getPrisoner();
			if (piece != null) {
				if (piece.getAlliance() == Alliance.BLACK) {
					switch (piece.getType()) {
					case QUEEN:
						blackQ += quantity;
						lblBlackQ.setText(blackQ + "");
						break;
					case ROOK:
						blackR += quantity;
						lblBlackR.setText(blackR + "");
						break;
					case BISHOP:
						blackB += quantity;
						lblBlackB.setText(blackB + "");
						break;
					case KNIGHT:
						blackN += quantity;
						lblBlackN.setText(blackN + "");
						break;
					case PAWN:
						blackP += quantity;
						lblBlackP.setText(blackP + "");
						break;
					default:
						break;
					}
				} else {
					switch (piece.getType()) {
					case QUEEN:
						whiteQ += quantity;
						lblWhiteQ.setText(whiteQ + "");
						break;
					case ROOK:
						whiteR += quantity;
						lblWhiteR.setText(whiteR + "");
						break;
					case BISHOP:
						whiteB += quantity;
						lblWhiteB.setText(whiteB + "");
						break;
					case KNIGHT:
						whiteN += quantity;
						lblWhiteN.setText(whiteN + "");
						break;
					case PAWN:
						whiteP += quantity;
						lblWhiteP.setText(whiteP + "");
						break;
					default:
						break;
					}
				}
				this.repaint();
				this.validate();
			}
		}
	}
}
