package action;

import chess.Piece;
import core.Location;

public class Move {
	private Piece from;
	private Location to;
	private Piece prisoner;

	public Move(Piece from, Location to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Piece getFrom() {
		return from;
	}

	public void setFrom(Piece from) {
		this.from = from;
	}

	public Location getTo() {
		return to;
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public String toWordString() {
		return from.getAcronym() + from.getLocation().toWordString() + " → " + to.toWordString();
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

	@Override
	public String toString() {
		return "" + getFrom().getAcronym() + getFrom().getLocation().toWordString() + " → " + getTo().toWordString();
	}

}
