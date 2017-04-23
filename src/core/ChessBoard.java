package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import chess.ColorPiece;
import chess.Piece;
import chess.PieceType;
import rule.BishopRule;
import rule.Castling;
import rule.KingRule;
import rule.KnightRule;
import rule.PawnRule;
import rule.QueenRule;
import rule.RookRule;

public class ChessBoard extends Observable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6210724185703262293L;
	public Piece[][] pieceBoard = new Piece[8][8];
	// Map<Location, Piece> mapPiece = new HashMap<>();
	public Map<Integer, Piece> mapKing = new HashMap<Integer, Piece>();
	public List<Piece> listWhiteAlliance = new ArrayList<>();
	public List<Piece> listBlackAlliance = new ArrayList<>();
	private int player;

	public ChessBoard(Piece[][] pieceBoard, int player) {
		super();
		this.pieceBoard = pieceBoard;
		this.setPlayer(player);
	}

	public ChessBoard() {
		init();
	}

	public static void main(String[] args) {
		ChessBoard ch = new ChessBoard();

		// List<Piece> list = ch.getPiecesControlLocation(new
		// Location(1,4)).get("all");
		// for(Piece p: list){
		// System.out.println(p.toString());
		// }
		ch.printBoard();

		// System.out.println(ch.pieceBoard[0][0].equals(ch.pieceBoard[7][7]));
		// System.out.println("================");
		// Rule rule = ch.pieceBoard[0][1].getRule();
		// rule.printListLocation(new Location(0, 1));
		// ch.removePiece(new Location(0, 1));
		// ch.addPiece(new Location(2, 0), ch.BLACKKNIGHT);
		// rule.printListLocation(new Location(2, 0));
		// System.out.println();
		// rule.printListLocation(new Location(0, 1));
	}

	public void init() {
		/**
		 * BLACK ALLIANCE
		 */
		addPiece(new Location(0, 0), new Piece(PieceType.ROOK, 'R', new RookRule(this, new Location(0, 0)),
				ColorPiece.BLACK, 50, "blackrook.png"));
		addPiece(new Location(0, 1), new Piece(PieceType.KNIGHT, 'N', new KnightRule(this, new Location(0, 1)),
				ColorPiece.BLACK, 30, "blackknight.png"));
		addPiece(new Location(0, 2), new Piece(PieceType.BISHOP, 'B', new BishopRule(this, new Location(0, 2)),
				ColorPiece.BLACK, 32, "blackbishop.png"));
		addPiece(new Location(0, 3), new Piece(PieceType.QUEEN, 'Q', new QueenRule(this, new Location(0, 3)),
				ColorPiece.BLACK, 90, "blackqueen.png"));
		addPiece(new Location(0, 4),
				new Piece(PieceType.KING, 'K',
						new KingRule(this, new Castling(this, new Location(0, 4)), new Location(0, 4)),
						ColorPiece.BLACK, 10000, "blackking.png"));
		addPiece(new Location(0, 5), new Piece(PieceType.BISHOP, 'B', new BishopRule(this, new Location(0, 5)),
				ColorPiece.BLACK, 32, "blackbishop.png"));
		addPiece(new Location(0, 6), new Piece(PieceType.KNIGHT, 'N', new KnightRule(this, new Location(0, 6)),
				ColorPiece.BLACK, 30, "blackknight.png"));
		addPiece(new Location(0, 7), new Piece(PieceType.ROOK, 'R', new RookRule(this, new Location(0, 7)),
				ColorPiece.BLACK, 50, "blackrook.png"));

		for (int i = 0; i < pieceBoard[0].length; i++) {
			addPiece(new Location(1, i), new Piece(PieceType.PAWN, 'C', new PawnRule(this, new Location(1, i)),
					ColorPiece.BLACK, 10, "blackpawn.png"));
			addPiece(new Location(6, i), new Piece(PieceType.PAWN, 'C', new PawnRule(this, new Location(6, i)),
					ColorPiece.WHITE, 10, "whitepawn.png"));
		}
		/**
		 * WHITE ALLIANCE
		 */
		addPiece(new Location(7, 0), new Piece(PieceType.ROOK, 'R', new RookRule(this, new Location(7, 0)),
				ColorPiece.WHITE, 50, "whiterook.png"));
		addPiece(new Location(7, 1), new Piece(PieceType.KNIGHT, 'N', new KnightRule(this, new Location(7, 1)),
				ColorPiece.WHITE, 30, "whiteknight.png"));
		addPiece(new Location(7, 2), new Piece(PieceType.BISHOP, 'B', new BishopRule(this, new Location(7, 2)),
				ColorPiece.WHITE, 32, "whitebishop.png"));
		addPiece(new Location(7, 3), new Piece(PieceType.QUEEN, 'Q', new QueenRule(this, new Location(7, 3)),
				ColorPiece.WHITE, 90, "whitequeen.png"));
		addPiece(new Location(7, 4),
				new Piece(PieceType.KING, 'K',
						new KingRule(this, new Castling(this, new Location(7, 4)), new Location(7, 4)),
						ColorPiece.WHITE, 10000, "whiteking.png"));
		addPiece(new Location(7, 5), new Piece(PieceType.BISHOP, 'B', new BishopRule(this, new Location(7, 5)),
				ColorPiece.WHITE, 32, "whitebishop.png"));
		addPiece(new Location(7, 6), new Piece(PieceType.KNIGHT, 'N', new KnightRule(this, new Location(7, 6)),
				ColorPiece.WHITE, 30, "whiteknight.png"));
		addPiece(new Location(7, 7), new Piece(PieceType.ROOK, 'R', new RookRule(this, new Location(7, 7)),
				ColorPiece.WHITE, 50, "whiterook.png"));

		mapKing.put(ColorPiece.BLACK, getPieceAt(new Location(0, 4)));
		mapKing.put(ColorPiece.WHITE, getPieceAt(new Location(7, 4)));
		setPlayer(ColorPiece.WHITE);

	}

	public boolean addPiece(Location location, Piece piece) {
		if (location == null)
			return false;
		if (piece == null)
			return false;
		int x = location.getX();
		int y = location.getY();
		pieceBoard[x][y] = piece;
		pieceBoard[x][y].setLocation(location);
		pieceBoard[x][y].getRule().setLocation(location);
		if (pieceBoard[x][y].getColor() == ColorPiece.WHITE)
			listWhiteAlliance.add(piece);
		else if (pieceBoard[x][y].getColor() == ColorPiece.BLACK)
			listBlackAlliance.add(piece);
		return true;
		// this.mapPiece.put(location, piece);
	}

	public void setPieceAtLocation(Location location, Piece piece) {
		if (location == null)
			return;
		if (piece == null)
			return;
		int x = location.getX();
		int y = location.getY();
		if (pieceBoard[x][y] != null)
			// xoa quan co o vi tri se toi
			removePiece(location);
		Location tmp = piece.getLocation();
		pieceBoard[x][y] = piece;
		if (pieceBoard[x][y].getColor() == ColorPiece.WHITE)
			for (Piece a : listWhiteAlliance) {
				if (a.equals(piece) && a.getLocation() == piece.getLocation())
					a.setLocation(location);
			}
		else if (pieceBoard[x][y].getColor() == ColorPiece.BLACK)
			for (Piece a : listBlackAlliance) {
				if (a.equals(piece) && a.getLocation() == piece.getLocation())
					a.setLocation(location);
			}

		this.pieceBoard[x][y].setLocation(location);
		this.pieceBoard[x][y].getRule().setLocation(location);
		pieceBoard[tmp.getX()][tmp.getY()] = null;
	}

	public void removePiece(Location location) {
		if (location == null)
			return;
		int x = location.getX();
		int y = location.getY();
		if (pieceBoard[x][y] != null) {
			if (pieceBoard[x][y].getColor() == ColorPiece.BLACK)
				listBlackAlliance.remove(getPieceAt(location));
			else if (pieceBoard[x][y].getColor() == ColorPiece.WHITE)
				listWhiteAlliance.remove(getPieceAt(location));
		}
		this.pieceBoard[x][y] = null;
	}

	public Piece getPieceAt(Location location) {
		if (location != null)
			return pieceBoard[location.getX()][location.getY()];
		return null;
	}

	/**
	 * 
	 * @return quan vua cua 2 ben
	 * @param key:
	 *            ColorPiece.WHITE cho vua trang
	 * @param key:
	 *            ColorPiece.BLACK cho vua den
	 */
	public Map<Integer, Piece> getKing() {
		if (mapKing == null || mapKing.size() != 2) {
			mapKing = new HashMap<Integer, Piece>();
			for (int j = 0; j < pieceBoard[0].length; j++) {
				for (int i = 0; i < pieceBoard.length; i++) {
					if (pieceBoard[i][j] != null && pieceBoard[i][j].getType() == PieceType.KING) {
						mapKing.put(pieceBoard[i][j].getColor(), pieceBoard[i][j]);
						if (mapKing.size() == 2)
							return mapKing;
					}
				}
			}
		}
		return mapKing;
	}

	public void printBoard() {
		for (int i = 0; i < pieceBoard.length; i++) {
			for (int j = 0; j < pieceBoard[0].length; j++) {
				if (pieceBoard[i][j] != null) {
					if (pieceBoard[i][j].getColor() == ColorPiece.BLACK)
						System.out.printf(" -%s", pieceBoard[i][j].getAcronym());
					else
						System.out.printf("  %s", pieceBoard[i][j].getAcronym());
				} else {
					System.out.print("  0");
				}
			}
			System.out.println();
		}
	}

	public void printLocation() {
		for (int i = 0; i < pieceBoard.length; i++) {
			for (int j = 0; j < pieceBoard[0].length; j++) {
				if (pieceBoard[i][j] == null)
					System.out.print("\t\t");
				else
					System.out.print(pieceBoard[i][j].getLocation().toString() + "\t");
			}
			System.out.println();
		}
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public void measurementsChanged() {
		setChanged();
		notifyObservers();
	}

	public void setMeasurements(int player, Piece[][] pieces) {
		this.player = player;
		this.pieceBoard = pieces;
		measurementsChanged();

	}

}
