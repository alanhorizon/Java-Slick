package org.newdawn.commet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.newdawn.commet.transport.TransportChannel;
import org.newdawn.commet.transport.TransportFactory;
import org.newdawn.commet.transport.TransportServer;
import org.newdawn.commet.util.Log;

/**
 * Simple server to accept connection and broadcast data between
 * clients on the server.
 * 
 * @author kevin
 */
public class BroadcastServer extends DefaultServer {
	/**
	 * Create a simple broadcast server
	 * 
	 * @param port The port on which to run the server
	 * @throws IOException Indicates a failure to create the server
	 */
	public BroadcastServer(int port) throws IOException {
		super(port);
	}
	
	/**
	 * @see org.newdawn.commet.DefaultServer#dataRecieved(org.newdawn.commet.transport.TransportChannel, java.nio.ByteBuffer)
	 */
	@Override
	protected void dataRecieved(TransportChannel channel, ByteBuffer data) {
		if (broadcastData(channel, buffer)) {
			sendToAll(buffer, channel.getChannelID());
		}
	}

	/**
	 * Send the data provided to all connected channels apart from the one with the
	 * given index
	 * 
	 * @param data The data to send
	 * @param ignoreChannelID The ID of the channel to exclude
	 */
	protected void sendToAll(ByteBuffer data, int ignoreChannelID) {
		// then we're going to tell all the other players about the data. In this 
		// case I'm going to skip sending the data to the player who sent it. Sometimes
		// it's useful to do that tho
		for (int j=0;j<channels.size();j++) {
			// get and outgoing connection
			TransportChannel out = (TransportChannel) channels.get(j);
			
			// ignore the player than sent the data
			if (out.getChannelID() != ignoreChannelID) {
				// write the data out
				try {
					data.flip();
					out.write(data, true);
				} catch (IOException e) {
					Log.error(e);
					
					// if we failed to write to a connection then it's most likely
					// broken try and close it up
					try {
						out.close();
					} catch (IOException x) {
						// if the close fails theres nothing left to do really
						// so just log and ignore
						Log.error(x);
					}
				}
			}
		}
	}

	/**
	 * Check if a set of data should be broadcast to other clients
	 * 
	 * @param channel The channel the data was recieved on
	 * @param data The data to be broadcast
	 * @return True if the data should be broadcast to other clients
	 */
	protected boolean broadcastData(TransportChannel channel, ByteBuffer data) {
		return true;
	}
	
	/**
	 * @see org.newdawn.commet.DefaultServer#channelConnected(org.newdawn.commet.transport.TransportChannel)
	 */
	protected void channelConnected(TransportChannel channel) {
	}
	
	/**
	 * @see org.newdawn.commet.DefaultServer#channelDisconnected(org.newdawn.commet.transport.TransportChannel)
	 */
	protected void channelDisconnected(TransportChannel channel) {
	}
}
