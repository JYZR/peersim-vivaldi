package com.lajv;

import com.lajv.location.Location;

import peersim.config.Configuration;
import peersim.core.GeneralNode;

public class NetworkNode extends GeneralNode implements Comparable<NetworkNode> {

	public Location location;

	public NetworkNode(String prefix) {
		super(prefix);
		location = (Location) Configuration.getInstance(prefix + "." + "loc_impl");
	}

	@Override
	public Object clone() {
		NetworkNode clone = (NetworkNode) super.clone();
		clone.location = (Location) location.clone();
		return clone;
	}

	@Override
	public int compareTo(NetworkNode o) {
		return (int) (getID() - o.getID());
	}
}
