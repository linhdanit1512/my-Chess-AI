package core;

/**
 * 
 * @author linhdan
 * 
 *         Lop vi tri tren ban co, dang ma tran
 */
public class Location {
	private int x, y;

	/**
	 * 
	 * @param x:
	 *            toa do x
	 * @param y:
	 *            toa do y
	 */
	public Location(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @param locat:
	 *            toa do dang chu
	 */
	public Location(String locat) {
		this.x = createLocation(locat).getX();
		this.y = createLocation(locat).getY();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getXString() {
		return 8 - x;
	}

	public char getYString() {
		char result = 0;
		switch (y) {
		case 0:
			result = 'a';
			break;
		case 1:
			result = 'b';
			break;
		case 2:
			result = 'c';
			break;
		case 3:
			result = 'd';
			break;
		case 4:
			result = 'e';
			break;
		case 5:
			result = 'f';
			break;
		case 6:
			result = 'g';
			break;
		case 7:
			result = 'h';
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 
	 * @param locat:
	 *            nhap vao dang ten cua o VD: A1, A2, B2,...
	 * @return Vi tri x, y dang index trong matrix (7,0), (7,1), (6,1)
	 */
	public Location createLocation(String locat) {
		char[] c = locat.toUpperCase().toCharArray();
		int x = Integer.parseInt((int) (8 - Integer.parseInt("" + c[1])) + "");
		int y = 0;
		switch (c[0]) {
		case 'A':
			y = 0;
			break;
		case 'B':
			y = 1;
			break;
		case 'C':
			y = 2;
			break;
		case 'D':
			y = 3;
			break;
		case 'E':
			y = 4;
			break;
		case 'F':
			y = 5;
			break;
		case 'G':
			y = 6;
			break;
		case 'H':
			y = 7;
			break;
		default:
			break;
		}
		return new Location(x, y);
	}

	@Override
	public String toString() {
		return "[ " + x + " , " + y + " ]";
	}

	public String toWordString() {

		return "" + getYString() + getXString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Location other = (Location) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
