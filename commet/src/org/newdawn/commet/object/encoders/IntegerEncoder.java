package org.newdawn.commet.object.encoders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.newdawn.commet.object.FieldEncoder;

public class IntegerEncoder implements FieldEncoder {
	private Field field;
	
	public IntegerEncoder(Field field) {
		this.field = field;
		field.setAccessible(true);
	}
	
	public void decode(DataInputStream din, Object value) throws IOException {
		try {
			field.setInt(value, din.readInt());
		} catch (IllegalArgumentException e) {
			throw new IOException("Failed to set: "+field);
		} catch (IllegalAccessException e) {
			throw new IOException("Failed to set: "+field);
		}
	}

	public void encode(DataOutputStream dout, Object value) throws IOException {
		try {
			dout.writeInt(field.getInt(value));
		} catch (IllegalArgumentException e) {
			throw new IOException("Failed to get: "+field);
		} catch (IllegalAccessException e) {
			throw new IOException("Failed to get: "+field);
		}
	}

	
}
