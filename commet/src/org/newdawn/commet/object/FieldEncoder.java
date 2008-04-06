package org.newdawn.commet.object;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface FieldEncoder {
	
	public void encode(DataOutputStream dout, Object value) throws IOException;
	
	public void decode(DataInputStream din, Object value) throws IOException;
}
