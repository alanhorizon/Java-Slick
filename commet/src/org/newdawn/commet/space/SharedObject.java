package org.newdawn.commet.space;

public class SharedObject {
	private Object target;
	private short clientID;
	private short id;
	private int authority;
	
	public SharedObject(Object target, short id, short clientID, int authority) {
		this.target = target;
		this.id = id;
		this.clientID = clientID;
		this.authority = authority;
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
}
