package com.lajv.vivaldi.dim2d;

import com.lajv.vivaldi.VivaldiVector;

import peersim.core.CommonState;

public class Dim2DVivaldiVector implements VivaldiVector {

	/**
	 * The X composant of this <code>Dim2DVivaldiVector</code>.
	 * 
	 */
	public double x;

	/**
	 * The Y composant of this <code>Dim2DVivaldiVector</code>.
	 */
	public double y;

	public Dim2DVivaldiVector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @see VivaldiVector#normalize()
	 */
	public void normalize() {
		double length = length();
		// Nodes start at origo and thus the length could be 0, in this case a random vector is
		// generated.
		if (length == 0) {
			x = CommonState.r.nextDouble();
			y = CommonState.r.nextDouble();
			length = length();
		}
		x /= length;
		y /= length;
	}

	/**
	 * @see VivaldiVector#applyError(double)
	 */
	public void applyError(double error) {
		x *= error;
		y *= error;
	}

	/**
	 * @see VivaldiVector#length()
	 */
	public double length() {
		return Math.sqrt(x * x + y * y);
	}
}
