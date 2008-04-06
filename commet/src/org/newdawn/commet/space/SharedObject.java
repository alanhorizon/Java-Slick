package org.newdawn.commet.space;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.newdawn.commet.object.ClassEncoderRegistry;
import org.newdawn.commet.object.ClassEncodingException;
import org.newdawn.commet.object.ObjectEncoder;

public class SharedObject {
	private ObjectEncoder encoder;
	private Object target;
	private short clientID;
	private short id;
	private int authority;
	private Object serverState;
	
	public SharedObject(Object target, short id, short clientID, int authority) throws ClassEncodingException {
		this.target = target;
		this.id = id;
		this.clientID = clientID;
		this.authority = authority;
		
		encoder = ClassEncoderRegistry.getEncoder(target.getClass());
		
		if (authority == NetworkSpace.LOCAL_AUTHORITY) {
			try {
				Constructor con = target.getClass().getDeclaredConstructor(new Class[0]);
				con.setAccessible(true);
				serverState = con.newInstance(new Object[0]);
			} catch (InstantiationException e) {
				throw new ClassEncodingException("Shared objects must have the default constructor: "+target.getClass());
			} catch (IllegalAccessException e) {
				throw new ClassEncodingException("Shared objects must have the default constructor: "+target.getClass());
			} catch (SecurityException e) {
				throw new ClassEncodingException("Shared objects must have the default constructor: "+target.getClass());
			} catch (IllegalArgumentException e) {
				throw new ClassEncodingException("Shared objects must have the default constructor: "+target.getClass());
			} catch (InvocationTargetException e) {
				throw new ClassEncodingException("Shared objects must have the default constructor: "+target.getClass());
			} catch (NoSuchMethodException e) {
				throw new ClassEncodingException("Shared objects must have the default constructor: "+target.getClass());
			} 
		}
	}
	
	public int getAuthority() {
		return authority;
	}
	
	public Object getTarget() {
		return target;
	}
	
	public short getOwnerID() {
		return clientID;
	}
	
	public short getObjectID() {
		return id;
	}
	
	public int getCombinedID() {
		return (clientID << 16) + id;
	}
	
	public void updateFrom(DataInputStream din) throws IOException {
		if (serverState != null) {
			encoder.decode(din, serverState);
		} else {
			encoder.decode(din, target);
		}
	}
	
	public void updateTo(DataOutputStream dout) throws IOException {
		encoder.encode(dout, target);
	}
}
