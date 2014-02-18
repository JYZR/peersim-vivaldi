package com.lajv.location;

import peersim.core.CommonState;

public class SimpleLocation implements Location, Cloneable {

	double x;
	double y;

	public SimpleLocation(String prefix) {
	}

	@Override
	public void randomize() {
		x = CommonState.r.nextDouble();
		y = CommonState.r.nextDouble();
	}

	public double latency(Location otherLocation) {
		SimpleLocation other = (SimpleLocation) otherLocation;
		double xdiff = other.x - x;
		double ydiff = other.y - y;
		return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
	}

	public Object clone() {
		SimpleLocation clone = null;
		try {
			clone = (SimpleLocation) super.clone();
		} catch (CloneNotSupportedException e) {
			// Never happens
		}
		return clone;
	}
}
