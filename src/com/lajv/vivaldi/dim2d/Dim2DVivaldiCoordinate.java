package com.lajv.vivaldi.dim2d;

import java.text.DecimalFormat;

import com.lajv.vivaldi.VivaldiCoordinate;
import com.lajv.vivaldi.VivaldiVector;

/**
 * Class definining a syntethic two-dimensional coordinate for Vivaldi
 */
public class Dim2DVivaldiCoordinate implements Cloneable, VivaldiCoordinate {

	/**
	 * The X coordinate of this <code>Dim2DVivaldiCoordinate</code>.
	 */
	public double x;

	/**
	 * The Y coordinate of this <code>Dim2DVivaldiCoordinate</code>.
	 */
	public double y;

	/**
	 * @see Dim2DVivaldiCoordinate#Dim2DVivaldiCoordinate()
	 */
	public Dim2DVivaldiCoordinate(String prefix) {
		x = 0;
		y = 0;
	}

	/**
	 * Constructs and initializes a <code>Dim2DVivaldiCoordinate</code> with coordinates in origo.
	 */
	public Dim2DVivaldiCoordinate() {
		x = 0;
		y = 0;
	}

	/**
	 * Constructs and initializes a <code>Dim2DVivaldiCoordinate</code> with the specified
	 * coordinates.
	 * 
	 * @param x
	 *            the X coordinate of the newly constructed <code>VivaldiCoordinate</code>
	 * @param y
	 *            the Y coordinate of the newly constructed <code>VivaldiCoordinate</code>
	 */
	public Dim2DVivaldiCoordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @see VivaldiCoordinate#distance(VivaldiCoordinate)
	 */
	public double distance(VivaldiCoordinate pt) {
		Dim2DVivaldiCoordinate other = (Dim2DVivaldiCoordinate) pt;
		double px = other.x - x;
		double py = other.y - y;
		return Math.sqrt(px * px + py * py);
	}

	/**
	 * @see VivaldiCoordinate#differenceVector(VivaldiCoordinate)
	 */
	public VivaldiVector differenceVector(VivaldiCoordinate other) {
		Dim2DVivaldiCoordinate o = (Dim2DVivaldiCoordinate) other;
		double vx = x - o.x;
		double vy = y - o.y;
		return new Dim2DVivaldiVector(vx, vy);
	}

	/**
	 * @see VivaldiVector#applyForceVector(VivaldiVector, double, double)
	 */
	public void applyForceVector(VivaldiVector vector, double correction_factor,
			double uncertainty_balance) {
		Dim2DVivaldiVector v = (Dim2DVivaldiVector) vector;
		x += v.x * correction_factor * uncertainty_balance;
		y += v.y * correction_factor * uncertainty_balance;
	}

	/**
	 * @see VivaldiCoordinate#clone()
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// Should not happen, since we are Cloneable
			throw new InternalError();
		}
	}

	/**
	 * Returns a <code>String</code> that represents the value of this
	 * <code>VivaldiCoordinate</code>.
	 * 
	 * @return a string representation of this <code>VivaldiCoordinate</code>.
	 */
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return "{x: " + df.format(x) + ", y: " + df.format(y) + "}";
	}

	/**
	 * @see VivaldiCoordinate#update()
	 */
	@Override
	public void update(VivaldiCoordinate other) {
		Dim2DVivaldiCoordinate o = (Dim2DVivaldiCoordinate) other;
		x = o.x;
		y = o.y;
	}

	@Override
	public String toCSV() {
		return x + "," + y;
	}
}
