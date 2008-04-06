package org.newdawn.commet.tankexamples.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageChannel;
import org.newdawn.commet.tankexamples.Arena;
import org.newdawn.commet.tankexamples.Tank;
import org.newdawn.commet.tankexamples.TankGameClient;
import org.newdawn.commet.transport.TransportChannel;

/**
 * A tank game client based on message sending
 * 
 * @author kevin
 */
public class MessageTankClient implements TankGameClient {
	/** The list of tanks held locally */
	private ArrayList<Tank> localTanks = new ArrayList<Tank>();
	/** The interval between updates */
	private int updateInterval = 400;
	/** The update timer controlling updates */
	private int updateTimer = 0;
	
	/** The tanks helds based on their IDs */
	private HashMap<Integer, Tank> tanks = new HashMap<Integer, Tank>();
	
	/** The channel used to communicate with the server */
	private MessageChannel channel;
	/** The arena being synced */
	private Arena arena;
	
	/**
	 * @see org.newdawn.commet.tankexamples.TankGameClient#addTank(org.newdawn.commet.tankexamples.Tank)
	 */
	public void addTank(Tank tank) {
		arena.addTank(tank);
		localTanks.add(tank);
		
		tanks.put(channel.getChannelID(), tank);
	}

	/**
	 * @see org.newdawn.commet.tankexamples.TankGameClient#configure(java.lang.String, int, org.newdawn.commet.tankexamples.Arena)
	 */
	public void configure(String host, int port, Arena arena) throws IOException {
		this.arena = arena;
		channel = new MessageChannel(new TankMessageFactory(), host, port);
	}

	/**
	 * @see org.newdawn.commet.tankexamples.TankGameClient#update(int)
	 */
	public void update(int delta) throws IOException {
		updateTimer -= delta;
		if (updateTimer < 0) {
			updateTimer += updateInterval;
			
			// send an update message for each local tank
			for (int i=0;i<localTanks.size();i++) {
				Tank tank = localTanks.get(i);
				
				channel.write(new TankUpdateMessage(channel.getChannelID(), tank), true);
			}
		}
		
		Message message = channel.read();
		if (message != null) {
			switch (message.getID()) {
				case TankUpdateMessage.ID:
				{
					TankUpdateMessage update = (TankUpdateMessage) message;
					Tank tank = tanks.get(update.getChannelID());
					if (tank == null) {
						tank = new Tank();
						tanks.put(update.getChannelID(), tank);
						arena.addTank(tank);
					}
					
					tank.configure(update.getX(), update.getY(), update.getAngle(), update.getTurretAngle());
					break;
				}
				case TankRemoveMessage.ID:
				{
					TankRemoveMessage remove = (TankRemoveMessage) message;
					Tank tank = tanks.remove(remove.getChannelID());
					if (tank != null) {
						arena.removeTank(tank);
						localTanks.remove(tank);
					}
					break;
				}
			}
		}
	}

}
