package com.lajv.location;

public interface Location extends Cloneable {

	public void randomize();

	public double latency(Location location);

	public Object clone();

	public double getUploadCapacity();
}
