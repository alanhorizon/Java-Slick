package org.newdawn.commet.example.message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.commet.example.Arena;
import org.newdawn.commet.example.Tank;
import org.newdawn.commet.example.TankGameClient;
import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageChannel;
import org.newdawn.commet.transport.TransportChannel;

public class MessageTankClient implements TankGameClient {
	private ArrayList<Tank> localTanks = new ArrayList<Tank>();
	private int updateInterval = 400;
	private int updateTimer = 0;
	
	private HashMap<Integer, Tank> tanks = new HashMap<Integer, Tank>();
	
	private MessageChannel channel;
	private Arena arena;
	
	public void addTank(Tank tank) {
		arena.addTank(tank);
		localTanks.add(tank);
		
		tanks.put(channel.getChannelID(), tank);
	}

	public void configure(String host, int port, Arena arena) throws IOException {
		this.arena = arena;
		channel = new MessageChannel(new TankMessageFactory(), host, port);
	}

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
