package org.newdawn.commet.space;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.commet.message.Message;

public class UpdateMessage implements Message {
	public static final short ID = 1002;
	
	private ArrayList<SharedObject> objectsToSend = new ArrayList<SharedObject>();
	
	public UpdateMessage() {	
	}
	
	public void add(SharedObject object) {
		objectsToSend.add(object);
	}
	
	public void decode(DataInputStream din) throws IOException {
		NetworkSpace space = NetworkSpaceHolder.getDefaultSpace();
		int count = din.readShort();
		
		for (int i=0;i<count;i++) {
			int id = din.readInt();
			SharedObject object = space.getSharedObjectByID(id);
			if (object == null) {
				throw new IOException("Recieved update for unknown object with id: "+id);
			}
			
			object.updateFrom(din);
		}
	}

	public void encode(DataOutputStream dout) throws IOException {
		NetworkSpace space = NetworkSpaceHolder.getDefaultSpace();
		dout.writeShort(objectsToSend.size());
		
		for (int i=0;i<objectsToSend.size();i++) {
			SharedObject object = objectsToSend.get(i);
			
			dout.writeInt(object.getCombinedID());
			object.updateTo(dout);
		}
	}

	public short getID() {
		return ID;
	}

}
