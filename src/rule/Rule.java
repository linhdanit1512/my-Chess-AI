package rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import chess.Alliance;
import chess.Piece;
import chess.PieceType;
import core.ChessBoard;
import core.ChessGoalTest;
import core.Location;

public abstract class Rule implements Serializable, Observer {

	private static final long serialVersionUID = 8920250216050470567L;
	Observable ob;
	ChessBoard board;
	protected Rule rule;
	Location location;

	public Rule(Observable ob, Rule rule, Location location) {
		this.ob = ob;
		this.rule = rule;
		this.board = (ChessBoard) ob;
		this.location = location;
	}

	public Rule(Observable ob, Location location) {
		this.ob = ob;
		this.board = (ChessBoard) ob;
		this.location = location;
	}

	/**
	 * 
	 * @param location:
	 *            vi tri dang xet
	 * @return neu vi tri nay co quan co thi no se tim cac nuoc di co ban co the
	 *         di cua quan co tai vi tri do.
	 * 
	 *         phuong thuc nay duoc su dung chung cho cac truong hop thong
	 *         thuong, va duoc su dung nhu cac nuoc di cua doi phuong (dung so
	 *         sanh), tuy cac nuoc doi phuong co the ko di duoc, nhung nhung~ o
	 *         nay van la nhung~ o ma no dang khong che.
	 * 
	 *         vi du nhu truong hop quan 2 quan vua, 2 quan vua ko the dung canh
	 *         nhau, nhung do la do do la vung khong che cua quan kia, nen mac
	 *         du ca 2 quan ko the an nhau nhung can phai lay cac nuoc di thong
	 *         thuong de kiem tra viec chung ko the den nhung o ma quan kia
	 *         khong che.
	 * 
	 *         hay truong hop 1 quan xe dang che cho quan vua cua minh, no khong
	 *         the di chuyen ra ngoai duong ma quan kia dang chieu, nhung quan
	 *         vua cung khong the di chuyen toi o ma quan xe nay dang khong che.
	 */
	public abstract List<Location> getNormalRule();

	public abstract List<Location> getAllLocationControl();

	/**
	 * 
	 * @param player:
	 *            toi luot cua nguoi choi player
	 * @return: tra ve 1 map luu tru cac quan co va vi tri cua no co the di
	 *          khien vua het bi chieu.
	 * 
	 * 
	 * 
	 */
	public Map<Piece, List<Location>> getAllListLocationCanMoveWhenCheckmate() {
		// map chua cac gia tri tra ve
		Map<Piece, List<Location>> listResult = new HashMap<Piece, List<Location>>();
		if (board.getPlayer() != 0) {
			// lay quan vua dang bi chieu
			Piece king = board.getKing().get(board.getPlayer());
			// tim cac quan co dang chieu vua vi co the trong 1 luc co nhieu
			// quan cung chieu no
			List<Piece> listEnemy = getEnemyControlAtLocation(king.getLocation(), king.getAlliance());
			if (listEnemy == null)
				return null;
			else if (listEnemy.isEmpty())
				return null;
			int count = listEnemy.size();
			/**
			 * neu dong thoi co 2 quan chieu toi vua thi bat buoc quan vua phai
			 * di chuyen vi cac quan khac ko the cung luc chan duoc tan cong cua
			 * 2 quan.
			 * 
			 * truong hop nay xay ra khi quan vua di chan quan dang sau no chieu
			 * vua, nhung chinh khi no di chuyen thi vao o khac co the chieu
			 * vua. va chi co the co toi da la 2 quan chieu vua 1 luc (khong
			 * tinh truong hop 2 nguoi choi voi nhau ma ca 2 deu ko phat hien ra
			 * vua dang bi chieu ma di quan khac dan den 1 luc co nhieu hon 2
			 * quan chieu vua cung luc)
			 */
			if (count > 1) {
				List<Location> list = ((KingRule) king.getRule()).getRealLocationCanMove();
				listResult.put(king, list);
				return listResult;

			} else {
				/**
				 * khi chi co 1 quan co chieu toi vua
				 */
				// voi moi quan co dang chieu toi vua thi duyet tung o giua no
				// va quan vua
				for (Piece enemy : listEnemy) {
					// lay cac o co nam giua vua va quan co dich va vi tri quan
					// dich
					List<Location> listCheckmateLocat = getDistanceLocation(king.getLocation(), enemy.getLocation());
					listCheckmateLocat.add(enemy.getLocation());

					List<Piece> myPieces = new ArrayList<Piece>();
					if (king.getAlliance() == Alliance.BLACK)
						myPieces = board.listBlackAlliance;
					else if (king.getAlliance() == Alliance.WHITE)
						myPieces = board.listWhiteAlliance;
					for (Piece p : myPieces) {
						List<Location> tmp = new ArrayList<Location>();
						List<Location> myLocatsMove = p.getRule().getNormalRule();
						for (Location l : listCheckmateLocat) {
							if (myLocatsMove.contains(l))
								tmp.add(l);
						}
						if (tmp.size() > 0)
							listResult.put(p, tmp);
					}
				}
			}
		}
		return listResult;
	}

	/**
	 * 
	 * @param location:
	 *            vi tri dang set
	 * @return tra ve cac vi tri ma quan co thuc su co the di toi tu vi tri
	 *         location
	 */
	public List<Location> getRealLocationCanMove() {
		List<Location> result = new ArrayList<Location>();
		if (location != null) {
			Piece piece = board.getPieceAt(location);
			if (piece != null) {
				// neu quan vua dang bi chieu
				if (ChessGoalTest.checkmate(board) && piece.getAlliance() == board.getPlayer()) {
					List<Location> tmp = getAllListLocationCanMoveWhenCheckmate().get(piece);
					if (tmp != null && !tmp.isEmpty())
						result.addAll(tmp);
					return result;
				}
				/**
				 * neu quan co nay dang che cho vua thi khong the di chuyen ra
				 * khoi duong do, nghia la no chi co the di chuyen tu vi tri cua
				 * no toi vi tri cua quan co dich
				 * 
				 */
				else if (checkBetweenKingAndEnemy(piece) != null) {
					List<Location> normalRule = piece.getRule().getNormalRule();
					Piece p = checkBetweenKingAndEnemy(piece);
					if (normalRule.contains(p)) {
						result.addAll(getDistanceLocation(p.getLocation(), location));
						result.add(p.getLocation());
						return result;
					} else if (!normalRule.contains(p)) {
						List<Location> tmp = new ArrayList<Location>();
						List<Location> tmp2 = getDistanceLocation(p.getLocation(), location);
						for (Location lo : normalRule) {
							if (tmp2.contains(lo))
								tmp.add(lo);
						}
						return tmp;
					}
					return null;
				} else {
					result.addAll(getNormalRule());
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @param piece:
	 *            quan co dang xet kiem tra o giua
	 * @return neu quan co dang nam tren duong co the di cua doi phuong va che
	 *         cho vua thi return quan co do cua doi phuong
	 * 
	 *         vi du: X[4,5], V[0,5], -H[7,5]
	 * 
	 *         se tra ve vi tri cua quan Hau la [7,5]
	 * 
	 */
	public Piece checkBetweenKingAndEnemy(Piece piece) {
		if (piece != null) {
			if (board.getKing().containsValue(piece)) {
				return null;
			}
			Location kingLocation = board.getKing().get(piece.getAlliance()).getLocation();
			// cac quan co cua doi phuong co the chieu toi vi tri cua quan
			// co piece
			List<Piece> list = getEnemyControlAtLocation(piece.getLocation(), piece.getAlliance());
			if (list != null && !list.isEmpty()) {
				for (Piece p : list) {

					// neu khoang cach tu vua toi doi phuong > khoang cach
					// tu quan dang xet toi doi phuong thi nghia la quan
					// dang set nam giua vua va quan cua doi phuong

					if (checkCross(kingLocation, piece.getLocation()) && checkCross(kingLocation, p.getLocation())
							&& checkCross(piece.getLocation(), p.getLocation())) {
						if (Math.abs(kingLocation.getX() - p.getLocation().getX()) > Math
								.abs(piece.getLocation().getX() - p.getLocation().getX()))
							if (p.getType() == PieceType.PAWN)
								continue;
							else
								return p;
						else
							continue;
					} else if (checkHorizontal(kingLocation, piece.getLocation())
							&& checkHorizontal(piece.getLocation(), p.getLocation())) {
						if (Math.abs(kingLocation.getY() - p.getLocation().getY()) > Math
								.abs(piece.getLocation().getY() - p.getLocation().getY()))
							return p;
						else
							continue;
					} else if (checkVertical(kingLocation, piece.getLocation())
							&& checkVertical(piece.getLocation(), p.getLocation())) {
						if (Math.abs(kingLocation.getX() - p.getLocation().getX()) > Math
								.abs(piece.getLocation().getX() - p.getLocation().getX()))
							return p;
						else
							continue;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return l1 va l2 nam tren cung 1 duong cheo
	 */
	public boolean checkCross(Location l1, Location l2) {
		return (Math.abs(l1.getX() - l2.getX()) == Math.abs(l1.getY() - l2.getY()));
	}

	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return l1 va l2 cung nam tren 1 hang ngang
	 */
	public boolean checkHorizontal(Location l1, Location l2) {
		return (l1.getX() == l2.getX());
	}

	/**
	 * 
	 * @param l1
	 * @param l2
	 * @return l1 va l2 cung nam tren 1 cot doc
	 */
	public boolean checkVertical(Location l1, Location l2) {
		return l1.getY() == l2.getY();
	}

	/**
	 * 
	 * @param location:
	 *            vi tri can kiem tra
	 * @param color:
	 *            mau cua quan can kiem tra
	 * @return danh sach cac piece cua quan mau color co the toi vi tri location
	 * @see rule.Rule.getAllLocationControl()
	 */
	public List<Piece> getEnemyControlAtLocation(Location location, int color) {
		if (location == null)
			return null;
		List<Piece> result = new ArrayList<Piece>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = board.pieceBoard[i][j];
				// neu vi tri i,j co quan co
				if (piece != null) {
					//
					// neu la quan chot thi no chi co the di thang ma ko the an
					// thang, cho nen o day neu quan co o o^ cung cot voi vi tri
					// location la quan chot thi ko can xet
					//
					// if (piece.getType() == PieceType.PAWN &&
					// piece.getLocation().getY() == location.getY())
					// continue;
					// neu quan co o vi tri i,j co the di toi vi tri location
					// thi add
					if (piece.getRule().getAllLocationControl().contains(location)) {
						if (piece.getAlliance() != color) {
							result.add(piece);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @return lay cac vi tri giua 2 quan co (co duong chieu).
	 * 
	 *         truong hơp quan ma (Knight) khong bi can duong boi cac quan co
	 *         nen khong can tim kiem
	 */
	public List<Location> getDistanceLocation(Location from, Location to) {
		List<Location> list = new ArrayList<Location>();
		int x1 = from.getX();
		int y1 = from.getY();
		int x2 = to.getX();
		int y2 = to.getY();
		// duong cheo \
		if ((x1 < x2 && y1 < y2) || (x1 > x2 && y1 > y2)) {
			for (int i = 1; i < Math.abs(y2 - y1); i++) {
				list.add(new Location(Math.min(x1, x2) + i, Math.min(y1, y2) + i));
			}
		}
		// duong cheo /
		if ((x1 > x2 && y1 < y2) || (x1 < x2 && y1 > y2)) {
			for (int i = 1; i < Math.abs(y2 - y1); i++) {
				list.add(new Location(Math.max(x1, x2) - i, Math.min(y1, y2) + i));
			}
		}
		// cot doc |
		if (x1 == x2) {
			for (int i = 1; i < Math.abs(y1 - y2); i++) {
				list.add(new Location(x1, Math.min(y1, y2) + i));
			}
		}
		// hang ngang ▬▬
		if (y1 == y2) {
			for (int i = 1; i < Math.abs(x1 - x2); i++) {
				list.add(new Location(Math.min(x1, x2) + i, y1));
			}
		}
		return list;
	}

	/**
	 * 
	 * @param location:
	 *            vi tri quan co can xet dang dung
	 * @param paramX:
	 *            toa do x cua o can toi
	 * @param paramY:
	 *            toa do y cua o can toi
	 * @return: 1: o co can toi trong; 2: o co muon toi la quan khac mau; 3: o
	 *          muon toi la quan cung mau; 0: khong the toi
	 */
	public int checkValidTile(int paramX, int paramY) {
		if (location == null)
			return 0;
		// vi tri ma quan do dang dung
		if (paramX == 0 && paramY == 0)
			return 0;
		if (paramX >= 0 && paramX < 8 && paramY >= 0 && paramY < 8) {
			// vi tri can toi trong
			if (board.pieceBoard[paramX][paramY] == null)
				return 1;
			// vi tri can toi co quan khac mau
			else if (board.pieceBoard[paramX][paramY].getAlliance() != board.getPieceAt(location).getAlliance()) {
				return 2;
			}
			// vi tri can toi co quan cung mau
			else if (board.pieceBoard[paramX][paramY].getAlliance() == board.getPieceAt(location).getAlliance()) {
				return 3;
			} else
				return 0;
		}
		return 0;
	}

	public void printListLocationCanMove(Location location) {
		ChessBoard board = (ChessBoard) ob;
		if (board.getPieceAt(location) == null) {
			System.out.println("O nay trong");
			return;
		}
		System.out.println(location.toString());
		System.out.println("==================");
		for (Location l : getNormalRule())
			System.out.println(l.toString());
	}

	@Override
	public void update(Observable ob, Object arg) {
		if (ob instanceof ChessBoard) {
			ChessBoard observer = (ChessBoard) ob;
			this.ob = observer;
			this.board = observer;
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Rule getRule() {
		return rule;
	}

}
