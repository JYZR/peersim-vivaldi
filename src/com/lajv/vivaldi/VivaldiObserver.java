package com.lajv.vivaldi;

import com.lajv.NetworkNode;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;

public class VivaldiObserver implements Control {

	// ========================= Parameters ===============================
	// ====================================================================

	/**
	 * The protocol to operate on.
	 * 
	 * @config
	 */
	private static final String PAR_PROT = "protocol";

	/**
	 * Config parameter that determines the threshold for when the Vivaldi algorithm should stop the
	 * execution. If not defined, a negative value is used which makes sure the observer does not
	 * stop the simulation.
	 * 
	 * @config
	 */
	private static final String PAR_THRESHOLD = "threshold";

	// =========================== Fields =================================
	// ====================================================================

	/**
	 * The name of this observer in the configuration. Initialized by the constructor parameter.
	 */
	private final String name;

	/**
	 * Protocol identifier; obtained from config property {@link #PAR_PROT}.
	 * */
	private final int pid;

	/**
	 * Threshold for when to stop the execution; obtained from config property
	 * {@link #PAR_THRESHOLD}. The value is calculated as the sum of differences in distance in
	 * square.
	 * */
	private final double threshold;

	private double inital_sum, final_sum;
	private int iterations;

	// ==================== Constructor ===================================
	// ====================================================================

	public VivaldiObserver(String name) {
		this.name = name;
		pid = Configuration.getPid(name + "." + PAR_PROT);
		threshold = Configuration.getDouble(name + "." + PAR_THRESHOLD, -1);
		inital_sum = final_sum = iterations = 0;
	}

	// ====================== Methods =====================================
	// ====================================================================

	/**
	 * Prints the sum of errors calculated by the difference between real distances and estimated
	 * distance in square.
	 * 
	 * 
	 * @return if the sum of distances in square is less than the given {@value #PAR_THRESHOLD}.
	 */

	@Override
	public boolean execute() {
		double minError = 10000;
		double maxError = 0;
		double sum = 0;

		for (int i = 0; i < Network.size(); i++) {
			NetworkNode n1 = (NetworkNode) Network.get(i);
			VivaldiProtocol vp1 = (VivaldiProtocol) n1.getProtocol(pid);
			for (int j = i + 1; j < Network.size(); j++) {
				NetworkNode n2 = (NetworkNode) Network.get(j);
				VivaldiProtocol vp2 = (VivaldiProtocol) n2.getProtocol(pid);
				double latency = n1.location.latency(n2.location);
				double estLatency = vp1.vivCoord.distance(vp2.vivCoord);
				double error = Math.abs(latency - estLatency);
				minError = error < minError ? error : minError;
				maxError = error > maxError ? error : maxError;
				sum += error * error;
			}

		}
		if (iterations == 0)
			inital_sum = sum;

		if (iterations == Configuration.getInt("simulation.cycles") - 1) {
			final_sum = sum;
			System.out.println(name + ": Initial sum was " + inital_sum);
			System.out.println(name + ": Final sum is " + final_sum + " after " + iterations
					+ " cycles");
		}

		if (iterations % 100 == 0 || sum < threshold) {
			System.out.println(name + ": Sum is " + sum);
			System.out.println(name + ": Max error is " + maxError);
			System.out.println(name + ": Min error is " + minError);
			if (sum < threshold)
				System.out.println(name + ": Threshold reached after " + iterations + " cycles");
		}
		iterations++;

		/* Terminate if accuracy target is reached */
		return (sum < threshold);
	}
}
