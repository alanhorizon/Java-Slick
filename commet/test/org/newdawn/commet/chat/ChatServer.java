package org.newdawn.commet.chat;

import java.io.IOException;

import org.newdawn.commet.BroadcastServer;

public class ChatServer extends BroadcastServer {

	public ChatServer(int port) throws IOException {
		super(port);
	}

	public static void main(String[] argv) throws IOException {
		ChatServer server = new ChatServer(12345);
		server.start();
	}
}
