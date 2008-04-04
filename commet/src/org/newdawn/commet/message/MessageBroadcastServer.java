package org.newdawn.commet.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.newdawn.commet.BroadcastServer;
import org.newdawn.commet.transport.TransportChannel;
import org.newdawn.commet.util.Log;

public class MessageBroadcastServer extends BroadcastServer {
	private HashMap<TransportChannel, MessageChannel> channelMap = new HashMap<TransportChannel, MessageChannel>();
	private MessageFactory factory;
	private ByteBuffer buffer = ByteBuffer.allocate(4096);
	
	public MessageBroadcastServer(MessageFactory factory, int port) throws IOException {
		super(port);
	
		this.factory = factory;
	}

	/**
	 * @see org.newdawn.commet.BroadcastServer#broadcastData(org.newdawn.commet.transport.TransportChannel, java.nio.ByteBuffer)
	 */
	@Override
	protected boolean broadcastData(TransportChannel channel, ByteBuffer data) {
		try {
			return broadcastMessage(channelMap.get(channel), MessageChannel.decode(factory, data));
		} catch (IOException e) {
			Log.error(e);
			return true;
		}
	}

	protected void sendToAll(Message message, int ignoreChannelID) throws IOException {
		buffer.clear();
		MessageChannel.encode(message, buffer);
		
		super.sendToAll(buffer, ignoreChannelID);
	}
	
	/**
	 * @see org.newdawn.commet.BroadcastServer#channelConnected(org.newdawn.commet.transport.TransportChannel)
	 */
	@Override
	protected void channelConnected(TransportChannel channel) {
		super.channelConnected(channel);
		
		channelMap.put(channel, new MessageChannel(factory, channel));
		channelConnected(channelMap.get(channel));
	}

	/**
	 * @see org.newdawn.commet.BroadcastServer#channelDisconnected(org.newdawn.commet.transport.TransportChannel)
	 */
	@Override
	protected void channelDisconnected(TransportChannel channel) {
		// TODO Auto-generated method stub
		super.channelDisconnected(channel);
		
		channelDisconnected(channelMap.remove(channel));
	}

	protected boolean broadcastMessage(MessageChannel channel, Message message) {
		return true;
	}

	protected void channelConnected(MessageChannel channel) {
	}
	
	protected void channelDisconnected(MessageChannel channel) {
	}
}
