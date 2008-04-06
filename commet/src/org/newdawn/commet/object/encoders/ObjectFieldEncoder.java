package org.newdawn.commet.object.encoders;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.newdawn.commet.object.ClassEncoderRegistry;
import org.newdawn.commet.object.FieldEncoder;
import org.newdawn.commet.object.ObjectEncoder;

public class ObjectFieldEncoder implements FieldEncoder {
	private ObjectEncoder encoder;
	private Field field;
	
	public ObjectFieldEncoder(Field field) {
		this.field = field;
		field.setAccessible(true);
		encoder = ClassEncoderRegistry.getEncoder(field.getType());
	}
	
	public void decode(DataInputStream din, Object value) throws IOException {
		if (din.readBoolean()) {
			try {
				Object subValue = field.get(value);
				encoder.decode(din, subValue);
			} catch (IllegalArgumentException e) {
				throw new IOException("Failed to access field: "+field);
			} catch (IllegalAccessException e) {
				throw new IOException("Failed to access field: "+field);
			}
		}
	}

	public void encode(DataOutputStream dout, Object value) throws IOException {
		try {
			Object subValue = field.get(value);
			if (subValue != null) {
				dout.writeBoolean(true);
				encoder.encode(dout, subValue);
			} else {
				dout.writeBoolean(false);
			}
		} catch (IllegalArgumentException e) {
			throw new IOException("Failed to access field: "+field);
		} catch (IllegalAccessException e) {
			throw new IOException("Failed to access field: "+field);
		}
	}

}
