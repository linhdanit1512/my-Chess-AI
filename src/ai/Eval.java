package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import chess.Piece;
import core.ChessBoard;
import core.Location;

public class Eval implements Observer {
	int[][] matrix = new int[8][8];
	int[][] orther_matrix = new int[8][8];
	public ChessBoard board;
	Observable ob;
	int param = 30;

	public Eval(Observable ob) {
		super();
		ob.addObserver(this);
		this.ob = ob;
		this.board = (ChessBoard) ob;
	}

	public static void main(String[] args) {
		ChessBoard b = new ChessBoard();
		Eval e = new Eval(b);
		e.print(1);
		System.out.println();
		e.print(2);
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
				if (ortherPieces != null && !ortherPieces.isEmpty()) {
					for (Piece p : ortherPieces) {
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

	/**
	 * 
	 * Tính điểm tại vị trí location theo alliance
	 * 
	 * @param location
	 * @param alliance
	 * @return điểm
	 */
	public int evaluationPosition(Location location, int alliance) {
		int evalua = 0;
		/*
		 * điểm cơ bản của quân cờ tại vị trí location
		 */
		if (board.getPieceAt(location) != null) {
			evalua += board.getPieceAt(location).getScore();
		}
		/*
		 * danh sách các quân cờ đang khống chế vị trí location
		 * 
		 * nếu quân cờ cùng màu thì cộng, khác thì trừ
		 * 
		 */
		List<Piece> list = board.getKing().get(alliance).getRule().getPieceControlAt(location);
		List<Piece> l1 = new ArrayList<>();
		List<Piece> l2 = new ArrayList<>();
		for (Piece piece : list) {
			if (piece.getAlliance() == alliance) {
				l1.add(piece);
			} else {
				l2.add(piece);
			}
		}
		Comparator<Piece> comp = new Comparator<Piece>() {

			@Override
			public int compare(Piece p1, Piece p2) {
				int s1 = evalPiece(p1) + p1.getScore();
				int s2 = evalPiece(p2) + p2.getScore();
				if (s1 > s2)
					return 1;
				else if (s1 < s2)
					return -1;
				return 0;
			}
		};
		Collections.sort(l1, comp);
		Collections.sort(l2, comp);
		if (l1.size() < l2.size()) {
			/**
			 * neu nhu la luot cua minh thi quan ben minh bi an het nhung quan
			 * doi phuong thi ko bi an het
			 */
			if (board.getPlayer() == alliance) {
				for (int i = 0; i < l1.size(); i++) {
					evalua -= evalPiece(l1.get(i)) + l1.get(i).getScore();
					evalua += evalPiece(l2.get(i)) + l2.get(i).getScore();
				}
			}
			/**
			 * neu nhu la luot cua doi phuong thi no di truoc an luon quan cua
			 * minh roi, noi thi hoi khó hiểu nhưng cứ thử
			 * 
			 * ví dụ như có 2 quân mình và 2 quân địch có thể ăn nhau, thằng kia
			 * đi trước nó ăn thì mình chỉ còn 1 quân ăn lại, do đó nó sẽ còn 1
			 * quân ko bị ăn, nên ô đó nếu đi tới thì điểm nó sẽ dở hơn (chỉ tốt
			 * hơn khi mình ko lỗ nhờ ăn được quân quan trọng thôi
			 * 
			 */
			else {
				for (int i = 0; i < l1.size() - 1; i++) {
					evalua -= evalPiece(l1.get(i)) + l1.get(i).getScore();
					evalua += evalPiece(l2.get(i)) + l2.get(i).getScore();
				}
				for (int i = l1.size() - 1; i < l2.size(); i++) {
					evalua -= evalPiece(l2.get(i)) + l2.get(i).getScore();
				}
			}
		}
		/**
		 * tương tự như trường hợp ở trên nhưng nó ngược lại
		 */
		else if (l1.size() > l2.size()) {
			if (board.getPlayer() == alliance) {
				for (int i = 0; i < l2.size() - 1; i++) {
					evalua -= evalPiece(l1.get(i)) + l1.get(i).getScore();
					evalua += evalPiece(l2.get(i)) + l2.get(i).getScore();
				}
				for (int i = l2.size() - 1; i < l2.size(); i++) {
					evalua += evalPiece(l2.get(i)) + l2.get(i).getScore();
				}
			} else {
				for (int i = 0; i < l2.size(); i++) {
					evalua -= evalPiece(l1.get(i)) + l1.get(i).getScore();
					evalua += evalPiece(l2.get(i)) + l2.get(i).getScore();
				}
			}
		}
		/**
		 * nếu bằng nhau thì thằng nào ăn trước nó sẽ còn lại 1 quân (nếu nó ăn)
		 */
		else {
			for (int i = 0; i < l2.size(); i++) {
				evalua -= evalPiece(l1.get(i)) + l1.get(i).getScore();
				evalua += evalPiece(l2.get(i)) + l2.get(i).getScore();
			}
			if (board.getPlayer() == alliance) {
				evalua += evalPiece(l1.get(l1.size() - 1)) + l1.get(l1.size() - 1).getScore();
			} else {
				evalua -= evalPiece(l2.get(l2.size() - 1)) + l2.get(l2.size() - 1).getScore();
			}
		}
		/**
		 * tính thêm phần điểm nếu vị trí này thuộc các đường có thể chiếu tới
		 * vua đối phương
		 */
		Map<Integer, Piece> mapking = board.getKing();
		for (Map.Entry<Integer, Piece> entry : mapking.entrySet()) {
			Piece king = entry.getValue();
			/*
			 * vua cua dich
			 */

			if (king.getAlliance() != alliance) {
				if (king.getRule().checkCross(location, king.getLocation())) {
					evalua += param;

				} else if (king.getRule().checkHorizontal(location, king.getLocation())) {
					evalua += param;

				} else if (king.getRule().checkVertical(location, king.getLocation())) {
					evalua += param;
				}
			}
			/*
			 * vua cua ta
			 */
			else {

			}
		}
		return evalua;
	}

	public void print(int alliance) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(evaluationPosition(new Location(i, j), alliance) + "\t");
			}
			System.out.println();
		}
	}

	public List<Piece> getPiecesDependOnLocation(Location location) {
		if (location == null)
			return null;
		List<Piece> list = new ArrayList<>();
		int x = location.getX();
		int y = location.getY();
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
		this.ob = o;
		this.board = (ChessBoard) o;
	}
}
