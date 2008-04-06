package org.newdawn.commet.object;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.commet.object.encoders.IntegerEncoder;
import org.newdawn.commet.object.encoders.ObjectFieldEncoder;

public class ObjectEncoder {
	private static HashMap<Class<?>, Class<? extends FieldEncoder>> encoderTypes = 
							new HashMap<Class<?>, Class<? extends FieldEncoder>>();
	
	static {
		encoderTypes.put(Integer.TYPE, IntegerEncoder.class);
	}
	
	private ArrayList<FieldEncoder> encoders = new ArrayList<FieldEncoder>();
	
	public ObjectEncoder(Class clazz) throws ClassEncodingException {
		Field[] fields = clazz.getDeclaredFields();
		processFields(fields);
	}
	
	private void processFields(Field[] fields) throws ClassEncodingException {
		for (int i=0;i<fields.length;i++) {
			NetworkField network = fields[i].getAnnotation(NetworkField.class);
			if (network != null) {
				addFieldEncoder(fields[i]);
			}
		}
	}
	
	private void addFieldEncoder(Field field) throws ClassEncodingException {
		Class<? extends FieldEncoder> encoderClazz = encoderTypes.get(field.getType());
		if (encoderClazz == null) {
			encoderClazz = ObjectFieldEncoder.class;
		}
		
		try {
			FieldEncoder encoder = encoderClazz.getConstructor(new Class[] {Field.class}).newInstance(new Object[] {field});
			encoders.add(encoder);
		} catch (IllegalArgumentException e) {
			throw new ClassEncodingException("Failed to create field encoder for: "+field, e);
		} catch (SecurityException e) {
			throw new ClassEncodingException("Failed to create field encoder for: "+field, e);
		} catch (InstantiationException e) {
			throw new ClassEncodingException("Failed to create field encoder for: "+field, e);
		} catch (IllegalAccessException e) {
			throw new ClassEncodingException("Failed to create field encoder for: "+field, e);
		} catch (InvocationTargetException e) {
			throw new ClassEncodingException("Failed to create field encoder for: "+field, e);
		} catch (NoSuchMethodException e) {
			throw new ClassEncodingException("Failed to create field encoder for: "+field, e);
		}
	}
	
	public void encode(DataOutputStream dout, Object value) throws IOException {
		for (int i=0;i<encoders.size();i++) {
			encoders.get(i).encode(dout, value);
		}
	}
	
	public void decode(DataInputStream din, Object value) throws IOException {
		for (int i=0;i<encoders.size();i++) {
			encoders.get(i).decode(din, value);
		}
	}
}
