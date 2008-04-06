package org.newdawn.commet.example.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.newdawn.commet.message.Message;

public class TankRemoveMessage implements Message {
	public static final short ID = 2;
	
	private int channelID;
	
	public TankRemoveMessage() {
	}
	
	public TankRemoveMessage(int channelID) {
		this.channelID = channelID;
	}
	
	public int getChannelID() {
		return channelID;
	}
	
	public void decode(DataInputStream din) throws IOException {
		channelID = din.readInt();
	}

	public void encode(DataOutputStream dout) throws IOException {
		dout.writeInt(channelID);
	}

	public short getID() {
		return ID;
	}

}
