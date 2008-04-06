package org.newdawn.commet.space;

import java.io.IOException;

import org.newdawn.commet.DefaultServer;
import org.newdawn.commet.transport.TransportChannel;
import org.newdawn.commet.util.Log;

public class NetworkSpaceServer extends DefaultServer implements NetworkSpaceListener {
	private static final int UPDATE_INTERVAL = 250;
	
	protected NetworkSpace space = new NetworkSpace(UPDATE_INTERVAL);
	
	public NetworkSpaceServer(int port) throws IOException {
		super(port);
		
		space.addListener(this);
	}

	/**
	 * @see org.newdawn.commet.DefaultServer#channelConnected(org.newdawn.commet.transport.TransportChannel)
	 */
	@Override
	protected void channelConnected(TransportChannel channel) {
		try {
			space.addChannel(channel);
		} catch (IOException e) {
			Log.error(e);
		}
	}

	/**
	 * @see org.newdawn.commet.DefaultServer#channelDisconnected(org.newdawn.commet.transport.TransportChannel)
	 */
	@Override
	public void channelDisconnected(TransportChannel channel) {
		space.removeChannel(channel);
	}

	public void objectAdded(NetworkSpace source, Object obj, short id, short ownerID) {
		System.out.println("Network Added: "+obj+" "+ownerID);
	}

	public void objectRemoved(NetworkSpace source, Object obj, short id, short ownerID) {
		System.out.println("Network Removed: "+obj+" "+ownerID);
	}

}
