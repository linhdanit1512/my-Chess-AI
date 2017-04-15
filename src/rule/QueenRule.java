package rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import core.Location;

@SuppressWarnings("serial")
public class QueenRule extends Rule {

	public QueenRule(Observable ob, Location location) {
		super(ob, location);
		ob.addObserver(this);
	}

	public QueenRule(Observable ob, Rule rule, Location location) {
		super(ob, rule, location);
		ob.addObserver(this);
	}

	@Override
	public List<Location> getNormalRule() {
		List<Location> listLocation = new ArrayList<Location>();
		listLocation.addAll(new RookRule(ob, location).getNormalRule());
		listLocation.addAll(new BishopRule(ob, location).getNormalRule());
		return listLocation;
	}

	@Override
	public List<Location> getAllLocationControl() {
		List<Location> listLocation = new ArrayList<Location>();
		listLocation.addAll(new RookRule(ob, location).getAllLocationControl());
		listLocation.addAll(new BishopRule(ob, location).getAllLocationControl());
		return listLocation;
	}

}
