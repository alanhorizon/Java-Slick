package org.newdawn.commet.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.newdawn.commet.example.Tank;

public class TankUpdateMessage implements Message {
	public static final int ID = 1;
	
	private int channelID;
	private float x;
	private float y;
	private float ang;
	private float turretAng;
	
	public TankUpdateMessage() {
		
	}
	
	public TankUpdateMessage(int channelID, Tank tank) {
		this.channelID = channelID;
		x = tank.getX();
		y = tank.getY();
		ang = tank.getAngle();
		turretAng = tank.getTurretAngle();
	}

	public int getChannelID() {
		return channelID;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getAngle() {
		return ang;
	}
	
	public float getTurretAngle() {
		return turretAng;
	}
	
	public void decode(DataInputStream din) throws IOException {
		channelID = din.readInt();
		x = din.readFloat();
		y = din.readFloat();
		ang = din.readFloat();
		turretAng = din.readFloat();
	}

	public void encode(DataOutputStream dout) throws IOException {
		dout.writeInt(channelID);
		dout.writeFloat(x);
		dout.writeFloat(y);
		dout.writeFloat(ang);
		dout.writeFloat(turretAng);
	}

	public short getID() {
		return ID;
	}

}
