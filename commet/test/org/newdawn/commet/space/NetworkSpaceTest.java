package org.newdawn.commet.space;

import java.io.IOException;

import org.newdawn.commet.transport.TransportChannel;

public class NetworkSpaceTest implements NetworkSpaceListener {

	public static void main(String[] argv) throws IOException {
		NetworkSpace space = new NetworkSpace("localhost", 12345, 250);
		space.addListener(new NetworkSpaceTest());
		
		Blob blob = new Blob();
		space.add(blob);
		
		while (true) {
			try { Thread.sleep(5); } catch (Exception e) {};
			space.update(5);
		}
	}

	public void objectAdded(NetworkSpace source, Object obj, short id, short ownerID) {
		System.out.println("Object added "+obj+" for: "+ownerID);
	}

	public void objectRemoved(NetworkSpace source, Object obj, short id, short ownerID) {
		System.out.println("Object removed "+obj+" for: "+ownerID);
	}

	public void channelDisconnected(TransportChannel channel) {
		System.out.println("Disconnection");
	}
}
