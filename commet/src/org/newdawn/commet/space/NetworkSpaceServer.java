package org.newdawn.commet.space;

import java.io.IOException;

import org.newdawn.commet.DefaultServer;
import org.newdawn.commet.transport.TransportChannel;
import org.newdawn.commet.util.Log;

/**
 * A default implemetation of a server that can maintain a network space. Note that
 * this server only supports a single network space. Future version should
 * allow multiple network spaces on a single server.
 * 
 * @author kevin
 */
public class NetworkSpaceServer extends DefaultServer {
	/** The interval at which to update clients */
	private static final int UPDATE_INTERVAL = 250;
	
	/** The space being maintained */
	protected NetworkSpace space = new NetworkSpace(UPDATE_INTERVAL);
	
	/**
	 * Create a new server
	 * 
	 * @param port The port on which the server should run
	 * @throws IOException Indicates a failure to start the server
	 */
	public NetworkSpaceServer(int port) throws IOException {
		super(port);
	}

	/**
	 * Get teh space being maintained
	 *  
	 * @return The space being maintained
	 */
	public NetworkSpace getSpace() {
		return space;
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
	public void channelDisconnected(TransportChannel channel) {
		space.removeChannel(channel);
	}

}
