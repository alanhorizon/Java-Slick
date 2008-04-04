package org.newdawn.commet.message;

import java.io.IOException;

public class TankMessageFactory implements MessageFactory {

	public Message createMessageFor(short id) throws IOException {
		switch (id) {
		case TankUpdateMessage.ID:
			return new TankUpdateMessage();
		case TankRemoveMessage.ID:
			return new TankRemoveMessage();
		}
		
		return null;
	}
}
