package org.newdawn.commet.chat;

import java.io.IOException;

import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageFactory;

/**
 * A factory to produce messages for the example chat client/server
 * 
 * @author kevin
 */
public class ChatMessageFactory implements MessageFactory {

	/**
	 * @see org.newdawn.commet.message.MessageFactory#createMessageFor(short)
	 */
	public Message createMessageFor(short id) throws IOException {
		if (id == ChatMessage.ID) {
			return new ChatMessage();
		}
		
		throw new IOException("Unknown message recieved: "+id);
	}

}
