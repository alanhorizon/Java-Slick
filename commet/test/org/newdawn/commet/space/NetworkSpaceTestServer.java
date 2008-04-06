package org.newdawn.commet.space;

import java.io.IOException;

public class NetworkSpaceTestServer extends NetworkSpaceServer {

	public NetworkSpaceTestServer(int port) throws IOException {
		super(port);
	}

	public void update(int delta) {
		checkConnect();
		space.update(delta);
	}
	
	public static void main(String[] arg) throws IOException {
		NetworkSpaceTestServer server = new NetworkSpaceTestServer(12345);
		while (true) {
			server.update(10);
			try { Thread.sleep(10); } catch (Exception e) {};
		}
	}
}
