package com.lajv.vivaldi;

import com.lajv.NetworkNode;
import com.lajv.cyclon.CyclonProtocol;

import peersim.cdsim.CDProtocol;
import peersim.config.Configuration;
import peersim.config.FastConfig;
import peersim.core.CommonState;
import peersim.core.Linkable;
import peersim.core.Node;

public class VivaldiProtocol implements CDProtocol {

	// ========================= Parameters ===============================
	// ====================================================================

	/**
	 * Correction factor for Vivaldi algorithm. Defined as c_c in the Vivaldi paper. Should be
	 * between 0 (exclusive) and 1 (inclusive). Default is 0.05.
	 * 
	 * @config
	 */
	private static final String PAR_CORRECTION_FACTOR = "correction_factor";

	/**
	 * Uncertainty factor for Vivaldi algorithm. Defined as c_e in the Vivaldi paper. Should be
	 * between 0 (exclusive) and 1 (inclusive). Default is 1.
	 * 
	 * @config
	 */
	private static final String PAR_UNCERTAINTY_FACTOR = "uncertainty_factor";

	/**
	 * Parameter which specifies the implementation of <code>VivaldiCoordinate</code> to use.
	 * 
	 * @config
	 */
	private static final String PAR_COORD_IMPL = "coord_impl";

	/**
	 * Parameter which specifies the <code>CyclonProtocol</code> where coordinates should be updated
	 * after a Vivaldi cycle.
	 * 
	 * @config
	 */
	private static final String PAR_CYCLON_PROT = "cyclon_prot";

	// =========================== Fields =================================
	// ====================================================================

	public VivaldiCoordinate vivCoord;
	public double uncertainty;

	public static double correction_factor;
	public static final double default_correction_factor = 0.25;

	public static double uncertainty_factor;
	public static final double default_uncertainty_factor = 1;

	public double last_uncertainty_balance;
	public double last_move_distance;

	private int cyclonPid;

	// ==================== Constructor ===================================
	// ====================================================================

	public VivaldiProtocol(String prefix) {
		vivCoord = (VivaldiCoordinate) Configuration.getInstance(prefix + "." + PAR_COORD_IMPL);
		uncertainty = 1;

		correction_factor = Configuration.getDouble(prefix + "." + PAR_CORRECTION_FACTOR,
				default_correction_factor);
		if (correction_factor <= 0 || correction_factor > 1) {
			System.err.println("Bad correction_factor, setting it to default.");
			correction_factor = default_correction_factor;
		}

		uncertainty_factor = Configuration.getDouble(prefix + "." + PAR_UNCERTAINTY_FACTOR,
				default_uncertainty_factor);
		if (uncertainty_factor <= 0 || uncertainty_factor > 1) {
			System.err.println("Bad uncertainty_factor, setting it to default.");
			uncertainty_factor = default_uncertainty_factor;
		}

		cyclonPid = Configuration.getPid(prefix + "." + PAR_CYCLON_PROT);
	}

	// ====================== Methods =====================================
	// ====================================================================

	@Override
	public void nextCycle(Node node, int protocolID) {

		// Cast node to Network node which also has a location
		NetworkNode netNode = (NetworkNode) node;

		// Get the overlay protocol which has the peer connection
		int linkableID = FastConfig.getLinkable(protocolID);
		Linkable linkable = (Linkable) netNode.getProtocol(linkableID);

		if (linkable.degree() == 0)
			return;

		// Choose a random peer
		int i = CommonState.r.nextInt(linkable.degree());
		NetworkNode nbNode = (NetworkNode) linkable.getNeighbor(i);

		// Return if the selected peer is inactive
		if (!nbNode.isUp())
			return;

		// Get the Vivaldi protocol from the neighbor so these methods can be accessed
		VivaldiProtocol nbProt = (VivaldiProtocol) nbNode.getProtocol(protocolID);

		// Calculate the real distance which should simulate the RTT
		double rtt = netNode.location.latency(nbNode.location);
		// Calculate the real distance which is the estimated RTT
		double est_rtt = vivCoord.distance(nbProt.vivCoord);
		// Calculate the error in estimation of RTT (can be negative)
		double error = rtt - est_rtt;
		// Calculate the relative error in estimation of RTT
		double relative_error = Math.abs(error) / rtt;
		// If rtt by chance is 0, set relative error to 1
		if (Double.isInfinite(relative_error))
			// srelative_error = 1;
			// *************
			// *** DEBUG ***
			// *************
			if (relative_error < 0 || relative_error > 1) {
				// System.out.println(node.getID() + ": relative_error is " + relative_error);
				// relative_error = 1;
			}

		// Calculate the uncertainty balance between the two nodes
		double uncertainty_balance = uncertainty / (uncertainty + nbProt.uncertainty);

		// *************
		// *** DEBUG ***
		// *************
		if (uncertainty_balance < 0 || uncertainty_balance > 1) {
			// System.out.println(node.getID() + ": UNCERTAINTY_BALANCE IS " + uncertainty_balance);
			// uncertainty_balance = 1;
		}

		// Calculate new uncertainty with respect to relative error and uncertainty balance
		uncertainty = (relative_error * uncertainty_factor * uncertainty_balance)
				+ (uncertainty * (1 - uncertainty_factor * uncertainty_balance));

		// *************
		// *** DEBUG ***
		// *************
		if (uncertainty < 0 || uncertainty > 1) {
			// System.out.println(node.getID() + ": UNCERTAINTY IS " + uncertainty);
			// uncertainty = 1;
		}

		// Vector representing the distance in estimated coordinates
		VivaldiVector vector = vivCoord.differenceVector(nbProt.vivCoord);

		/*
		 * Normalize vector, i.e. set length to 1
		 */
		vector.normalize();

		/*
		 * Apply (the possibly negative) error to calculate the force vector
		 */
		vector.applyError(error);

		/*
		 * Adapt force vecor with the correction factor and the uncertainty balance before applying
		 * it to the estimated coordinates
		 */

		vivCoord.applyForceVector(vector, correction_factor, uncertainty_balance);

		last_uncertainty_balance = uncertainty_balance;
		vector.applyError(correction_factor * uncertainty_balance);
		last_move_distance = vector.length();

		/*
		 * Update the cyclon protocol with the received coordinate
		 */
		CyclonProtocol cycProt = (CyclonProtocol) node.getProtocol(cyclonPid);
		cycProt.updateCoord(nbNode, nbProt.vivCoord);
	}

	@Override
	public Object clone() {
		VivaldiProtocol vp = null;
		try {
			vp = (VivaldiProtocol) super.clone();
		} catch (CloneNotSupportedException e) {
			// never happens
		}
		vp.vivCoord = (VivaldiCoordinate) vivCoord.clone();
		return vp;
	}

	public VivaldiCoordinate getCoord() {
		return vivCoord;
	}

}
