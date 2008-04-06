package org.newdawn.commet.space;

import java.io.IOException;

import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageFactory;

public class SpaceMessageFactory implements MessageFactory {

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
