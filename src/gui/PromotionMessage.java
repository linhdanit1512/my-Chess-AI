package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import action.Move;
import chess.Alliance;
import chess.Piece;
import chess.PieceType;
import controller.BoardController;
import rule.BishopRule;
import rule.KnightRule;
import rule.QueenRule;
import rule.RookRule;

public class PromotionMessage extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3708766205764814884L;
	Move move;
	DecoButton create = new DecoButton();
	public JButton btnQueen, btnRook, btnBishop, btnKnight;
	BoardController control;

	public PromotionMessage(BoardController control, Move move) {
		this.move = move;
		this.control = control;
		setLayout(new BorderLayout());
		JLabel lbl = new JLabel("Chá»�n quÃ¢n cá»� muá»‘n phong thÃ nh");
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lbl, BorderLayout.NORTH);
		JPanel pnBody = new JPanel();
		pnBody.setLayout(new GridLayout(1, 4, 5, 5));
		Piece pawn = move.getPieceFrom();
		if (pawn.getAlliance() == Alliance.WHITE) {
			btnQueen = create.createButton("image\\whitequeen.png");
			btnRook = create.createButton("image\\whiterook.png");
			btnBishop = create.createButton("image\\whitebishop.png");
			btnKnight = create.createButton("image\\whiteknight.png");
		} else if (pawn.getAlliance() == Alliance.BLACK) {
			btnQueen = create.createButton("image\\blackqueen.png");
			btnRook = create.createButton("image\\blackrook.png");
			btnBishop = create.createButton("image\\blackbishop.png");
			btnKnight = create.createButton("image\\blackknight.png");
		}
		btnBishop.addActionListener(this);
		btnKnight.addActionListener(this);
		btnQueen.addActionListener(this);
		btnRook.addActionListener(this);
		pnBody.add(btnQueen);
		pnBody.add(btnRook);
		pnBody.add(btnBishop);
		pnBody.add(btnKnight);

		add(pnBody, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(control.getView());
//		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Piece pawn = move.getPieceFrom();
		if (e.getSource() == btnQueen) {
			if (pawn.getAlliance() == Alliance.WHITE) {
				pawn = new Piece(pawn.getLocation(), PieceType.QUEEN, 'Q',
						new QueenRule(pawn.getRule().getOb(), pawn.getLocation()), Alliance.WHITE, 90,
						"whitequeen.png");
			} else if (pawn.getAlliance() == Alliance.BLACK) {
				pawn = new Piece(pawn.getLocation(), PieceType.QUEEN, 'Q',
						new QueenRule(pawn.getRule().getOb(), pawn.getLocation()), Alliance.BLACK, 90,
						"blackqueen.png");
			}
		} else if (e.getSource() == btnRook) {
			if (pawn.getAlliance() == Alliance.WHITE) {
				pawn = new Piece(pawn.getLocation(), PieceType.ROOK, 'R',
						new RookRule(pawn.getRule().getOb(), pawn.getLocation()), Alliance.WHITE, 50, "whiterook.png");
			} else if (pawn.getAlliance() == Alliance.BLACK) {
				pawn = new Piece(pawn.getLocation(), PieceType.ROOK, 'R',
						new RookRule(pawn.getRule().getOb(), pawn.getLocation()), Alliance.BLACK, 50, "blackrook.png");
			}
		}

		else if (e.getSource() == btnBishop) {
			if (pawn.getAlliance() == Alliance.WHITE) {
				pawn = new Piece(pawn.getLocation(), PieceType.BISHOP, 'B',
						new BishopRule(pawn.getRule().getOb(), pawn.getLocation()), Alliance.WHITE, 32,
						"whitebishop.png");
			} else if (pawn.getAlliance() == Alliance.BLACK) {
				pawn = new Piece(pawn.getLocation(), PieceType.BISHOP, 'B',
						new BishopRule(pawn.getRule().getOb(), pawn.getLocation()), Alliance.BLACK, 32,
						"blackbishop.png");
			}
		}

		else if (e.getSource() == btnKnight) {
			if (pawn.getAlliance() == Alliance.WHITE) {
				pawn = new Piece(pawn.getLocation(), PieceType.KNIGHT, 'N',
						new KnightRule(pawn.getRule().getOb(), pawn.getLocation()), Alliance.WHITE, 30,
						"whitequeen.png");
			} else if (pawn.getAlliance() == Alliance.BLACK) {
				pawn = new Piece(pawn.getLocation(), PieceType.KNIGHT, 'N',
						new KnightRule(pawn.getRule().getOb(), pawn.getLocation()), Alliance.BLACK, 30,
						"blackqueen.png");
			}
		}
		pawn.setMove(0);
		move.setPiecePromotion(pawn);
		setVisible(false);
		control.setPricePromotion(pawn);
		control.setFrom(move.getFrom());
		control.setCheck(true);
		control.move(move.getTo());
		System.out.println(pawn);
	}

	public Piece getPiece() {
		return move.getPiecePromotion();
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
}
