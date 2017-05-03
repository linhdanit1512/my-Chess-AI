package action;

import chess.Piece;
import core.Location;

public class Move {
	private Location from;
	private Location to;
	private Piece pieceFrom;
	private Piece prisoner;

	public Move(Location from, Location to, Piece pieceFrom, Piece prisoner) {
		super();
		this.from = from;
		this.to = to;
		this.pieceFrom = pieceFrom;
		this.prisoner = prisoner;
	}

	public Move(Location from, Location to) {
		super();
		this.from = from;
		this.to = to;
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

	public String toFullString() {
		if (prisoner != null)
			return from.toWordString() + " → " + to.toWordString() +" "+ pieceFrom.getType().toString() + " : "
					+ prisoner.getType().toString();
		else
			return from.toWordString() + " → " + to.toWordString() +" "+ pieceFrom.getType().toString();
	}

	@Override
	public String toString() {
		return "" + getPieceFrom().getAcronym() + from.toWordString() + " → " + getTo().toWordString();
	}

}
