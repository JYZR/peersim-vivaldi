package com.lajv.location;

import com.lajv.NetworkNode;

import peersim.core.*;
import peersim.dynamics.NodeInitializer;

/**
 * Initializes the network with network coordinates
 */
public class LocationInitializer implements NodeInitializer, Control {

	// --------------------------------------------------------------------------
	// Parameters
	// --------------------------------------------------------------------------

	// /**
	// * Upper end for x.
	// *
	// * @config
	// */
	// private static final String PAR_X_MAX = "x_max";
	//
	// /**
	// * Lower end for x. Defaults to -{@value #PAR_X_MAX}.
	// *
	// * @config
	// */
	// private static final String PAR_X_MIN = "x_min";
	//
	// /**
	// * Upper end for x.
	// *
	// * @config
	// */
	// private static final String PAR_Y_MAX = "y_max";
	//
	// /**
	// * Lower end for x. Defaults to -{@value #PAR_Y_MAX}.
	// *
	// * @config
	// */
	// private static final String PAR_Y_MIN = "y_min";

	// /**
	// * @config
	// */
	// private static final String PAR_PROT = "protocol";

	// --------------------------------------------------------------------------
	// Fields
	// --------------------------------------------------------------------------

	// /** Boundary values */
	// private final int x_max;
	// private final int x_min;
	// private final int y_max;
	// private final int y_min;

	// /** Protocol identifier */
	// private final int pid;

	private String prefix;

	// --------------------------------------------------------------------------
	// Initialization
	// --------------------------------------------------------------------------

	/**
	 * Standard constructor that reads the configuration parameters. Invoked by the simulation
	 * engine.
	 * 
	 * @param prefix
	 *            the configuration prefix for this class
	 */
	public LocationInitializer(String prefix) {
		this.prefix = prefix;

		// x_max = Configuration.getInt(prefix + "." + PAR_X_MAX);
		// x_min = Configuration.getInt(prefix + "." + PAR_X_MIN, -x_max);
		// y_max = Configuration.getInt(prefix + "." + PAR_Y_MAX);
		// y_min = Configuration.getInt(prefix + "." + PAR_Y_MIN, -y_max);
		// pid = Configuration.getPid(prefix + "." + PAR_PROT);

	}

	// --------------------------------------------------------------------------
	// Methods
	// --------------------------------------------------------------------------

	/**
	 * 
	 * @return always false
	 */
	public boolean execute() {

		int numOfDistances = 0;
		double sumOfDistances = 0;
		for (int i = 0; i < Network.size(); i++) {
			NetworkNode node = (NetworkNode) Network.get(i);
			initialize(node);
			for (int j = i - 1; j >= 0; j--) {
				NetworkNode otherNode = (NetworkNode) Network.get(j);
				sumOfDistances += node.location.latency(otherNode.location);
				numOfDistances++;
			}
		}
		double avgDistance = sumOfDistances / numOfDistances;
		System.out.println(prefix + " : Average distance between nodes: " + avgDistance);
		return false;
	}

	/**
	 * @see peersim.dynamics.NodeInitializer#initialize(peersim.core.Node)
	 */
	public void initialize(Node n) {
		NetworkNode netNode = (NetworkNode) n;
		netNode.location.randomize();
	}
}
