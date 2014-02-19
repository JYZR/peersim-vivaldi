package com.lajv.vivaldi.dim3d;

import com.lajv.vivaldi.VivaldiVector;

import peersim.core.CommonState;

public class Dim3DVivaldiVector implements VivaldiVector {

	/**
	 * The X composant of this <code>Dim3DVivaldiVector</code>.
	 * 
	 */
	public double x;

	/**
	 * The Y composant of this <code>Dim3DVivaldiVector</code>.
	 */
	public double y;

	/**
	 * The Z composant of this <code>Dim3DVivaldiVector</code>.
	 */
	public double z;

	public Dim3DVivaldiVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
			z = CommonState.r.nextDouble();
			length = length();
		}
		x /= length;
		y /= length;
		z /= length;
	}

	/**
	 * @see VivaldiVector#applyError(double)
	 */
	public void applyError(double error) {
		x *= error;
		y *= error;
		z *= error;
	}

	/**
	 * @see VivaldiVector#length()
	 */
	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}
}
