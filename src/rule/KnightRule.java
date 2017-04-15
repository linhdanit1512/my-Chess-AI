package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import core.Location;

@SuppressWarnings("serial")
public class KnightRule extends Rule {

	public KnightRule(Observable ob, Location location) {
		super(ob, location);
		ob.addObserver(this);
	}

	public KnightRule(Observable ob, Rule rule, Location location) {
		super(ob, rule, location);
		ob.addObserver(this);
	}

	@Override
	public List<Location> getNormalRule() {
		List<Location> listLocation = new ArrayList<Location>();
		int x = location.getX();
		int y = location.getY();
		if (checkValidTile( x - 2, y - 1) != 0 && checkValidTile( x - 2, y - 1) != 3)
			listLocation.add(new Location(x - 2, y - 1));

		if (checkValidTile( x - 2, y + 1) != 0 && checkValidTile( x - 2, y + 1) != 3)
			listLocation.add(new Location(x - 2, y + 1));

		if (checkValidTile( x + 2, y - 1) != 0 && checkValidTile( x + 2, y - 1) != 3)
			listLocation.add(new Location(x + 2, y - 1));

		if (checkValidTile( x + 2, y + 1) != 0 && checkValidTile( x + 2, y + 1) != 3)
			listLocation.add(new Location(x + 2, y + 1));

		if (checkValidTile( x - 1, y - 2) != 0 && checkValidTile( x - 1, y - 2) != 3)
			listLocation.add(new Location(x - 1, y - 2));

		if (checkValidTile( x - 1, y + 2) != 0 && checkValidTile( x - 1, y + 2) != 3)
			listLocation.add(new Location(x - 1, y + 2));

		if (checkValidTile( x + 1, y - 2) != 0 && checkValidTile( x + 1, y - 2) != 3)
			listLocation.add(new Location(x + 1, y - 2));

		if (checkValidTile( x + 1, y + 2) != 0 && checkValidTile( x + 1, y + 2) != 3)
			listLocation.add(new Location(x + 1, y + 2));
		return listLocation;
	}

	@Override
	public List<Location> getAllLocationControl() {
		List<Location> listLocation = new ArrayList<Location>();
		int x = location.getX();
		int y = location.getY();
		if (checkValidTile( x - 2, y - 1) != 0)
			listLocation.add(new Location(x - 2, y - 1));

		if (checkValidTile( x - 2, y + 1) != 0)
			listLocation.add(new Location(x - 2, y + 1));

		if (checkValidTile( x + 2, y - 1) != 0)
			listLocation.add(new Location(x + 2, y - 1));

		if (checkValidTile( x + 2, y + 1) != 0)
			listLocation.add(new Location(x + 2, y + 1));

		if (checkValidTile( x - 1, y - 2) != 0)
			listLocation.add(new Location(x - 1, y - 2));

		if (checkValidTile( x - 1, y + 2) != 0)
			listLocation.add(new Location(x - 1, y + 2));

		if (checkValidTile( x + 1, y - 2) != 0)
			listLocation.add(new Location(x + 1, y - 2));

		if (checkValidTile( x + 1, y + 2) != 0)
			listLocation.add(new Location(x + 1, y + 2));
		return listLocation;
	}

}
