package core;

import action.Move;

public class Record {
	private int order;
	private String player;
	private Move move;

	public Record(int order, String player, Move move) {
		super();
		this.order = order;
		this.player = player;
		this.move = move;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

}
