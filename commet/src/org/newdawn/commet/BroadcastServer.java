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
public abstract class BroadcastServer {
	/** The transport we're using to recieve connections */
	protected TransportServer transport;
	/** True if the server is running */
	protected boolean running = true;
	/** The list of connected player channels */
	protected ArrayList<TransportChannel> channels = new ArrayList<TransportChannel>();
	/** The buffer used to read and write */
	protected ByteBuffer buffer = ByteBuffer.allocate(4096);
	
	/**
	 * Create a simple tank tank server
	 * 
	 * @param port The port on which to run the server
	 * @throws IOException Indicates a failure to create the server
	 */
	public BroadcastServer(int port) throws IOException {
		transport = TransportFactory.createServer(port);
	}
	
	/**
	 * Start the game server, broadcasting recieved data to all connected
	 */
	public void start() {
		while (running) {
			try {
				// wait for something to happen on the server, i.e. a connection to occur
				// or some data to arrive
				transport.waitForActivity();
			} catch (IOException e) {
				// a failure during the wait generally means the server has closed. might as well
				// give up now.
				Log.error(e);
				running = false;
				return;
			}
			
			try {
				// check for new connections, we keep looping until there arn't any more
				// though in most cases the default connection waiting list is 5 connections under 
				// TCP, UDP connections can happen very quickly
				TransportChannel channel;
				do {
					channel = transport.accept();
					if (channel != null) {
						channels.add(channel);
						channelConnected(channel);
					}
				} while (channel != null);
			} catch (IOException e) {
				// a failure during accept doesn't really hurt. Other pending connections will 
				// get picked up next loop and the connection coming in will just be ignored
				Log.error(e);
			}
			
			// cycle through all the channels attempting to read from them. It's very important
			// that all channels are read each time since the only way we can detect closure
			// is on read. If you want to go one step lower you can use selectors to determine
			// which channels need reading but in general this just seems to confuse people. The 
			// overhead of reading a non-blocking connection with no data on it is tiny so this
			// seems a small sacrifice for an easier API
			for (int i=0;i<channels.size();i++) {
				TransportChannel channel = channels.get(i);
				buffer.clear();

				try {
					int read = channel.read(buffer);
					
					if (read > 0) {
						if (broadcastData(channel, buffer)) {
							sendToAll(buffer, channel.getChannelID());
						}
					} 
					if (read < 0) {
						channels.remove(i);
						i--;
						channelDisconnected(channel);
					}
				} catch (IOException e) {
					// if we fail reading/writing data we can lose some information best to wrap
					// each layer in it's own handler. Just ignore failed reads
					Log.error(e);
					continue;
				}
			}
		}
	}
	
	/**
	 * Send the data provided to all connected channels apart from the one with the
	 * given index
	 * 
	 * @param data The data to send
	 * @param index The index of the channel to exclude
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

	protected boolean broadcastData(TransportChannel channel, ByteBuffer data) {
		return true;
	}
	
	protected void channelConnected(TransportChannel channel) {
	}
	
	protected void channelDisconnected(TransportChannel channel) {
	}
}
