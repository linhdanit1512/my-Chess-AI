package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import chess.ColorPiece;
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
			if (pieces[x][y].getColor() == ColorPiece.BLACK)
				param = 1;
			else if (pieces[x][y].getColor() == ColorPiece.WHITE)
				param = -1;
			// co the an quan ben phai
			if (checkValidTile( x + param, y + 1) == 2)
				listLocation.add(new Location(x + param, y + 1));
			// co the an quan ben trai
			if (checkValidTile( x + param, y - 1) == 2)
				listLocation.add(new Location(x + param, y - 1));
			// co the di thang
			if (checkValidTile( x + param, y) == 1) {
				listLocation.add(new Location(x + param, y));
				// neu la buoc di dau tien thi co the di 2 buoc neu 2 o
				// truoc no trong
				if (pieces[x][y].getMove() == 0 && checkValidTile( x + param + param, y) == 1)
					listLocation.add(new Location(x + param + param, y));
			}
			passant(location);
			return listLocation;
		}
		return null;
	}

	/**
	 * 
	 * @param location
	 *            Bat chot qua duong
	 */
	public void passant(Location location) {

	}

	/**
	 * 
	 * @param location
	 * @return co phai la thanh hau hay khong
	 */
	public boolean pawnPromotion(Location location) {
		int x = location.getX();
		int y = location.getY();
		if (board.pieceBoard[x][y].getType() == PieceType.PAWN) {
			if (board.pieceBoard[x][y].getColor() == ColorPiece.BLACK && x == 7)
				return true;
			else if (board.pieceBoard[x][y].getColor() == ColorPiece.WHITE && x == 0)
				return true;
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
			if (pieces[x][y].getColor() == ColorPiece.BLACK)
				param = 1;
			else if (pieces[x][y].getColor() == ColorPiece.WHITE)
				param = -1;
			// co the an quan ben phai
			if (checkValidTile( x + param, y + 1) == 2 || checkValidTile( x + param, y + 1) == 3)
				listLocation.add(new Location(x + param, y + 1));
			// co the an quan ben trai
			if (checkValidTile( x + param, y - 1) == 2 || checkValidTile( x + param, y - 1) == 3)
				listLocation.add(new Location(x + param, y - 1));
			passant(location);
			return listLocation;
		}
		return null;
	}

}
