package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import core.Location;

public class RookRule extends Rule implements Observer {

	private static final long serialVersionUID = -7857180907238925165L;

	public RookRule(Observable ob, Location location) {
		super(ob, location);
		ob.addObserver(this);
	}

	public RookRule(Observable ob, Rule rule, Location location) {
		super(ob, rule, location);
		ob.addObserver(this);
	}

	@Override
	public List<Location> getNormalRule() {
		List<Location> listLocation = new ArrayList<Location>();
		int x = location.getX();
		int y = location.getY();
		// kiem tra phia ben trai
		for (int i = 1; i <= y; i++) {
			int c = checkValidTile(x, y - i);
			if (c == 0 || c == 3) {
				break;
			} else if (c == 1) {
				listLocation.add(new Location(x, y - i));
				continue;
			} else if (c == 2) {
				listLocation.add(new Location(x, y - i));
				break;
			}
		}
		// kiem tra phia ben phai
		for (int i = 1; i < 8 - y; i++) {
			int c = checkValidTile(x, y + i);
			if (c == 0 || c == 3) {
				break;
			} else if (c == 1) {
				listLocation.add(new Location(x, y + i));
				continue;
			} else if (c == 2) {
				listLocation.add(new Location(x, y + i));
				break;
			}
		}
		// kiem tra ben tren
		for (int i = 1; i <= x; i++) {
			int c = checkValidTile(x - i, y);
			if (c == 0 || c == 3) {
				break;
			} else if (c == 1) {
				listLocation.add(new Location(x - i, y));
				continue;
			} else if (c == 2) {
				listLocation.add(new Location(x - i, y));
				break;
			}
		}
		// kiem tra phia duoi
		for (int i = 1; i < 8 - x; i++) {
			int c = checkValidTile(x + i, y);
			if (c == 0 || c == 3) {
				break;
			} else if (c == 1) {
				listLocation.add(new Location(x + i, y));
				continue;
			} else if (c == 2) {
				listLocation.add(new Location(x + i, y));
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
		// kiem tra phia ben trai
		for (int i = 1; i <= y; i++) {
			int c = checkValidTile(x, y - i);
			if (c == 0) {
				break;
			} else if (c == 1) {
				listLocation.add(new Location(x, y - i));
				continue;
			} else if (c == 2 || c == 3) {
				listLocation.add(new Location(x, y - i));
				break;
			}
		}
		// kiem tra phia ben phai
		for (int i = 1; i < 8 - y; i++) {
			int c = checkValidTile(x, y + i);
			if (c == 0) {
				break;
			} else if (c == 1) {
				listLocation.add(new Location(x, y + i));
				continue;
			} else if (c == 2 || c == 3) {
				listLocation.add(new Location(x, y + i));
				break;
			}
		}
		// kiem tra ben tren
		for (int i = 1; i <= x; i++) {
			int c = checkValidTile(x - i, y);
			if (c == 0) {
				break;
			} else if (c == 1) {
				listLocation.add(new Location(x - i, y));
				continue;
			} else if (c == 2 || c == 3) {
				listLocation.add(new Location(x - i, y));
				break;
			}
		}
		// kiem tra phia duoi
		for (int i = 1; i < 8 - x; i++) {
			int c = checkValidTile(x + i, y);
			if (c == 0) {
				break;
			} else if (c == 1) {
				listLocation.add(new Location(x + i, y));
				continue;
			} else if (c == 2 || c == 3) {
				listLocation.add(new Location(x + i, y));
				break;
			}
		}
		return listLocation;
	}

}
