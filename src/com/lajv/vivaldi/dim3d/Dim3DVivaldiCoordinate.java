package com.lajv.vivaldi.dim3d;

import java.text.DecimalFormat;

import com.lajv.vivaldi.VivaldiCoordinate;
import com.lajv.vivaldi.VivaldiVector;

/**
 * Class definining a syntethic two-dimensional coordinate for Vivaldi
 */
public class Dim3DVivaldiCoordinate implements Cloneable, VivaldiCoordinate {

	/**
	 * The X coordinate of this <code>Dim3DVivaldiCoordinate</code>.
	 */
	public double x;

	/**
	 * The Y coordinate of this <code>Dim3DVivaldiCoordinate</code>.
	 */
	public double y;

	/**
	 * The Y coordinate of this <code>Dim3DVivaldiCoordinate</code>.
	 */
	public double z;

	/**
	 * @see Dim3DVivaldiCoordinate#Dim3DVivaldiCoordinate()
	 */
	public Dim3DVivaldiCoordinate(String prefix) {
		x = 0;
		y = 0;
		z = 0;
	}

	/**
	 * Constructs and initializes a <code>Dim3DVivaldiCoordinate</code> with coordinates in origo.
	 */
	public Dim3DVivaldiCoordinate() {
		x = 0;
		y = 0;
		z = 0;
	}

	/**
	 * Constructs and initializes a <code>Dim3DVivaldiCoordinate</code> with the specified
	 * coordinates.
	 * 
	 * @param x
	 *            the X coordinate of the newly constructed <code>VivaldiCoordinate</code>
	 * @param y
	 *            the Y coordinate of the newly constructed <code>VivaldiCoordinate</code>
	 */
	public Dim3DVivaldiCoordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @see VivaldiCoordinate#distance(VivaldiCoordinate)
	 */
	public double distance(VivaldiCoordinate pt) {
		Dim3DVivaldiCoordinate other = (Dim3DVivaldiCoordinate) pt;
		double px = other.x - x;
		double py = other.y - y;
		double pz = other.z - z;
		return Math.sqrt(px * px + py * py + pz * pz);
	}

	/**
	 * @see VivaldiCoordinate#differenceVector(VivaldiCoordinate)
	 */
	public VivaldiVector differenceVector(VivaldiCoordinate other) {
		Dim3DVivaldiCoordinate o = (Dim3DVivaldiCoordinate) other;
		double vx = x - o.x;
		double vy = y - o.y;
		double vz = z - o.z;
		return new Dim3DVivaldiVector(vx, vy, vz);
	}

	/**
	 * @see VivaldiVector#applyForceVector(VivaldiVector, double, double)
	 */
	public void applyForceVector(VivaldiVector vector, double correction_factor,
			double uncertainty_balance) {
		Dim3DVivaldiVector v = (Dim3DVivaldiVector) vector;
		x += v.x * correction_factor * uncertainty_balance;
		y += v.y * correction_factor * uncertainty_balance;
		z += v.z * correction_factor * uncertainty_balance;
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
		return "{x: " + df.format(x) + ", y: " + df.format(y) + ", z: " + df.format(z) + "}";
	}

	/**
	 * @see VivaldiCoordinate#update()
	 */
	@Override
	public void update(VivaldiCoordinate other) {
		Dim3DVivaldiCoordinate o = (Dim3DVivaldiCoordinate) other;
		x = o.x;
		y = o.y;
		z = o.z;
	}

	@Override
	public String toCSV() {
		return x + "," + y + "," + z;
	}
}
