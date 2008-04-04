package org.newdawn.commet.example;

public class Tank {
	private float x = 100;
	private float y = 100;
	private float ang = 10;
	private float turretAng = 20;
	
	private float initialised;
	
	public void configure(float x, float y, float ang, float turretAng) {
		this.x = x;
		this.y = y;
		this.ang = ang;
		this.turretAng = turretAng;
	}
	
	public void rotate(float ang) {
		this.ang += ang;
	}
	
	public void move(float amount) {
		this.x += Math.sin(ang) * amount;
		this.y -= Math.cos(ang) * amount;
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
}
