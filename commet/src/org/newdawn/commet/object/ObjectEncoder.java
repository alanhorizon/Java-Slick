package org.newdawn.commet.object;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.commet.object.encoders.BooleanEncoder;
import org.newdawn.commet.object.encoders.ByteEncoder;
import org.newdawn.commet.object.encoders.CharEncoder;
import org.newdawn.commet.object.encoders.DoubleEncoder;
import org.newdawn.commet.object.encoders.FloatEncoder;
import org.newdawn.commet.object.encoders.IntegerEncoder;
import org.newdawn.commet.object.encoders.ObjectFieldEncoder;
import org.newdawn.commet.object.encoders.ShortEncoder;
import org.newdawn.commet.object.encoders.StringEncoder;

/**
 * An encoder that can encode the state of a given object's fields. Only the fields 
 * marked with the NetworkField annotation will be encoded/decoded
 * 
 * @author kevin
 */
public class ObjectEncoder {
	/** The map of encoders that can be used for specific field types */
	private static HashMap<Class<?>, Class<? extends FieldEncoder>> encoderTypes = 
							new HashMap<Class<?>, Class<? extends FieldEncoder>>();
	
	static {
		encoderTypes.put(Integer.TYPE, IntegerEncoder.class);
		encoderTypes.put(Boolean.TYPE, BooleanEncoder.class);
		encoderTypes.put(Short.TYPE, ShortEncoder.class);
		encoderTypes.put(Float.TYPE, FloatEncoder.class);
		encoderTypes.put(Double.TYPE, DoubleEncoder.class);
		encoderTypes.put(Byte.TYPE, ByteEncoder.class);
		encoderTypes.put(Character.TYPE, CharEncoder.class);
		encoderTypes.put(String.class, StringEncoder.class);
	}
	
	/** The ordered list of field encoders applied for the object type */
	private ArrayList<FieldEncoder> encoders = new ArrayList<FieldEncoder>();
	
	/**
	 * Create a new object encoder for the given class 
	 * 
	 * @param clazz The object type to be encoded
	 * @throws ClassEncodingException Indicates an inability to encode the given type
	 * of object.
	 */
	public ObjectEncoder(Class clazz) throws ClassEncodingException {
		Field[] fields = clazz.getDeclaredFields();
		processFields(fields);
	}
	
	/**
	 * Process all the fields and create encoders for the appropriate ones
	 * 
	 * @param fields The fields to process
	 * @throws ClassEncodingException Indicates an inability to encode one of the fields
	 */
	private void processFields(Field[] fields) throws ClassEncodingException {
		for (int i=0;i<fields.length;i++) {
			NetworkField network = fields[i].getAnnotation(NetworkField.class);
			if (network != null) {
				addFieldEncoder(fields[i]);
			}
		}
	}
	
	/**
	 * Add a field encoder for the given field
	 * 
	 * @param field The field to be encoded
	 * @throws ClassEncodingException Indicates an inability to encode the field
	 */
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
	
	/**
	 * Encode the given object to the output stream using the field encoders configured here
	 * 
	 * @param dout The output stream to encode to
	 * @param value The value to be encoded
	 * @throws IOException Indicates a failure to encode the given object
	 */
	public void encode(DataOutputStream dout, Object value) throws IOException {
		for (int i=0;i<encoders.size();i++) {
			encoders.get(i).encode(dout, value);
		}
	}
	
	/**
	 * Decode the given object's state from the input stream using the fields configured here
	 * 
	 * @param din The input stream from which to read the object's state
	 * @param value The value to decode into
	 * @throws IOException Indicates a failure to decode the given object
	 */
	public void decode(DataInputStream din, Object value) throws IOException {
		for (int i=0;i<encoders.size();i++) {
			encoders.get(i).decode(din, value);
		}
	}
}
