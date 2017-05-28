package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import chess.Piece;
import chess.PieceType;
import core.Location;

public class Castling extends Rule {

	public Castling(Observable ob, Location location) {
		super(ob, location);
		ob.addObserver(this);
	}

	public Castling(Observable ob, Rule rule, Location location) {
		super(ob, rule, location);
		ob.addObserver(this);
	}

	private static final long serialVersionUID = 4326765764289424274L;

	@Override
	public List<Location> getNormalRule() {
		return null;
	}

	@Override
	public List<Location> getRealLocationCanMove() {
		return castling(board.getPieceAt(location));
	}

	/**
	 * 
	 * @param king:
	 *            quan vua can xet
	 * @return Cac vi tri co the nhap thanh. Co the co 2 gia tri cho 1 quan vua.
	 *         neu nhap thanh ve ben co toa do Y nho hon la nhap thanh xa (ben
	 *         cach quan xe xa hon) neu nhap thanh ve ben co toa do Y lon hon la
	 *         nhap thanh gan. Tra ve null || empty khi khong the nhap thanh
	 */
	public List<Location> castling(Piece king) {
		if (king != null) {
			if (king.getMove() == 0) {
				// kiem tra co phai vua hay ko
				if (king.getType().equals(PieceType.KING)) {
					Piece[][] pieces = board.pieceBoard;
					int x = king.getLocation().getX();
					List<Piece> liTmp = new ArrayList<Piece>();
					// lay danh sach cac quan dich dang chieu vua
					// neu vua dang bi chieu thi ko duoc nhap thanh
					liTmp.addAll(getEnemyControlAtLocation(king.getLocation(), king.getAlliance()));
					if (liTmp != null && !liTmp.isEmpty()) {
						return null;
					}
					// chua ket qua tra ve
					List<Location> list = new ArrayList<Location>();
					// kiem tra xem giua vua va xe ben trai phai tro^'ng

					// nhap thanh xa

					if (pieces[x][1] == null && pieces[x][2] == null && pieces[x][3] == null) {
						// kiem tra la quan xe phai chua di chuyen va cung mau
						// voi
						// vua
						if (pieces[x][0] != null) {
							if (PieceType.ROOK.equals(pieces[x][0].getType())
									&& pieces[x][0].getAlliance() == king.getAlliance()
									&& pieces[x][0].getMove() == 0) {
								/**
								 * kiem tra vi tri ma quan vua di qua (2 o ben
								 * trai no) co bi chieu hay ko, neu bi chieu thi
								 * bo qua, ko thi add vao list
								 */
								liTmp.clear();
								liTmp.addAll(getEnemyControlAtLocation(new Location(x, 2), king.getAlliance()));
								liTmp.addAll(getEnemyControlAtLocation(new Location(x, 3), king.getAlliance()));
								if (liTmp == null || liTmp.isEmpty()) {
									list.add(new Location(x, 2));
								}
							}
						}
					}

					// nhap thanh gan

					if (pieces[x][5] == null && pieces[x][6] == null) {
						if (pieces[x][7] != null) {
							if (PieceType.ROOK.equals(pieces[x][7].getType())
									&& pieces[x][7].getAlliance() == king.getAlliance()
									&& pieces[x][7].getMove() == 0) {
								/**
								 * kiem tra vi tri ma quan vua di qua (2 o ben
								 * trai no) co bi chieu hay ko, neu bi chieu thi
								 * bo qua, ko thi add vao list
								 */
								liTmp.clear();
								liTmp.addAll(getEnemyControlAtLocation(new Location(x, 5), king.getAlliance()));
								liTmp.addAll(getEnemyControlAtLocation(new Location(x, 6), king.getAlliance()));
								if (liTmp == null || liTmp.isEmpty()) {
									list.add(new Location(x, 6));
								}
							}
						}
					}
					return list;
				}
			}
		}
		return null;
	}

	@Override
	public List<Location> getAllLocationControl() {
		// TODO Auto-generated method stub
		return null;
	}
}
