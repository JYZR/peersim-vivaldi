package com.lajv.vivaldi.dim2dh;

import com.lajv.vivaldi.VivaldiVector;

import peersim.core.CommonState;

public class Dim2DHeightVivaldiVector implements VivaldiVector {

	/**
	 * The X composant of this <code>Dim2DHeightVivaldiVector</code>.
	 * 
	 */
	public double x;

	/**
	 * The Y composant of this <code>Dim2DHeightVivaldiVector</code>.
	 */
	public double y;

	/**
	 * The height composant of this <code>Dim2DHeightVivaldiVector</code>.
	 */
	public double h;

	public Dim2DHeightVivaldiVector(double x, double y, double h) {
		this.x = x;
		this.y = y;
		this.h = h;
	}

	/**
	 * @see VivaldiVector#normalize()
	 */
	public void normalize() {
		// Make sure that the height composant is maximum one third of the length
		limitH();
		double length = length();
		// Nodes start at origo and thus the length could be 0, in this case a random vector is
		// generated.
		if (length == 0) {
			x = CommonState.r.nextDouble();
			y = CommonState.r.nextDouble();
			h = CommonState.r.nextDouble();
			limitH();
			length = length();
		}
		x /= length;
		y /= length;
		h /= length;
	}

	private void limitH() {
		double xy_length = Math.sqrt(x * x + y * y);
		if (h > xy_length / 2)
			h = xy_length / 2;
	}

	/**
	 * @see VivaldiVector#applyError(double)
	 */
	public void applyError(double error) {
		x *= error;
		y *= error;
		h *= error;
	}

	/**
	 * @see VivaldiVector#length()
	 */
	public double length() {
		return Math.sqrt(x * x + y * y) + h;
	}
}
