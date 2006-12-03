package playground.games;

import java.io.IOException;

/**
 * TODO: Document this class
 *
 * @author kevin
 */
public interface GameStore {
	
	public GameList getGames(String category);
	
	public GameList getGames();
	
	public String[] getCategories();
	
	public void update() throws IOException;
	
	public long lastUpdated();
}
