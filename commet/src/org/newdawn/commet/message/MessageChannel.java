package org.newdawn.commet.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import org.newdawn.commet.transport.TransportChannel;
import org.newdawn.commet.transport.TransportFactory;

public class MessageChannel {
	private TransportChannel channel;
	private ByteBuffer buffer = ByteBuffer.allocate(4096);
	private MessageFactory factory;
	
	public MessageChannel(MessageFactory factory, String host, int port) throws IOException {
		this(factory, TransportFactory.createChannel(host, port));
	}
	
	public MessageChannel(MessageFactory factory, TransportChannel channel) {
		this.channel = channel;
		this.factory = factory;
	}
	
	public Message read() throws IOException {
		buffer.clear();
		int read = channel.read(buffer);
		
		if (read > 0) {
			return decode(factory, buffer);
		}
		
		return null;
	}
	
	public void write(Message message, boolean reliable) throws IOException {
		encode(message, buffer);
		
		buffer.flip();
		channel.write(buffer, reliable);
	}
	
	public static Message decode(MessageFactory factory, ByteBuffer buffer) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
		DataInputStream din = new DataInputStream(in);
		
		Message message = factory.createMessageFor(din.readShort());
		message.decode(din);
		
		return message;
	}
	
	public static void encode(Message message, ByteBuffer target) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		
		dout.writeShort(message.getID());
		message.encode(dout);

		target.clear();
		target.put(out.toByteArray());
	}
	
	/**
	 * Close the channel, releasing any underlying resources
	 * 
	 * @throws IOException Indicates an IO failure occured while closing
	 */
	public void close() throws IOException {
		channel.close();
	}
	
	
	/**
	 * Get the address of the socket at the other end of the 
	 * channel
	 * 
	 * @return The address of the remote end of the channel
	 */
	public SocketAddress getRemoteSocketAddress() {
		return channel.getRemoteSocketAddress();
	}
	
	/**
	 * Check if the channel has been closed
	 * 
	 * @return True if the channel has been closed
	 */
	public boolean isClosed() {
		return channel.isClosed();
	}
	
	/**
	 * Get the ID given by the server for this connection
	 * 
	 * @return The ID given by the server for this connection
	 */
	public int getChannelID() {
		return channel.getChannelID();
	}
}
