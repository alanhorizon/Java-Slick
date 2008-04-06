package org.newdawn.commet.space;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.newdawn.commet.message.Message;

public class CreateMessage implements Message {
	public static final short ID = 1000;
	
	private String className;
	private short id;
	private short clientID;
	
	public CreateMessage() {
	}
	
	public CreateMessage(String className, short id, short clientID) {
		this.className = className;
		this.id = id;
		this.clientID = clientID;
	}
	
	public String getClassName() {
		return className;
	}
	
	public short getObjectID() {
		return id;
	}
	
	public short getOwnerID() {
		return clientID;
	}
	
	public void decode(DataInputStream din) throws IOException {
		className = din.readUTF();
		id = din.readShort();
		clientID = din.readShort();
	}

	public void encode(DataOutputStream dout) throws IOException {
		dout.writeUTF(className);
		dout.writeShort(id);
		dout.writeShort(clientID);
	}

	public short getID() {
		return ID;
	}

}
