package org.newdawn.commet.space;

import java.io.IOException;

import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageFactory;

/**
 * A message factory used to produce the network space specific messages. This
 * should be extended if custom messages are to be sent within the network space
 * management.
 * 
 * @author kevin
 */
public class SpaceMessageFactory implements MessageFactory {

	/**
	 * @see org.newdawn.commet.message.MessageFactory#createMessageFor(short)
	 */
	public Message createMessageFor(short id) throws IOException {
		switch (id) {
		case CreateMessage.ID:
			return new CreateMessage();
		case DestroyMessage.ID:
			return new DestroyMessage();
		case UpdateMessage.ID:
			return new UpdateMessage();
		}
		
		throw new RuntimeException("Unknown network space message ID: "+id);
	}

}
