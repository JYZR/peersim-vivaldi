package com.lajv.location;

import peersim.config.Configuration;
import peersim.core.CommonState;

public class IspLocation implements Location {

	/**
	 * The number of ISPs
	 * 
	 * @config
	 */
	private static final String PAR_NUM_ISPS = "num_isps";

	/**
	 * The latency factor which will be multiplied to distance. Distance is in the interval (0,
	 * sqrt(2)).
	 * 
	 * @config
	 */
	private static final String PAR_LAT_FAC = "latency_factor";

	/**
	 * The latency which is added for nodes that different ISPs.
	 * 
	 * @config
	 */
	private static final String PAR_ISP_LAT = "isp_lat";

	double x;
	double y;
	public int isp;
	String prefix;
	double uploadCapacity;

	public static int num_isps;
	static int latency_factor;
	static int isp_latency;

	public IspLocation(String prefix) {
		this.prefix = prefix;
		num_isps = Configuration.getInt(prefix + "." + PAR_NUM_ISPS, 1);
		latency_factor = Configuration.getInt(prefix + "." + PAR_LAT_FAC, 50);
		isp_latency = Configuration.getInt(prefix + "." + PAR_ISP_LAT, 50);
	}

	/**
	 * Randomize a location and an ISP. It is not a normal distribution however. 25% of nodes are
	 * expected to be between x:(0, 0.125) and y:(0, 0.125). More nodes will also have an ISP with
	 * lower index.
	 */
	public void randomize() {
		x = CommonState.r.nextDouble() * CommonState.r.nextDouble() * CommonState.r.nextDouble();
		y = CommonState.r.nextDouble() * CommonState.r.nextDouble() * CommonState.r.nextDouble();
		isp = Math.min(CommonState.r.nextInt(num_isps), CommonState.r.nextInt(num_isps));
		uploadCapacity = 0.5 + (1 - x * y) * CommonState.r.nextDouble() * 4.5;
	}

	public double latency(Location otherLocation) {
		IspLocation other = (IspLocation) otherLocation;
		if (isp == other.isp) {
			double xdiff = other.x - x;
			double ydiff = other.y - y;
			return latency_factor * Math.sqrt(xdiff * xdiff + ydiff * ydiff);
		} else {
			double latency = Math.sqrt(x * x + y * y);
			latency += Math.sqrt(other.x * other.x + other.y * other.y);
			return latency + isp_latency * (isp + other.isp) / num_isps;
		}
	}

	public Object clone() {
		IspLocation clone = null;
		try {
			clone = (IspLocation) super.clone();
		} catch (CloneNotSupportedException e) {
			// Never happens
		}
		return clone;
	}

	public double getUploadCapacity() {
		return uploadCapacity;
	}
}
