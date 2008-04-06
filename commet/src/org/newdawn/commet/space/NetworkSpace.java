package org.newdawn.commet.space;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageChannel;
import org.newdawn.commet.message.MessageFactory;
import org.newdawn.commet.object.ClassEncodingException;
import org.newdawn.commet.transport.TransportChannel;
import org.newdawn.commet.transport.TransportFactory;
import org.newdawn.commet.util.Log;

public class NetworkSpace {
	/** The TCP transport layer indicator */
	public static final int TCP = TransportFactory.TCP;
	/** The UDP transport layer indicator */
	public static final int UDP = TransportFactory.UDP;
	
	public static final int LOCAL_AUTHORITY = 1;
	public static final int REMOTE_AUTHORITY = 2;

	/**
	 * Configure the transport to be produced. 
	 * 
	 * @param m The transport layer indicator. Should be one of {@link #TCP} or {@link #UDP}
	 */
	public static void configureMode(int m) {
		TransportFactory.configureMode(m);
	}
	
	private ArrayList<SharedObject> shared = new ArrayList<SharedObject>();
	private ArrayList<MessageChannel> channels = new ArrayList<MessageChannel>();
	private HashMap<TransportChannel, MessageChannel> channelMap = new HashMap<TransportChannel, MessageChannel>();
	
	private HashMap<Object, SharedObject> extMap = new HashMap<Object, SharedObject>();
	private HashMap<Integer, SharedObject> idMap = new HashMap<Integer, SharedObject>();
	
	private int updateInterval;
	private int updateTimer;
	private SpaceMessageFactory factory = new SpaceMessageFactory();
	
	private short nextID = 1;
	private short channelID = 0;
	
	private ArrayList<NetworkSpaceListener> listeners = new ArrayList<NetworkSpaceListener>();
	private boolean broadcast;
	private int spaceID;
	
	public NetworkSpace(String host, int port, int updateInterval) throws IOException {
		this(TransportFactory.createChannel(host, port), updateInterval);
		broadcast = false;
	}
	
	public NetworkSpace(TransportChannel channel, int updateInterval) {
		this(updateInterval);
		broadcast = false;
		
		try {
			addChannel(channel);
		} catch (IOException e) {
			// should never happen since we won't be broadcasting
			// state 
			Log.error(e);
		}
		
		channelID = (short) channel.getChannelID();
	}
	
	public NetworkSpace(int updateInterval) {
		this.updateInterval = updateInterval;
		updateTimer = updateInterval;
		
		// server side
		broadcast = true;
		
		spaceID = NetworkSpaceHolder.registerSpace(this);
	}
	
	public void setMessageFactory(SpaceMessageFactory factory) {
		this.factory = factory;
	}
	
	public short getLocalOwnerID() {
		if (channels.size() == 1) {
			return (short) channels.get(0).getChannelID();
		}
		
		return 0;
	}
	
	SharedObject getSharedObjectByID(int id) {
		return idMap.get(id);
	}
	
	public void addListener(NetworkSpaceListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(NetworkSpaceListener listener) {
		listeners.remove(listener);
	}
	
	public void addChannel(TransportChannel channel) throws IOException {
		MessageChannel msg = new MessageChannel(factory, channel);
		channelMap.put(channel, msg);
		channels.add(msg);
		
		if (broadcast) {
			sendState(msg);
		}
	}
	
	public void removeChannel(TransportChannel channel) {
		MessageChannel msg = channelMap.remove(channel);
		channels.remove(msg);
	}
	
	public void add(Object obj) {
		add(obj, LOCAL_AUTHORITY);
	}
	
	public void add(Object obj, int authority) {
		short objId = nextID++;
		short ownerID = channelID;
		
		try {
			addSharedObject(new SharedObject(obj, objId, ownerID, authority));
		} catch (ClassEncodingException e) {
			throw new RuntimeException("Cannot encode "+obj, e);
		}
		sendMessage(new CreateMessage(obj.getClass().getName(), objId, ownerID), -1);
	}
	
	private void addSharedObject(SharedObject obj) {
		shared.add(obj);
		extMap.put(obj.getTarget(), obj);
		idMap.put(obj.getCombinedID(), obj);
		
		for (int i=0;i<listeners.size();i++) {
			listeners.get(i).objectAdded(this, obj.getTarget(), obj.getObjectID(), obj.getOwnerID());
		}
	}
	
	private void removeSharedObject(SharedObject obj) {
		extMap.remove(obj.getTarget());
		shared.remove(obj);
		idMap.remove(obj.getCombinedID());
		
		for (int i=0;i<listeners.size();i++) {
			listeners.get(i).objectRemoved(this, obj.getTarget(), obj.getObjectID(), obj.getOwnerID());
		}
	}
	
	public void remove(Object obj) {
		SharedObject s = extMap.get(obj);
		
		if (s.getAuthority() == LOCAL_AUTHORITY) {
			removeSharedObject(s);
			sendMessage(new DestroyMessage(s.getObjectID(), s.getOwnerID()), -1);
		} else {
			throw new RuntimeException("Attempt to remove object without local authority");
		}
	}
	
	public void sendMessage(Message message, int ignoreID) {
		for (int i=0;i<channels.size();i++) {
				if (channels.get(i).getChannelID() != ignoreID) {
				try {
					channels.get(i).write(message, true);
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
	}
	
	public void update(int delta) {
		updateTimer -= delta;
		if (updateTimer < 0) {
			updateTimer = updateInterval;
			sendUpdates();
		}
		
		for (int i=0;i<channels.size();i++) {
			try {
				Message message = channels.get(i).read();
				if (message != null) {
					switch (message.getID()) {
					case CreateMessage.ID:
						create((CreateMessage) message);
						break;
					case DestroyMessage.ID:
						destroy((DestroyMessage) message);
						break;
					case UpdateMessage.ID:
						// do nothing, the logic is contained
						// within the update message
						break;
					default:
						for (int j=0;j<listeners.size();j++) {
							listeners.get(j).customMessageRecieved(channels.get(i), message);
						}
						break;
					}
				}
			} catch (IOException e) {
				Log.error(e);
				
				try {
					channels.get(i).close();
				} catch (IOException e1) {
					Log.error(e1);
				}
				
				MessageChannel channel = channels.remove(i);
				i--;
				
				for (int j=0;j<listeners.size();j++) {
					listeners.get(j).channelDisconnected(channel);
				}
			}
		}
	}
	
	private void create(CreateMessage message) throws IOException {
		try {
			Class clazz = Class.forName(message.getClassName());
			Constructor con = clazz.getDeclaredConstructor(new Class[0]);
			con.setAccessible(true);
			Object obj = con.newInstance(new Object[0]);
			
			try { 
				addSharedObject(new SharedObject(obj, message.getObjectID(), 
													  message.getOwnerID(), REMOTE_AUTHORITY));
			} catch (ClassEncodingException e) {
				throw new RuntimeException("Cannot encode "+obj, e);
			}
			
			if (broadcast) {
				short objId = message.getObjectID();
				short ownerID = message.getOwnerID();
				
				sendMessage(new CreateMessage(obj.getClass().getName(), objId, ownerID), ownerID);
			}
		} catch (InstantiationException e) {
			throw new IOException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new IOException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new IOException(e.getMessage());
		} catch (SecurityException e) {
			throw new IOException(e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new IOException(e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new IOException(e.getMessage());
		}
	}
	
	private void destroy(DestroyMessage message) {
		int combined = getCombinedID(message.getObjectID(), message.getOwnerID());
		SharedObject shared = idMap.get(combined);
		removeSharedObject(shared);
		
		if (broadcast) {
			short objId = message.getObjectID();
			short ownerID = message.getOwnerID();

			sendMessage(new DestroyMessage(shared.getObjectID(), shared.getOwnerID()), shared.getOwnerID());
		}
	}
	
	private void sendState(MessageChannel channel) throws IOException {
		for (int i=0;i<shared.size();i++) {
			SharedObject obj = shared.get(i);
			short objId = obj.getObjectID();
			short ownerID = obj.getOwnerID();

			channel.write(new CreateMessage(obj.getTarget().getClass().getName(), objId, ownerID), true);
		}
	}
	
	private int getCombinedID(short objectID, short ownerID) {
		return (ownerID << 16) + objectID;
	}
	
	private void sendUpdates() {
		UpdateMessage update = new UpdateMessage();
		
		for (int i=0;i<shared.size();i++) {
			if ((broadcast) || (shared.get(i).getAuthority() == LOCAL_AUTHORITY)) {
				update.add(shared.get(i));
			}
		}
		
		for (int i=0;i<channels.size();i++) {
			try {
				channels.get(i).write(update, true);
			} catch (IOException e) {
				Log.error(e);
			}
		}
	}
}
