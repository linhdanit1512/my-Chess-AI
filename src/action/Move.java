package action;

import chess.Piece;
import core.Location;

public class Move {
	private Piece from;
	private Location to;

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

	@Override
	public String toString() {
		return from.getLocation() + " → " + to;
	}

}
