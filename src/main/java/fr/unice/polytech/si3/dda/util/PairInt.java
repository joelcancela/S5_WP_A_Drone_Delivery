package fr.unice.polytech.si3.dda.util;

/**
 * The coordinates class represents the coordinates of a point.
 *
 * @author Jeremy Junac
 */
public class PairInt {

	/** The x position */
	protected int x;
	/** The y position */
	protected int y;
	
	/**
	 * Default constructor.
	 *
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 */
	public PairInt(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Instantiates a new coordinates.
	 *
	 * @param coor
	 *            the coor
	 */
	public PairInt(final PairInt coor) {
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
	 * @param x
	 *            the x to set
	 * @return this
	 */
	public PairInt setX(final int x) {
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
	 * @param y
	 *            the y to set
	 * @return this
	 */
	public PairInt setY(final int y) {
		this.y = y;
		return this;
	}
	
	/**
	 * Sets the size.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the pair int
	 */
	public PairInt setSize(final int x, final int y) {
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

	/**
	 * Test if two Coordinates have the same value.
	 *
	 * @param obj
	 *            the obj
	 * @return true if the two Coordinates are equivalent
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PairInt other = (PairInt) obj;
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
	 * @param coor
	 *            the coordinates to add
	 * @return a coordinates that represents the addition of this coordinates and arguments coordinates
	 */
	public PairInt plus(final PairInt coor) {
		x += coor.getX();
		y += coor.getY();
		return this;
	}

	/**
	 * Subtract the specified coordinates with this coordinates.
	 *
	 * @param coor
	 *            the coordinates to subtract
	 * @return a coordinates that represents the subtraction of this coordinates and arguments coordinates
	 */
	public PairInt minus(final PairInt coor) {
		x -= coor.getX();
		y -= coor.getY();
		return this;
	}
	
	/**
	 * Return a copy of this coordinates using the copy-constructor.
	 *
	 * @return the copy of this coordinates
	 */
	public PairInt copy() {
		return new PairInt(this);
	}

	/**
	 * Increment the x.
	 *
	 * @return this Coordinates
	 */
	public PairInt incX() {
		x++;
		return this;
	}

	/**
	 * Decrement the x.
	 *
	 * @return this Coordinates
	 */
	public PairInt decX() {
		x--;
		return this;
	}

	/**
	 * Increment the y.
	 *
	 * @return this Coordinates
	 */
	public PairInt incY() {
		y++;
		return this;
	}

	/**
	 * Decrement the y.
	 *
	 * @return this Coordinates
	 */
	public PairInt decY() {
		y--;
		return this;
	}

	/**
	 * Distance.
	 *
	 * @param other
	 *            the other
	 * @return the double
	 */
	public double distance(final PairInt other) {
		return Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
	}
	
	public PairInt multiply(int a) {
		x *= a;
		y *= a;
		return this;
	}
	
	public PairInt divide(int a) {
		x /= a;
		y /= a;
		return this;
	}
	
	public PairInt projectionOn(PairInt v) {
		return v.copy().multiply(scalar(v)).divide(v.norm());
	}
	
	public int scalar(PairInt v) {
		return x*v.x + y*v.y;
	}
	
	public int norm() {
		return scalar(this);
	}
	
}
