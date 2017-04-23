package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import chess.Piece;
import chess.PieceType;
import core.Location;

public class KingRule extends Rule {
	private static final long serialVersionUID = -4255884816556635557L;

	public KingRule(Observable ob, Location location) {
		super(ob, location);
		ob.addObserver(this);
	}

	public KingRule(Observable ob, Rule rule, Location location) {
		super(ob, rule, location);
		ob.addObserver(this);
	}

	public List<Location> getRules() {
		List<Location> listLocation = new ArrayList<Location>();
		int x = location.getX();
		int y = location.getY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (checkValidTile(i, j) != 0 && checkValidTile(i, j) != 3) {
					listLocation.add(new Location(i, j));
				}
			}
		}
		return listLocation;
	}

	/**
	 * 
	 * @param location:
	 *            vi tri quan vua
	 * @return tra ve cac vi tri ma quan co thuc su co the di toi tu vi tri
	 *         location
	 */
	@Override
	public List<Location> getRealLocationCanMove() {
		if (location != null) {
			if (board.getPieceAt(location) != null && board.getPieceAt(location).getType() == PieceType.KING) {
				Piece king = board.getPieceAt(location);
				// chua gia tri tra ve
				List<Location> result = new ArrayList<Location>();
				// chua cac gia tri di theo quy tac
				List<Location> tmp = new ArrayList<Location>();
				tmp = getRules();
				for (Location l : tmp) {
					// neu o vi tri do co ko bi quan dich khong che thi co
					// the di
					if (getEnemyControlAtLocation(location, king.getColor()) == null
							|| getEnemyControlAtLocation(location, king.getColor()).isEmpty()) {
						result.add(l);
					}

				}
				// neu vua co them luat nhap thanh
				if (rule != null) {
					// neu vua chua di chuyen thi kiem tra nhap thanh
					if (king.getMove() == 0) {
						// neu vua ko bi chieu thi kiem tra nhap thanh
						List<Location> list = ((Castling) rule).getRealLocationCanMove();
						if (list != null && !list.isEmpty())
							result.addAll(list);
					}
				}
				return result;
			}
		}
		return null;
	}

	@Override
	public List<Location> getNormalRule() {
		return getRules();

	}

	@Override
	public List<Location> getAllLocationControl() {
		List<Location> listLocation = new ArrayList<Location>();
		int x = location.getX();
		int y = location.getY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (checkValidTile(i, j) != 0) {
					listLocation.add(new Location(i, j));
				}
			}
		}
		return listLocation;
	}

}
