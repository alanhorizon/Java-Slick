package org.newdawn.commet.example.space;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageChannel;
import org.newdawn.commet.space.NetworkSpace;
import org.newdawn.commet.space.NetworkSpaceListener;
import org.newdawn.commet.tankexamples.Arena;
import org.newdawn.commet.tankexamples.Tank;
import org.newdawn.commet.tankexamples.TankGameClient;

/**
 * A tank client implementation that syncs everything through a network space
 * 
 * This implementation will get much more simple once we can sync lists of objects
 * but this is more complicated that it sounds. At this point you could just add
 * the Arena to the network space and do no listening at all.
 * 
 * @author kevin
 */
public class SpaceTankClient implements TankGameClient, NetworkSpaceListener {
	/** The arena */
	private Arena arena;
	/** The network space syncing the tanks */
	private NetworkSpace space;
	
	/**
	 * @see org.newdawn.commet.tankexamples.TankGameClient#addTank(org.newdawn.commet.tankexamples.Tank)
	 */
	public void addTank(Tank tank) {
		// when we add a tank we just add it to the space. We'll be notified of the 
		// addition and can use this to consistantly add tanks to the arena
		space.add(tank);
	}

	/**
	 * @see org.newdawn.commet.tankexamples.TankGameClient#configure(java.lang.String, int, org.newdawn.commet.tankexamples.Arena)
	 */
	public void configure(String host, int port, Arena arena)
			throws IOException {
		this.arena = arena;

		// connect to the space and add a listener so we can be notified of 
		// changes
		space = new NetworkSpace("localhost", 12345, 400);
		space.addListener(this);
	}

	/**
	 * @see org.newdawn.commet.tankexamples.TankGameClient#update(int)
	 */
	public void update(int delta) throws IOException {
		space.update(delta);
	}

	/**
	 * @see org.newdawn.commet.space.NetworkSpaceListener#channelDisconnected(org.newdawn.commet.message.MessageChannel)
	 */
	public void channelDisconnected(MessageChannel channel) {
		// if the server goes down, quit
		System.exit(0);
	}

	/**
	 * @see org.newdawn.commet.space.NetworkSpaceListener#customMessageRecieved(org.newdawn.commet.message.MessageChannel, org.newdawn.commet.message.Message)
	 */
	public void customMessageRecieved(MessageChannel channel, Message message) {
	}

	/**
	 * @see org.newdawn.commet.space.NetworkSpaceListener#objectAdded(org.newdawn.commet.space.NetworkSpace, java.lang.Object, short, short)
	 */
	public void objectAdded(NetworkSpace source, Object obj, short id, short ownerID) {
		// a tank has been added to the network space, add it to the game world
		arena.addTank((Tank) obj);
	}

	/**
	 * @see org.newdawn.commet.space.NetworkSpaceListener#objectRemoved(org.newdawn.commet.space.NetworkSpace, java.lang.Object, short, short)
	 */
	public void objectRemoved(NetworkSpace source, Object obj, short id, short ownerID) {
		// a tank has been removed from the network space, remove it from the game world
		arena.removeTank((Tank) obj);
	}

}
