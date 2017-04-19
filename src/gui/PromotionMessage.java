package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chess.ColorPiece;
import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import rule.BishopRule;
import rule.KnightRule;
import rule.QueenRule;
import rule.RookRule;

public class PromotionMessage extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3708766205764814884L;
	Piece piece;
	Piece pawn;
	CreateButton create = new CreateButton();
	JButton btnQueen, btnRook, btnBishop, btnKnight;

	public PromotionMessage(Piece pawn) {
		this.pawn = pawn;
		setLayout(new BorderLayout());
		add(new JLabel("Chon quan co muon tro thanh"), BorderLayout.NORTH);

		JPanel pnBody = new JPanel();
		pnBody.setLayout(new GridLayout(1, 4, 5, 5));
		// if (pawn.getColor() == ColorPiece.WHITE) {
		// btnQueen = create.createButton("image\\whitequeen.png");
		// btnRook = create.createButton("image\\whiterook.png");
		// btnBishop = create.createButton("image\\whitebishop.png");
		// btnKnight = create.createButton("image\\whiteknight.png");
		// } else if (pawn.getColor() == ColorPiece.BLACK) {
		btnQueen = new JButton(new ImageIcon("image\\blackqueen.png"));
		btnRook = new JButton(new ImageIcon("image\\blackrook.png"));
		btnBishop = new JButton(new ImageIcon("image\\blackbishop.png"));
		btnKnight = new JButton(new ImageIcon("image\\blackknight.png"));
		// }

		pnBody.add(btnQueen);
		pnBody.add(btnRook);
		pnBody.add(btnBishop);
		pnBody.add(btnKnight);

		add(pnBody, BorderLayout.CENTER);

	}

	// public static void main(String[] args) {
	// JFrame f = new JFrame("test");
	// ChessBoard ob = new ChessBoard();
	// // Piece pawn = new Piece(new Location(1, 7), PieceType.PAWN, 'C', new
	// // PawnRule(ob, new Location(1, 7)),ColorPiece.BLACK, 10,
	// // "blackpawn.png");
	//// f.add(new PromotionMessage());
	// f.pack();
	// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// f.setLocationRelativeTo(null);
	// f.setVisible(true);
	// }

	ChessBoard ob;

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnQueen) {
			if (pawn.getColor() == ColorPiece.WHITE) {
				piece = new Piece(pawn.getLocation(), PieceType.QUEEN, 'Q', new QueenRule(ob, pawn.getLocation()),
						ColorPiece.WHITE, 90, "whitequeen.png");
			} else if (pawn.getColor() == ColorPiece.BLACK) {
				piece = new Piece(pawn.getLocation(), PieceType.QUEEN, 'Q', new QueenRule(ob, pawn.getLocation()),
						ColorPiece.BLACK, 90, "blackqueen.png");
			}
		}
		if (e.getSource() == btnRook) {
			if (pawn.getColor() == ColorPiece.WHITE) {
				piece = new Piece(pawn.getLocation(), PieceType.ROOK, 'R', new RookRule(ob, pawn.getLocation()),
						ColorPiece.WHITE, 50, "whiterook.png");
			} else if (pawn.getColor() == ColorPiece.BLACK) {
				piece = new Piece(pawn.getLocation(), PieceType.ROOK, 'R', new RookRule(ob, pawn.getLocation()),
						ColorPiece.BLACK, 50, "blackrook.png");
			}
		}

		if (e.getSource() == btnBishop) {
			if (pawn.getColor() == ColorPiece.WHITE) {
				piece = new Piece(pawn.getLocation(), PieceType.BISHOP, 'B', new BishopRule(ob, pawn.getLocation()),
						ColorPiece.WHITE, 32, "whitebishop.png");
			} else if (pawn.getColor() == ColorPiece.BLACK) {
				piece = new Piece(pawn.getLocation(), PieceType.BISHOP, 'B', new BishopRule(ob, pawn.getLocation()),
						ColorPiece.BLACK, 32, "blackbishop.png");
			}
		}

		if (e.getSource() == btnKnight) {
			if (pawn.getColor() == ColorPiece.WHITE) {
				piece = new Piece(pawn.getLocation(), PieceType.KNIGHT, 'N', new KnightRule(ob, pawn.getLocation()),
						ColorPiece.WHITE, 30, "whitequeen.png");
			} else if (pawn.getColor() == ColorPiece.BLACK) {
				piece = new Piece(pawn.getLocation(), PieceType.KNIGHT, 'N', new KnightRule(ob, pawn.getLocation()),
						ColorPiece.BLACK, 30, "blackqueen.png");
			}
		}
	}

}
