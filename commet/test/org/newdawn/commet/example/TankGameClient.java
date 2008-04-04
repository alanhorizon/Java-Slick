package org.newdawn.commet.example;

import java.io.IOException;

public interface TankGameClient {

	public void configure(String host, int port, Arena arena) throws IOException;
	
	public void addTank(Tank tank);
	
	public void update(int delta) throws IOException;
}
