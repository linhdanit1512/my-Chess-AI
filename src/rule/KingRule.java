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

	private List<Location> getRules() {
		List<Location> listLocation = new ArrayList<Location>();
		int x = location.getX();
		int y = location.getY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (checkValidTile(i, j) == 1 || checkValidTile(i, j) == 2) {
					listLocation.add(new Location(i, j));
				}
			}
		}
		return listLocation;
	}

	/**
	 * 
	 * @return tra ve cac vi tri ma quan co thuc su co the di toi tu vi tri
	 *         location
	 */
	// @Override
	// public List<Location> getRealLocationCanMove() {
	// if (location != null) {
	// if (board.getPieceAt(location) != null &&
	// board.getPieceAt(location).getType() == PieceType.KING) {
	// Piece king = board.getPieceAt(location);
	// // chua gia tri tra ve
	// List<Location> result = new ArrayList<Location>();
	// // chua cac gia tri di theo quy tac
	// List<Location> tmp = new ArrayList<Location>();
	// tmp.addAll(getRules());
	// List<Piece> listEnemy = getEnemyControlAtLocation(location,
	// king.getAlliance());
	// /**
	// * neu quan vua bi chieu
	// */
	// if (listEnemy != null && listEnemy.size() > 0) {
	// for (Piece p : listEnemy) {
	// for (Location l : tmp) {
	// List<Piece> enemy = getEnemyControlAtLocation(l, king.getAlliance());
	// if ((enemy == null) || enemy.isEmpty()) {
	// if (!checkStrength(l, location, p.getLocation()))
	// result.add(l);
	// }
	// }
	// }
	// return result;
	// }
	// /**
	// * neu quan vua ko bi chieu thi set binh thuong
	// */
	// for (Location l : tmp) {
	// // neu o vi tri do co ko bi quan dich khong che thi co
	// // the di
	// List<Piece> enemy = getEnemyControlAtLocation(l, king.getAlliance());
	// if ((enemy == null) || enemy.isEmpty()) {
	// result.add(l);
	// }
	// }
	// // neu vua co them luat nhap thanh
	// if (rule != null) {
	// // neu vua chua di chuyen thi kiem tra nhap thanh
	// if (king.getMove() == 0) {
	// // neu vua ko bi chieu thi kiem tra nhap thanh
	// List<Location> list = ((Castling) rule).getRealLocationCanMove();
	// if (list != null && !list.isEmpty())
	// result.addAll(list);
	// }
	// }
	// return result;
	// }
	// }
	// return null;
	// }
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
				List<Location> temp = new ArrayList<>();
				for (Location l : tmp) {
					// neu o vi tri do co ko bi quan dich khong che thi co
					// the di
					List<Piece> enemy = getEnemyControlAtLocation(l, king.getAlliance());
					if (enemy == null || enemy.isEmpty()) {
						temp.add(l);
					}

				}
				List<Piece> kingenemy = getEnemyControlAtLocation(location, king.getAlliance());
				if (kingenemy != null && kingenemy.size() > 0) {
					for (Location l : temp) {
						for (Piece enemy : kingenemy) {
							if (!checkStrength(l, location, enemy.getLocation()))
								result.add(l);
						}
					}
				}else{
					result.addAll(temp);
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
		return getRealLocationCanMove();

	}

	/**
	 * kiem tra 3 o l1 l2 l3 co tao thanh duong thang ko
	 * 
	 * @param l1
	 * @param l2
	 * @param l3
	 * @return
	 */
	private boolean checkStrength(Location l1, Location l2, Location l3) {
		if (checkCross(l1, l2) && checkCross(l1, l3) && checkCross(l2, l3)) {
			return true;
		}
		if (checkHorizontal(l1, l2) && checkHorizontal(l2, l3)) {
			return true;
		}
		if (checkVertical(l1, l2) && checkVertical(l2, l3)) {
			return true;
		}
		return false;
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
