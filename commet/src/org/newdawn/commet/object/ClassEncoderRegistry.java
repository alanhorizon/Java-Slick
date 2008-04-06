package org.newdawn.commet.object;

import java.util.HashMap;


public class ClassEncoderRegistry {
	private static HashMap<Class<?>, ObjectEncoder> encoders = new HashMap<Class<?>, ObjectEncoder>();
	
	public static ObjectEncoder getEncoder(Class<?> clazz) throws ClassEncodingException {
		ObjectEncoder encoder = encoders.get(clazz);
		
		if (encoder == null) {
			encoder = new ObjectEncoder(clazz);
			encoders.put(clazz, encoder);
		}
		
		return encoder;
	}
}
