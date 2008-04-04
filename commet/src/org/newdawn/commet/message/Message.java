package org.newdawn.commet.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Message {

	public short getID();
	
	public void encode(DataOutputStream dout) throws IOException;
	
	public void decode(DataInputStream din) throws IOException;
}
