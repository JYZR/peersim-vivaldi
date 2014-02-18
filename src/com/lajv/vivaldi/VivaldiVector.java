package com.lajv.vivaldi;

public interface VivaldiVector {

	/**
	 * Normalizes the vector, i.e. sets length to 1. If the vector is of length zero, a random
	 * vector will be generated.
	 */
	public void normalize();

	/**
	 * Applies (the possibly negative) error to calculate the force vector. This is done by
	 * multiplying the error with all composants.
	 * 
	 * @param error
	 *            The calculated error in estimated latency
	 */
	public void applyError(double error);

	/**
	 * Calculate the length of the vector
	 * 
	 * @return The length
	 */
	public double length();
}
