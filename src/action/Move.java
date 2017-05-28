package action;

import java.util.List;

import chess.Piece;
import chess.PieceType;
import core.Location;
import rule.Castling;
import rule.PawnRule;

public class Move {
	private Location from;
	private Location to;
	private Piece pieceFrom;
	private Piece prisoner;
	private boolean isCastlingQueen, isCastlingKing, isPromotion, isPassant, isChessmate, isChess, isDraw;

	public Move(Location from, Location to, Piece pieceFrom, Piece prisoner) {
		super();
		this.from = from;
		this.to = to;
		this.pieceFrom = pieceFrom;
		this.prisoner = prisoner;
		castling();
		promotion();
	}

	private boolean castling() {
		if (pieceFrom != null && to != null) {
			if (pieceFrom.getType().equals(PieceType.KING)) {
				if (pieceFrom.getRule().getRule() != null) {
					Castling castling = (Castling) pieceFrom.getRule().getRule();
					List<Location> list = castling.castling(pieceFrom);
					if (list != null && !list.isEmpty()) {
						if (list.contains(to)) {
							if (to.getY() == 6) {
								isCastlingKing = true;
							} else if (to.getY() == 2) {
								isCastlingQueen = true;
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private Piece piecePromotion;

	private boolean promotion() {
		if (pieceFrom != null) {
			if (pieceFrom.getType() == PieceType.PAWN) {
				PawnRule rule = (PawnRule) pieceFrom.getRule();
				if (rule.pawnPromotion(from)) {
					isPromotion = true;
					return true;
				}
			}
		}
		return false;
	}

	public boolean passant(Move premove) {
		if (premove != null)
			if (pieceFrom != null && to != null) {
				if (pieceFrom.getType() == PieceType.PAWN) {
					PawnRule rule = (PawnRule) pieceFrom.getRule();
					Location loca = rule.passant();
					if (to.equals(loca)) {
						isPassant = true;
						return true;
					}
				}
			}
		return false;
	}

	public Location getFrom() {
		return from;
	}

	public void setFrom(Location from) {
		this.from = from;
	}

	public Piece getPieceFrom() {
		return pieceFrom;
	}

	public void setPieceFrom(Piece from) {
		this.pieceFrom = from;
	}

	public Location getTo() {
		return to;
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public Piece getPrisoner() {
		return prisoner;
	}

	public void setPrisoner(Piece prisoner) {
		this.prisoner = prisoner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((pieceFrom == null) ? 0 : pieceFrom.hashCode());
		result = prime * result + ((prisoner == null) ? 0 : prisoner.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (pieceFrom == null) {
			if (other.pieceFrom != null)
				return false;
		} else if (!pieceFrom.equals(other.pieceFrom))
			return false;
		if (prisoner == null) {
			if (other.prisoner != null)
				return false;
		} else if (!prisoner.equals(other.prisoner))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	public String toStrings() {
		StringBuilder builder = new StringBuilder();
		builder.append("Move [from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", pieceFrom=");
		builder.append(pieceFrom);
		builder.append(", prisoner=");
		builder.append(prisoner);
		builder.append(", isCastlingQueen=");
		builder.append(isCastlingQueen);
		builder.append(", isCastlingKing=");
		builder.append(isCastlingKing);
		builder.append(", isPromotion=");
		builder.append(isPromotion);
		builder.append(", isPassant=");
		builder.append(isPassant);
		builder.append(", isChessmate=");
		builder.append(isChessmate);
		builder.append(", isChess=");
		builder.append(isChess);
		builder.append(", piecePromotion=");
		builder.append(piecePromotion);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * 
	 * cách ghi để đánh giá nước cờ
	 * 
	 * ! nước đi hay
	 * 
	 * !! nước rất hay
	 * 
	 * +- trắng ưu thế tuyệt đối
	 * 
	 * -+ đen ưu thế tuyệt đối
	 * 
	 * ? nước cờ không hay (sai lầm)
	 * 
	 * ?? nước đi quá tệ
	 */

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isCastlingQueen)
			sb.append("O-O-O");
		else if (isCastlingKing)
			sb.append("O-O");
		else if (isPromotion) {
			sb.append(to.toWordString());
			sb.append("=");
			sb.append("" + piecePromotion.getAcronym());
		} else if (isChessmate) {
			sb.append("" + pieceFrom.getAcronym());
			sb.append(to.toWordString());
			sb.append("#");
		} else if (isChess) {
			sb.append("" + pieceFrom.getAcronym());
			sb.append(to.toWordString());
			sb.append("+");
		} else {
			if (prisoner == null) {
				sb.append("" + pieceFrom.getAcronym());
				sb.append(to.toWordString());
			} else {
				if (pieceFrom.getType() == PieceType.PAWN) {
					sb.append("c");
				} else {
					sb.append("" + pieceFrom.getAcronym());
				}
				sb.append("x");
				sb.append(to.toWordString());
			}
		}
		return sb.toString();
	}

	public boolean isCastlingQueen() {
		return isCastlingQueen;
	}

	public boolean isCastlingKing() {
		return isCastlingKing;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public boolean isPassant() {
		return isPassant;
	}

	public void setPassant(boolean isPassant) {
		this.isPassant = isPassant;
	}

	public Piece getPiecePromotion() {
		return piecePromotion;
	}

	public void setPiecePromotion(Piece piecePromotion) {
		this.piecePromotion = piecePromotion;
	}

	public boolean isChessmate() {
		return isChessmate;
	}

	public void setChessmate(boolean isChessmate) {
		this.isChessmate = isChessmate;
	}

	public boolean isChess() {
		return isChess;
	}

	public void setChess(boolean isChess) {
		this.isChess = isChess;
	}

	public void setCastlingQueen(boolean isCastlingQueen) {
		this.isCastlingQueen = isCastlingQueen;
	}

	public void setCastlingKing(boolean isCastlingKing) {
		this.isCastlingKing = isCastlingKing;
	}

	public void setPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public boolean isDraw() {
		return isDraw;
	}

	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}

}
