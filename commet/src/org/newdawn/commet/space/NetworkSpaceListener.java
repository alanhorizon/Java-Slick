package org.newdawn.commet.space;

import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageChannel;
import org.newdawn.commet.transport.TransportChannel;

public interface NetworkSpaceListener {

	public void objectAdded(NetworkSpace source, Object obj, short id, short ownerID);
	
	public void objectRemoved(NetworkSpace source, Object obj, short id, short ownerID);
	
	public void channelDisconnected(MessageChannel channel);
	
	public void customMessageRecieved(MessageChannel channel, Message message);
}
