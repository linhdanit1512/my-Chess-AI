package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import chess.Piece;
import core.ChessBoard;
import core.Location;

public class Eval implements Observer {
	int[][] matrix = new int[8][8];
	int[][] orther_matrix = new int[8][8];
	ChessBoard board;

	public Eval(Observable ob) {
		super();
		ob.addObserver(this);
		this.board = (ChessBoard) ob;
	}

	public static void main(String[] args) {
		ChessBoard b = new ChessBoard();
		Eval e = new Eval(b);
		System.out
				.println(b.getPieceAt(new Location(0, 1)) + " score " + e.evalPiece(b.getPieceAt(new Location(0, 1))));
		System.out
				.println(b.getPieceAt(new Location(1, 1)) + " score " + e.evalPiece(b.getPieceAt(new Location(1, 1))));
		System.out
				.println(b.getPieceAt(new Location(0, 0)) + " score " + e.evalPiece(b.getPieceAt(new Location(0, 0))));

	}

	public int evalPiece(Piece piece) {
		if (piece != null) {
			int score = 0;
			Location l = piece.getLocation();
			Piece myPiece = board.getPieceAt(l);
			if (myPiece != null) {
				int maxEnemy = 0;
				/*
				 * diem co ban, neu nhu co the di toi 1 o bat ki thi tang len 1
				 * 
				 */
				List<Location> list = myPiece.getRule().getRealLocationCanMove();
				for (Location loca : list) {
					Piece p = board.getPieceAt(loca);
					if (p != null) {
						maxEnemy = p.getScore();
					} else {
						score += 1;
					}
				}
				/*
				 * cac o mo rong cua no
				 */
				List<Piece> ortherPieces = getPiecesDependOnLocation(l);
				if (ortherPieces != null) {
					for (Piece p : ortherPieces) {
						System.out.println(p.getLinkImg());
						/*
						 * neu quan co cung mau
						 */
						if (p.getAlliance() == myPiece.getAlliance()) {
							/*
							 * neu nhu quan dang set nam giua 2 quan ta va dich
							 * thi diem so tinh = quan dang set - quan doi
							 * phuong
							 */
							Piece ene = p.getRule().checkBeetween(myPiece, p);
							if (ene != null) {
								System.out.println("abssss");
								score += -myPiece.getScore() + ene.getScore();
							}
						}
					}
				}

				score += maxEnemy;
			}
			return score;
		}
		return 0;
	}

	public int evaluationPosition(Location location){
		int evalua = 0;
		List<Piece> list = getPiecesDependOnLocation(location);
		for (Piece piece : list) {
			
			
		}
		return evalua;
	}

	public List<Piece> getPiecesDependOnLocation(Location location) {
		if (location == null)
			return null;
		List<Piece> list = new ArrayList<>();
		int x = location.getX();
		System.out.println("x" + x);
		int y = location.getY();
		System.out.println("y" + y);
		// kiem tra phia ben trai
		for (int i = 1; i <= y; i++) {
			if (board.isHasPiece(x, y - i)) {
				list.add(board.getPieceAt(new Location(x, y - i)));
				break;
			}
		}
		// kiem tra phia ben phai
		for (int i = 1; i < 8 - y; i++) {
			if (board.isHasPiece(x, y + i)) {
				list.add(board.getPieceAt(new Location(x, y + i)));
				break;
			}
		}
		// kiem tra ben tren
		for (int i = 1; i <= x; i++) {
			if (board.isHasPiece(x - i, y)) {
				list.add(board.getPieceAt(new Location(x - i, y)));
				break;
			}
		}
		// kiem tra phia duoi
		for (int i = 1; i < 8 - x; i++) {
			if (board.isHasPiece(x + i, y)) {
				list.add(board.getPieceAt(new Location(x + i, y)));
				break;
			}
		}

		// kiem tra cheo tren trai
		for (int i = 1; i <= x; i++) {
			if (board.isHasPiece(x - i, y - i)) {
				list.add(board.getPieceAt(new Location(x - i, y - y)));
				break;
			}
		}
		// kiem tra cheo tren phai
		for (int i = 1; i <= x; i++) {
			if (board.isHasPiece(x - i, y + i)) {
				list.add(board.getPieceAt(new Location(x - i, y + i)));
				break;
			}
		}
		// kiem tra cheo duoi trai
		for (int i = 1; i < 8 - x; i++) {
			if (board.isHasPiece(x + i, y - i)) {
				list.add(board.getPieceAt(new Location(x + i, y - i)));
				break;
			}
		}
		// kiem tra cheo duoi phai
		for (int i = 1; i < 8 - x; i++) {
			if (board.isHasPiece(x + i, y + i)) {
				list.add(board.getPieceAt(new Location(x + i, y + i)));
				break;
			}
		}
		return list;
	}

	@Override
	public void update(Observable o, Object arg) {
		this.board = (ChessBoard) o;
	}
}
