package org.newdawn.commet.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.newdawn.commet.message.Message;

public class ChatMessage implements Message {
	public static final int ID = 1;
	
	private String sender;
	private String message;
	
	public ChatMessage() {
		
	}
	
	public ChatMessage(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}

	public String getSender() {
		return sender;
	}
	
	public String getText() {
		return message;
	}
	
	public void decode(DataInputStream din) throws IOException {
		sender = din.readUTF();
		message = din.readUTF();
	}

	public void encode(DataOutputStream dout) throws IOException {
		dout.writeUTF(sender);
		dout.writeUTF(message);
	}

	public short getID() {
		return ID;
	}
}
