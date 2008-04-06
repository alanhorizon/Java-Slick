package org.newdawn.commet.space;

import java.util.ArrayList;

public class NetworkSpaceHolder {
	private static ArrayList<NetworkSpace> spaces = new ArrayList<NetworkSpace>();
	
	public static NetworkSpace getDefaultSpace() {
		return spaces.get(0);
	}
	
	public static int registerSpace(NetworkSpace space) {
		spaces.add(space);
		return spaces.indexOf(space);
	}
}
