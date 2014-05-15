package com.lajv.vivaldi.dim2dh;

import java.text.DecimalFormat;

import com.lajv.vivaldi.VivaldiCoordinate;
import com.lajv.vivaldi.VivaldiVector;

/**
 * Class definining a syntethic two-dimensional coordinate for Vivaldi
 */
public class Dim2DHeightVivaldiCoordinate implements Cloneable, VivaldiCoordinate {

	/**
	 * The X coordinate of this <code>Dim2DHeightVivaldiCoordinate</code>.
	 */
	public double x;

	/**
	 * The Y coordinate of this <code>Dim2DHeightVivaldiCoordinate</code>.
	 */
	public double y;

	/**
	 * The height coordinate of this <code>Dim2DHeightVivaldiCoordinate</code>.
	 */
	public double h;

	/**
	 * @see Dim2DHeightVivaldiCoordinate#Dim2DHeightVivaldiCoordinate()
	 */
	public Dim2DHeightVivaldiCoordinate(String prefix) {
		x = 0;
		y = 0;
		h = 0;
	}

	/**
	 * Constructs and initializes a <code>Dim2DHeightVivaldiCoordinate</code> with coordinates in
	 * origo.
	 */
	public Dim2DHeightVivaldiCoordinate() {
		x = 0;
		y = 0;
		h = 0;
	}

	/**
	 * Constructs and initializes a <code>Dim2DHeightVivaldiCoordinate</code> with the specified
	 * coordinates.
	 * 
	 * @param x
	 *            the X coordinate of the newly constructed <code>VivaldiCoordinate</code>
	 * @param y
	 *            the Y coordinate of the newly constructed <code>VivaldiCoordinate</code>
	 * @param h
	 *            the height coordinate of the newly constructed <code>VivaldiCoordinate</code>
	 */
	public Dim2DHeightVivaldiCoordinate(double x, double y, double h) {
		this.x = x;
		this.y = y;
		this.h = h;
	}

	public void setLocation(double x, double y, double h) {
		this.x = x;
		this.y = y;
		this.h = h;
	}

	/**
	 * @see VivaldiCoordinate#distance(VivaldiCoordinate)
	 */
	public double distance(VivaldiCoordinate other) {
		return differenceVector(other).length();
	}

	/**
	 * @see VivaldiCoordinate#differenceVector(VivaldiCoordinate)
	 */
	public VivaldiVector differenceVector(VivaldiCoordinate other) {
		Dim2DHeightVivaldiCoordinate o = (Dim2DHeightVivaldiCoordinate) other;
		double vx = x - o.x;
		double vy = y - o.y;
		double vh = h + o.h;
		return new Dim2DHeightVivaldiVector(vx, vy, vh);
	}

	/**
	 * @see VivaldiVector#applyForceVector(VivaldiVector, double, double)
	 */
	public void applyForceVector(VivaldiVector vector, double correction_factor,
			double uncertainty_balance) {
		Dim2DHeightVivaldiVector v = (Dim2DHeightVivaldiVector) vector;
		x += v.x * correction_factor * uncertainty_balance;
		y += v.y * correction_factor * uncertainty_balance;
		h += v.h * correction_factor * uncertainty_balance;
		if (h < 0)
			h = 0;
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
		return "{x: " + df.format(x) + ", y: " + df.format(y) + ", h: " + df.format(h) + "}";
	}

	/**
	 * @see VivaldiCoordinate#update()
	 */
	@Override
	public void update(VivaldiCoordinate other) {
		Dim2DHeightVivaldiCoordinate o = (Dim2DHeightVivaldiCoordinate) other;
		x = o.x;
		y = o.y;
		h = o.h;
	}

	@Override
	public String toCSV() {
		return x + ", " + y + ", " + h;
	}
}
