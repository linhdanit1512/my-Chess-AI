package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import chess.Alliance;
import chess.Piece;
import chess.PieceType;
import core.Location;

public class PawnRule extends Rule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2025447225850627174L;

	public PawnRule(Observable ob, Location location) {
		super(ob, location);
		ob.addObserver(this);
	}

	public PawnRule(Observable ob, Rule rule, Location location) {
		super(ob, rule, location);
		ob.addObserver(this);
	}

	@Override
	public List<Location> getNormalRule() {
		List<Location> listLocation = new ArrayList<Location>();
		Piece[][] pieces = board.pieceBoard;
		int x = location.getX();
		int y = location.getY();
		if (pieces[x][y] != null && pieces[x][y].getType() == PieceType.PAWN) {
			int param = 0;
			/**
			 * neu la quan den thi di theo chieu tu tren xuong (x tang dan), neu
			 * la quan trang thi di theo chieu tu duoi len (x giam dan)
			 */
			if (pieces[x][y].getAlliance() == Alliance.BLACK)
				param = 1;
			else if (pieces[x][y].getAlliance() == Alliance.WHITE)
				param = -1;
			// co the an quan ben phai
			if (checkValidTile(x + param, y + 1) == 2)
				listLocation.add(new Location(x + param, y + 1));
			// co the an quan ben trai
			if (checkValidTile(x + param, y - 1) == 2)
				listLocation.add(new Location(x + param, y - 1));
			// co the di thang
			if (checkValidTile(x + param, y) == 1) {
				listLocation.add(new Location(x + param, y));
				// neu la buoc di dau tien thi co the di 2 buoc neu 2 o
				// truoc no trong
				if (pieces[x][y].getMove() == 0 && checkValidTile(x + param + param, y) == 1)
					listLocation.add(new Location(x + param + param, y));
			}
			if (premove != null) {
				Location loca = passant();
				if (loca != null)
					listLocation.add(loca);
			}
			return listLocation;
		}
		return null;
	}

	/**
	 * 
	 * @param premove:
	 *            nuoc co vua di Bat chot qua duong
	 */
	public Location passant() {
		if (premove != null) {
			if (premove.getPieceFrom().getType() == PieceType.PAWN) {
				int param = 0;
				if ((premove.getFrom().getX() == 1 && premove.getTo().getX() == 3)) {
					param = -1;
				}
				if (premove.getFrom().getX() == 6 && premove.getTo().getX() == 4) {
					param = 1;
				}
				int x = premove.getTo().getX();
				int y = premove.getTo().getY();
				if (param != 0) {
					Location left, right;
					if (y > 0) {
						left = new Location(x, y - 1);
						if (left.equals(this.location)) {
							Piece pieceLeft = board.getPieceAt(left);
							if (pieceLeft != null) {
								if (pieceLeft.getAlliance() != premove.getPieceFrom().getAlliance()) {
									if (pieceLeft.getType() == PieceType.PAWN) {
										Location loca = new Location(x + param, y);
										System.out.println("left location: " + left);
										return loca;
									}
								}
							}
						}
					}
					if (y < board.pieceBoard[0].length - 1) {
						right = new Location(x, y + 1);
						if (right.equals(this.location)) {
							Piece pieceRight = board.getPieceAt(right);
							if (pieceRight != null) {
								if (pieceRight.getAlliance() != premove.getPieceFrom().getAlliance()) {
									if (pieceRight.getType() == PieceType.PAWN) {
										Location loca = new Location(x + param, y);
										System.out.println("right location: " + right);
										return loca;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param location:
	 *            vi tri hien tai cua quan chot truoc khi di
	 * @return co phai la thanh hau hay khong
	 */
	public boolean pawnPromotion(Location from) {
		if (from != null) {
			int x = from.getX();
			int y = from.getY();
			if (board.pieceBoard[x][y].getType() == PieceType.PAWN) {
				if (board.pieceBoard[x][y].getAlliance() == Alliance.BLACK && x == 6)
					return true;
				else if (board.pieceBoard[x][y].getAlliance() == Alliance.WHITE && x == 1)
					return true;
			}
		}
		return false;
	}

	@Override
	public List<Location> getAllLocationControl() {
		List<Location> listLocation = new ArrayList<Location>();
		Piece[][] pieces = board.pieceBoard;
		int x = location.getX();
		int y = location.getY();
		if (pieces[x][y] != null && pieces[x][y].getType() == PieceType.PAWN) {
			int param = 0;
			/**
			 * neu la quan den thi di theo chieu tu tren xuong (x tang dan), neu
			 * la quan trang thi di theo chieu tu duoi len (x giam dan)
			 */
			if (pieces[x][y].getAlliance() == Alliance.BLACK)
				param = 1;
			else if (pieces[x][y].getAlliance() == Alliance.WHITE)
				param = -1;
			// co the chieu quan ben phai
			if (checkValidTile(x + param, y + 1) == 2 || checkValidTile(x + param, y + 1) == 3)
				listLocation.add(new Location(x + param, y + 1));
			// co the chieu quan ben trai
			if (checkValidTile(x + param, y - 1) == 2 || checkValidTile(x + param, y - 1) == 3)
				listLocation.add(new Location(x + param, y - 1));
			return listLocation;
		}
		return null;
	}

}
