package com.lajv.location;

import com.lajv.NetworkNode;

import peersim.core.*;

/**
 * Initializes the network with network coordinates
 */
public class LocationInitializer implements Control {

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

//	/** Protocol identifier */
//	private final int pid;

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

		for (int i = 0; i < Network.size(); i++) {
			NetworkNode netNode = (NetworkNode) Network.get(i);
			netNode.location.randomize();
		}

		return false;
	}
}
