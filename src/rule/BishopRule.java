package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import core.Location;

@SuppressWarnings("serial")
public class BishopRule extends Rule {

	public BishopRule(Observable ob, Rule rule, Location location) {
		super(ob, rule, location);
		ob.addObserver(this);
	}

	public BishopRule(Observable ob, Location location) {
		super(ob, location);
		ob.addObserver(this);
	}

	@Override
	public List<Location> getNormalRule() {
		List<Location> listLocation = new ArrayList<Location>();
		int x = location.getX();
		int y = location.getY();
		// kiem tra cheo tren trai
		for (int i = 1; i <= x; i++) {
			if (checkValidTile( x - i, y - i) == 0 || checkValidTile( x - i, y - i) == 3) {
				break;
			} else if (checkValidTile( x - i, y - i) == 1) {
				listLocation.add(new Location(x - i, y - i));
			} else if (checkValidTile( x - i, y - i) == 2) {
				listLocation.add(new Location(x - i, y - i));
				break;
			}
		}
		// kiem tra cheo tren phai
		for (int i = 1; i <= x; i++) {
			if (checkValidTile( x - i, y + i) == 0 || checkValidTile( x - i, y + i) == 3) {
				break;
			} else if (checkValidTile( x - i, y + i) == 1) {
				listLocation.add(new Location(x - i, y + i));
			} else if (checkValidTile( x - i, y + i) == 2) {
				listLocation.add(new Location(x - i, y + i));
				break;
			}
		}
		// kiem tra cheo duoi trai
		for (int i = 1; i < 8 - x; i++) {
			if (checkValidTile( x + i, y - i) == 0 || checkValidTile( x + i, y - i) == 3) {
				break;
			} else if (checkValidTile( x + i, y - i) == 1) {
				listLocation.add(new Location(x + i, y - i));
			} else if (checkValidTile( x + i, y - i) == 2) {
				listLocation.add(new Location(x + i, y - i));
				break;
			}
		}
		// kiem tra cheo duoi phai
		for (int i = 1; i < 8 - x; i++) {
			if (checkValidTile( x + i, y + i) == 0 || checkValidTile( x + i, y + i) == 3) {
				break;
			} else if (checkValidTile( x + i, y + i) == 1) {
				listLocation.add(new Location(x + i, y + i));
			} else if (checkValidTile( x + i, y + i) == 2) {
				listLocation.add(new Location(x + i, y + i));
				break;
			}
		}
		return listLocation;
	}

	@Override
	public List<Location> getAllLocationControl() {
		List<Location> listLocation = new ArrayList<Location>();
		int x = location.getX();
		int y = location.getY();
		// kiem tra cheo tren trai
		for (int i = 1; i <= x; i++) {
			if (checkValidTile( x - i, y - i) == 0) {
				break;
			} else if (checkValidTile( x - i, y - i) == 1) {
				listLocation.add(new Location(x - i, y - i));
			} else if (checkValidTile( x - i, y - i) == 2 || checkValidTile( x - i, y - i) == 3) {
				listLocation.add(new Location(x - i, y - i));
				break;
			}
		}
		// kiem tra cheo tren phai
		for (int i = 1; i <= x; i++) {
			if (checkValidTile( x - i, y + i) == 0) {
				break;
			} else if (checkValidTile( x - i, y + i) == 1) {
				listLocation.add(new Location(x - i, y + i));
			} else if (checkValidTile( x - i, y + i) == 2 || checkValidTile( x - i, y + i) == 3) {
				listLocation.add(new Location(x - i, y + i));
				break;
			}
		}
		// kiem tra cheo duoi trai
		for (int i = 1; i < 8 - x; i++) {
			if (checkValidTile( x + i, y - i) == 0) {
				break;
			} else if (checkValidTile( x + i, y - i) == 1) {
				listLocation.add(new Location(x + i, y - i));
			} else if (checkValidTile( x + i, y - i) == 2 || checkValidTile( x + i, y - i) == 3) {
				listLocation.add(new Location(x + i, y - i));
				break;
			}
		}
		// kiem tra cheo duoi phai
		for (int i = 1; i < 8 - x; i++) {
			if (checkValidTile( x + i, y + i) == 0) {
				break;
			} else if (checkValidTile( x + i, y + i) == 1) {
				listLocation.add(new Location(x + i, y + i));
			} else if (checkValidTile( x + i, y + i) == 2 || checkValidTile( x + i, y + i) == 3) {
				listLocation.add(new Location(x + i, y + i));
				break;
			}
		}
		return listLocation;
	}

}
