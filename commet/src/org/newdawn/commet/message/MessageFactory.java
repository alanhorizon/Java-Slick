package org.newdawn.commet.message;

import java.io.IOException;

public interface MessageFactory {

	public Message createMessageFor(short id) throws IOException;
}
