package org.newdawn.commet.example.lowlevel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.commet.example.Arena;
import org.newdawn.commet.example.Tank;
import org.newdawn.commet.example.TankGameClient;
import org.newdawn.commet.transport.TransportChannel;
import org.newdawn.commet.transport.TransportFactory;

public class LowLevelTankClient implements TankGameClient {
	private Arena arena;
	private TransportChannel channel;
	private ArrayList<Tank> localTanks = new ArrayList<Tank>();
	
	private ByteBuffer data = ByteBuffer.allocate(4096);
	private int updateInterval = 400;
	private int updateTimer = 0;
	
	private HashMap<Integer, Tank> tanks = new HashMap<Integer, Tank>();
	
	public void addTank(Tank tank) {
		arena.addTank(tank);
		localTanks.add(tank);
		
		tanks.put(channel.getChannelID(), tank);
		// on first update the tank will be created for everyone else
	}

	public void configure(String host, int port, Arena arena) throws IOException {
		this.arena = arena;
		
		channel = TransportFactory.createChannel(host, port);
	}

	public void update(int delta) throws IOException {
		updateTimer -= delta;
		if (updateTimer < 0) {
			updateTimer += updateInterval;
			
			// send an update message for each local tank
			for (int i=0;i<localTanks.size();i++) {
				Tank tank = localTanks.get(i);
				
				data.clear();
				data.putShort((short) channel.getChannelID()); // the local channel ID
				data.putFloat(tank.getX());
				data.putFloat(tank.getY());
				data.putFloat(tank.getAngle());
				data.putFloat(tank.getTurretAngle());
				data.flip();
				
				channel.write(data, true);
			}
		}
		
		// read any data pending
		int read = 1;
		
		data.clear();
		read = channel.read(data);
		
		if (read == -1) {
			throw new IOException("Server Disconnected");
		}
		
		while (read != 0) {
			data.flip();
			// we no that only entire reads will be pumped so we can safely
			// read the whole block
			short id = data.getShort();
			float x = data.getFloat();
			float y = data.getFloat();
			float angle = data.getFloat();
			float turret = data.getFloat();
			
			// -1 used an indicator that the tank is no longer valid. An example
			// of the sort of hack you do when prototyping that really isn't great. Wouldn't
			// it be nice if prototyping was quick and sane?
			if (angle == -1) {
				Tank tank = tanks.remove((int) id);
				if (tank != null) {
					arena.removeTank(tank);
					localTanks.remove(tank);
				}
			} else {
				Tank tank = tanks.get((int) id);
				if (tank == null) {
					tank = new Tank();
					tanks.put((int) id, tank);
					arena.addTank(tank);
				}
				
				tank.configure(x,y,angle,turret);
			}
			
			// get some more data if there is any
			data.clear();
			read = channel.read(data);
		}
	}

}
