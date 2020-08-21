package swingy.model;

public class Coordinate {
	private int x;
	private int y;

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

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate() {
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Coordinate))
			return false;
		Coordinate other = (Coordinate) obj;
		return other.getX() == this.getX() && other.getY() == this.getY();
	}
}
