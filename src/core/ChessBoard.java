package core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import action.Move;
import chess.Alliance;
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
	public Map<Integer, Piece> mapKing = new HashMap<Integer, Piece>();
	private int player;
	private Move premove;

	public ChessBoard(Piece[][] pieceBoard, int player) {
		super();
		this.pieceBoard = pieceBoard;
		this.setPlayer(player);
	}

	public ChessBoard(ChessBoard copyBoard) {
		this(copyBoard.pieceBoard, copyBoard.player);
		this.mapKing = copyBoard.getKing();
	}

	public ChessBoard() {
		init();
	}

	private void init() {
		/**
		 * BLACK ALLIANCE
		 */
		addPiece(new Location(0, 0), new Piece(PieceType.ROOK, 'R', new RookRule(this, new Location(0, 0)),
				Alliance.BLACK, 50, "blackrook.png"));
		addPiece(new Location(0, 1), new Piece(PieceType.KNIGHT, 'N', new KnightRule(this, new Location(0, 1)),
				Alliance.BLACK, 30, "blackknight.png"));
		addPiece(new Location(0, 2), new Piece(PieceType.BISHOP, 'B', new BishopRule(this, new Location(0, 2)),
				Alliance.BLACK, 32, "blackbishop.png"));
		addPiece(new Location(0, 3), new Piece(PieceType.QUEEN, 'Q', new QueenRule(this, new Location(0, 3)),
				Alliance.BLACK, 90, "blackqueen.png"));
		addPiece(new Location(0, 4),
				new Piece(PieceType.KING, 'K',
						new KingRule(this, new Castling(this, new Location(0, 4)), new Location(0, 4)), Alliance.BLACK,
						10000, "blackking.png"));
		addPiece(new Location(0, 5), new Piece(PieceType.BISHOP, 'B', new BishopRule(this, new Location(0, 5)),
				Alliance.BLACK, 32, "blackbishop.png"));
		addPiece(new Location(0, 6), new Piece(PieceType.KNIGHT, 'N', new KnightRule(this, new Location(0, 6)),
				Alliance.BLACK, 30, "blackknight.png"));
		addPiece(new Location(0, 7), new Piece(PieceType.ROOK, 'R', new RookRule(this, new Location(0, 7)),
				Alliance.BLACK, 50, "blackrook.png"));

		for (int i = 0; i < pieceBoard[0].length; i++) {
			addPiece(new Location(1, i), new Piece(PieceType.PAWN, ' ', new PawnRule(this, new Location(1, i)),
					Alliance.BLACK, 10, "blackpawn.png"));
			addPiece(new Location(6, i), new Piece(PieceType.PAWN, ' ', new PawnRule(this, new Location(6, i)),
					Alliance.WHITE, 10, "whitepawn.png"));
		}
		/**
		 * WHITE ALLIANCE
		 */
		addPiece(new Location(7, 0), new Piece(PieceType.ROOK, 'R', new RookRule(this, new Location(7, 0)),
				Alliance.WHITE, 50, "whiterook.png"));
		addPiece(new Location(7, 1), new Piece(PieceType.KNIGHT, 'N', new KnightRule(this, new Location(7, 1)),
				Alliance.WHITE, 30, "whiteknight.png"));
		addPiece(new Location(7, 2), new Piece(PieceType.BISHOP, 'B', new BishopRule(this, new Location(7, 2)),
				Alliance.WHITE, 32, "whitebishop.png"));
		addPiece(new Location(7, 3), new Piece(PieceType.QUEEN, 'Q', new QueenRule(this, new Location(7, 3)),
				Alliance.WHITE, 90, "whitequeen.png"));
		addPiece(new Location(7, 4),
				new Piece(PieceType.KING, 'K',
						new KingRule(this, new Castling(this, new Location(7, 4)), new Location(7, 4)), Alliance.WHITE,
						10000, "whiteking.png"));
		addPiece(new Location(7, 5), new Piece(PieceType.BISHOP, 'B', new BishopRule(this, new Location(7, 5)),
				Alliance.WHITE, 32, "whitebishop.png"));
		addPiece(new Location(7, 6), new Piece(PieceType.KNIGHT, 'N', new KnightRule(this, new Location(7, 6)),
				Alliance.WHITE, 30, "whiteknight.png"));
		addPiece(new Location(7, 7), new Piece(PieceType.ROOK, 'R', new RookRule(this, new Location(7, 7)),
				Alliance.WHITE, 50, "whiterook.png"));

		mapKing.put(Alliance.BLACK, getPieceAt(new Location(0, 4)));
		mapKing.put(Alliance.WHITE, getPieceAt(new Location(7, 4)));
		setPlayer(Alliance.WHITE);

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
		return true;
	}

	public void setPieceAtLocation(Location location, Piece piece) {
		if (location == null)
			return;
		if (piece == null)
			return;

		int x = location.getX();
		int y = location.getY();
		Location tmp = piece.getLocation();
		piece.setLocation(location);
		this.pieceBoard[x][y] = piece;
		this.pieceBoard[x][y].getRule().setLocation(location);
		if (!location.equals(piece.getLocation()))
			pieceBoard[tmp.getX()][tmp.getY()] = null;
	}

	public void removePiece(Location location) {
		if (location == null)
			return;
		int x = location.getX();
		int y = location.getY();
		this.pieceBoard[x][y] = null;
	}

	public Piece getPieceAt(Location location) {
		if (location != null)
			return pieceBoard[location.getX()][location.getY()];
		return null;
	}

	public boolean isHasPiece(int x, int y) {
		if (x >= 0 && x < 8 && y >= 0 && y < 8) {
			if (pieceBoard[x][y] != null)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return quan vua cua 2 ben
	 * @param key:
	 *            Alliance.WHITE cho vua trang
	 * @param key:
	 *            Alliance.BLACK cho vua den
	 */
	public Map<Integer, Piece> getKing() {
		if (mapKing == null || mapKing.size() != 2) {
			mapKing = new HashMap<Integer, Piece>();
			for (int j = 0; j < pieceBoard[0].length; j++) {
				for (int i = 0; i < pieceBoard.length; i++) {
					if (pieceBoard[i][j] != null && pieceBoard[i][j].getType() == PieceType.KING) {
						mapKing.put(pieceBoard[i][j].getAlliance(), pieceBoard[i][j]);
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
					if (pieceBoard[i][j].getAlliance() == Alliance.BLACK)
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

	public void setMeasurements(int player, Piece[][] pieces, Move premove) {
		this.player = player;
		this.pieceBoard = pieces;
		setPremove(premove);
		measurementsChanged();
	}

	private void normal(Move move) {
		setPieceAtLocation(move.getTo(), move.getPieceFrom());
		getPieceAt(move.getTo()).updateMove();
		removePiece(move.getFrom());
	}

	private void undoNormal(Move move) {
		setPieceAtLocation(move.getFrom(), move.getPieceFrom());
		if (move.getPrisoner() != null) {
			setPieceAtLocation(move.getTo(), move.getPrisoner());
		} else {
			removePiece(move.getTo());
		}
		getPieceAt(move.getFrom()).updateUndoMove();
	}

	private void promotion(Move move) {
		setPieceAtLocation(move.getTo(), move.getPiecePromotion());
		removePiece(move.getFrom());
	}

	private void undoPromotion(Move move) {
		if (move.getPrisoner() != null) {
			setPieceAtLocation(move.getTo(), move.getPrisoner());
		} else
			removePiece(move.getTo());
		setPieceAtLocation(move.getFrom(), move.getPieceFrom());
		getPieceAt(move.getFrom()).updateUndoMove();
	}

	private void castling(Move move) {
		move.getPieceFrom().getRule().setRule(null);
		setPieceAtLocation(move.getTo(), move.getPieceFrom());
		getPieceAt(move.getTo()).updateMove();
		removePiece(move.getFrom());
		int x = move.getTo().getX();
		if (move.isCastlingQueen()) {
			setPieceAtLocation(new Location(x, 3), pieceBoard[x][0]);
			getPieceAt(new Location(x, 3)).updateMove();
			pieceBoard[x][0] = null;
		}

		else if (move.isCastlingKing()) {
			setPieceAtLocation(new Location(x, 5), pieceBoard[x][7]);
			getPieceAt(new Location(x, 5)).updateMove();
			pieceBoard[x][7] = null;
		}
	}

	private void undoCastling(Move move) {
		setPieceAtLocation(move.getFrom(), move.getPieceFrom());
		getPieceAt(move.getFrom()).updateUndoMove();
		removePiece(move.getTo());
		int x = move.getFrom().getX();
		if (move.isCastlingQueen()) {
			setPieceAtLocation(new Location(x, 0), pieceBoard[x][3]);
			removePiece(new Location(x, 3));
			pieceBoard[x][0].updateUndoMove();
		}
		if (move.isCastlingKing()) {
			setPieceAtLocation(new Location(x, 7), pieceBoard[x][5]);
			removePiece(new Location(x, 5));
			pieceBoard[x][7].updateUndoMove();
		}
	}

	private void undoPassant(Move move, Move premove) {
		setPieceAtLocation(premove.getTo(), move.getPrisoner());
		setPieceAtLocation(move.getFrom(), move.getPieceFrom());
		removePiece(move.getTo());
		getPieceAt(move.getFrom()).updateUndoMove();
	}

	private void passant(Move move) {
		move.setPrisoner(premove.getPieceFrom());
		setPieceAtLocation(move.getTo(), move.getPieceFrom());
		getPieceAt(move.getTo()).updateMove();
		removePiece(premove.getTo());
		removePiece(move.getFrom());
	}

	public boolean makeMove(Move move) {
		if (move == null)
			return false;
		if (move.getPieceFrom() == null)
			return false;
		if (move.getFrom().equals(move.getTo()))
			return false;
		if (move.getPrisoner() == null)
			move.setPrisoner(getPieceAt(move.getTo()));
		if (move.getPieceFrom().getAlliance() == getPlayer()) {
			if (move.getPieceFrom().getRule().getRealLocationCanMove().contains(move.getTo())) {
				if (move.isPromotion()) {
					System.out.println("isPromotion");
					promotion(move);
				} else if (move.isCastlingKing() || move.isCastlingQueen()) {
					System.out.println("isCastling");
					castling(move);
				} else if (move.passant(premove)) {
					System.out.println("isPassant");
					passant(move);
				} else if (!move.isCastlingKing() && !move.isCastlingQueen() && !move.isPassant()
						&& !move.isPromotion()) {
					System.out.println("isNormal");
					normal(move);
				}
				return true;
			}
		}
		return false;
	}

	public boolean unMakeMove(Move move, Move premove) {
		if (move == null)
			return false;
		if (move.getPieceFrom() == null)
			return false;
		if (move.getFrom().equals(move.getTo()))
			return false;
		if (move.isPromotion()) {
			System.out.println("undo promotion");
			undoPromotion(move);
		} else if (move.isCastlingKing() || move.isCastlingQueen()) {
			System.out.println("undo castling");
			undoCastling(move);
		} else if (move.isPassant()) {
			System.out.println("undo passant");
			undoPassant(move, premove);
		} else if (!move.isCastlingKing() && !move.isCastlingQueen() && !move.isPassant() && !move.isPromotion()) {
			System.out.println("undo normal");
			undoNormal(move);
		}
		setPremove(premove);
		return true;
	}

	public Move getPremove() {
		return premove;
	}

	public void setPremove(Move premove) {
		this.premove = premove;
	}

}
