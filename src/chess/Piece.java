package chess;

import core.Location;
import rule.Rule;

public class Piece {
	Location location;
	PieceType type;
	char acronym;
	Rule rule;
	int alliance;
	int score;
	String linkImg;
	int move;

	public Piece() {
		super();
	}

	public Piece(Piece piece) {
		super();
		this.location = piece.getLocation();
		this.type = piece.getType();
		this.acronym = piece.getAcronym();
		this.rule = piece.getRule();
		this.alliance = piece.getAlliance();
		this.score = piece.getScore();
		this.linkImg = piece.getLinkImg();
		this.move = piece.getMove();
		this.rule.setLocation(location);
	}

	public Piece(Location location, PieceType type, char acronym, Rule rule, int alliance, int score, String linkImg) {
		super();
		this.location = location;
		this.type = type;
		this.acronym = acronym;
		this.rule = rule;
		this.alliance = alliance;
		this.score = score;
		this.linkImg = linkImg;
		this.move = 0;
	}

	public Piece(PieceType name, char acronym, Rule rule, int alliance, int score, String linkImg) {
		super();
		this.type = name;
		this.acronym = acronym;
		this.rule = rule;
		this.alliance = alliance;
		this.score = score;
		this.linkImg = linkImg;
		this.move = 0;
	}

	public boolean isControlTileAtLocation(Location locatDes) {
		if (rule.getNormalRule().contains(locatDes))
			return true;
		else
			return false;
	}

	public char getAcronym() {
		return acronym;
	}

	public void setAcronym(char acronym) {
		this.acronym = acronym;
	}

	public PieceType getType() {
		return type;
	}

	public void setType(PieceType name) {
		this.type = name;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getLinkImg() {
		return linkImg;
	}

	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		this.rule.setLocation(location);
	}

	public int getMove() {
		return move;
	}

	public void updateMove() {
		this.move += 1;
	}

	public void updateUndoMove() {
		this.move -= 1;
	}

	public void setMove(int move) {
		this.move = move;
	}

	@Override
	public String toString() {
		return getType() + ":\t" + getAcronym() + " " + getLocation() + "\tRule: " + getRule().getClass().getName()
				+ "\tScore: " + getScore() + ", image: " + getLinkImg() + ", alliance: " + alliance;
	}

	public int getAlliance() {
		return alliance;
	}

	public void setAlliance(int alliance) {
		this.alliance = alliance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acronym;
		result = prime * result + alliance;
		result = prime * result + ((linkImg == null) ? 0 : linkImg.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + move;
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		result = prime * result + score;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Piece other = (Piece) obj;
		if (acronym != other.acronym)
			return false;
		if (alliance != other.alliance)
			return false;
		if (linkImg == null) {
			if (other.linkImg != null)
				return false;
		} else if (!linkImg.equals(other.linkImg))
			return false;
		if (move != other.move)
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (score != other.score)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
