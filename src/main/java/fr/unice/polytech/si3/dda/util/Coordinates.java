package fr.unice.polytech.si3.dda.util;

/**
 * The coordinates class represents the coordinates of a point.
 *
 * @author Jeremy Junac
 */
public class Coordinates {

	/**
	 * The x position
	 */
	protected int x;
	/**
	 * The y position
	 */
	protected int y;

	/**
	 * Default constructor.
	 *
	 * @param x the x position
	 * @param y the y position
	 */
	public Coordinates(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Instantiates a new coordinates.
	 *
	 * @param coor the coor
	 */
	public Coordinates(final Coordinates coor) {
		this(coor.getX(), coor.getY());
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X.
	 *
	 * @param x the x to set
	 * @return this
	 */
	public Coordinates setX(final int x) {
		this.x = x;
		return this;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y.
	 *
	 * @param y the y to set
	 * @return this
	 */
	public Coordinates setY(final int y) {
		this.y = y;
		return this;
	}

	/**
	 * Sets the size.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the pair int
	 */
	public Coordinates setSize(final int x, final int y) {
		setX(x);
		setY(y);
		return this;
	}

	/**
	 * Return a String that represents the coordinates.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 0711;
		result = 37 * result + x;
		result = 37 * result + y;
		return result;
	}

	/**
	 * Add the specified coordinates with this coordinates.
	 *
	 * @param coor the coordinates to add
	 * @return a coordinates that represents the addition of this coordinates and arguments coordinates
	 */
	public Coordinates plus(final Coordinates coor) {
		x += coor.getX();
		y += coor.getY();
		return this;
	}

	/**
	 * Subtract the specified coordinates with this coordinates.
	 *
	 * @param coor the coordinates to subtract
	 * @return a coordinates that represents the subtraction of this coordinates and arguments coordinates
	 */
	public Coordinates minus(final Coordinates coor) {
		x -= coor.getX();
		y -= coor.getY();
		return this;
	}

	/**
	 * Return a copy of this coordinates using the copy-constructor.
	 *
	 * @return the copy of this coordinates
	 */
	public Coordinates copy() {
		return new Coordinates(this);
	}

	/**
	 * Returns the distance between two Coordinates
	 *
	 * @param other the other
	 * @return the double
	 */
	public double distance(final Coordinates other) {
		return Math.sqrt(Math.pow((double) x - other.getX(), 2) + Math.pow((double) y - other.getY(), 2));
	}


}
