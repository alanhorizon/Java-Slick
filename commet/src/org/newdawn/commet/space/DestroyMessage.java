package org.newdawn.commet.space;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.newdawn.commet.message.Message;

public class DestroyMessage implements Message {
	public static final short ID = 1001;
	
	private short id;
	private short clientID;

	public DestroyMessage() {
	}
	
	public DestroyMessage(short id, short clientID) {
		this.id = id;
		this.clientID = clientID;
	}
	
	public short getObjectID() {
		return id;
	}
	
	public short getOwnerID() {
		return clientID;
	}
	
	public void decode(DataInputStream din) throws IOException {
		id = din.readShort();
		clientID = din.readShort();
	}

	public void encode(DataOutputStream dout) throws IOException {
		dout.writeShort(id);
		dout.writeShort(clientID);
	}

	public short getID() {
		return ID;
	}

}
