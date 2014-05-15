package com.lajv.vivaldi;

/**
 * Interface definining a syntethic coordinate for Vivaldi. The interface unites coordinates of
 * different dimensions.
 */
public interface VivaldiCoordinate extends Cloneable {

	/**
	 * Returns the distance from this <code>VivaldiCoordinate</code> to a specified
	 * <code>VivaldiCoordinate</code>.
	 * 
	 * @param other
	 *            the specified coordinate to be measured against this
	 *            <code>VivaldiCoordinate</code>
	 * @return the distance between this <code>VivaldiCoordinate</code> and the specified
	 *         <code>VivaldiCoordinate</code>.
	 */
	public double distance(VivaldiCoordinate other);

	/**
	 * Returns a vector which stretches between the two coordinates
	 * 
	 * @param other
	 *            the specified coordinate used to calculate the vector
	 * 
	 * @return The difference vector
	 */
	public VivaldiVector differenceVector(VivaldiCoordinate other);

	/**
	 * Changes the coordinate according to the given force vector. The change is limited by
	 * multiplication of correction factor and uncertainty balance.
	 * 
	 * @param vector
	 * @param correction_factor
	 * @param uncertainty_balance
	 */
	public void applyForceVector(VivaldiVector vector, double correction_factor,
			double uncertainty_balance);

	/**
	 * Creates a new object of the same class and with the same contents as this object.
	 * 
	 * @return a clone of this instance.
	 */
	public Object clone();

	/**
	 * Updates this <code>VivaldiCoordinate</code> to be equal to the specified
	 * <code>VivaldiCoordinate</code>.
	 * 
	 * @param coord
	 *            the specified coordinate from which values will be copied
	 */
	public void update(VivaldiCoordinate other);

	public String toCSV();

}
