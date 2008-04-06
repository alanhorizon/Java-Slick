package org.newdawn.commet.example.message;

import java.io.IOException;

import org.newdawn.commet.example.lowlevel.LowLevelTankServer;
import org.newdawn.commet.message.MessageBroadcastServer;
import org.newdawn.commet.message.MessageChannel;
import org.newdawn.commet.util.Log;

public class MessageTankServer extends MessageBroadcastServer {

	public MessageTankServer(int port) throws IOException {
		super(new TankMessageFactory(), port);
	}

	/**
	 * @see org.newdawn.commet.message.MessageBroadcastServer#channelDisconnected(org.newdawn.commet.message.MessageChannel)
	 */
	@Override
	protected void channelDisconnected(MessageChannel channel) {
		super.channelDisconnected(channel);
		
		try {
			sendToAll(new TankRemoveMessage(channel.getChannelID()), -1);
		} catch (IOException e) {
			Log.error(e);
		}
	}

	/**
	 * Entry point to the low level tank example
	 * 
	 * @param argv The arguments passed into the example tank server
	 */
	public static void main(String argv[]) {
		// create the server and start it
		try {
			MessageTankServer server = new MessageTankServer(12345);
			server.start();
		} catch (IOException e) {
			Log.error(e);
		}
	}
}
